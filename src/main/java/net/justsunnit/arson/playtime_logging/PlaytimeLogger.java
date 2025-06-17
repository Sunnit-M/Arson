package net.justsunnit.arson.playtime_logging;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.objects.PlayerPlaytimeData;
import net.justsunnit.arson.objects.ServerTimeStampData;
import net.justsunnit.arson.util.JsonSaveHandler;
import net.justsunnit.arson.util.WebHookFormatter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

import java.time.*;
import java.util.HashMap;
import java.util.Map;

public class PlaytimeLogger {
    public static Map<String, LocalDateTime> playerLoginTimeStamp = new HashMap<String, LocalDateTime>();

    public static void initializeLogger(){
        UpdateTimeStamps();
    }

    public static void playerJoin(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server){
        playerLoginTimeStamp.put(handler.getPlayer().getName().getLiteralString(), LocalDateTime.now());
    }

    public static void playerLeave(ServerPlayNetworkHandler handler, MinecraftServer server){
        Duration timeSpent = Duration.between(playerLoginTimeStamp.get(handler.getPlayer().getName().toString()), LocalDateTime.now());
        PlayerPlaytimeData data = (PlayerPlaytimeData) JsonSaveHandler.GetPlayerPlaytimeData().getOrDefault(handler.getPlayer().getName().toString(), new PlayerPlaytimeData());
        data.AddPlaytime(timeSpent.toSeconds());
        JsonSaveHandler.SavePlayerPlaytimeData(data, handler.getPlayer().getName().toString());
        Arson.LOGGER.info("Playtime for " + handler.getPlayer().getName().getLiteralString() + " has been saved.");

        playerLoginTimeStamp.remove(handler.getPlayer().getName().getLiteralString());
    }

    public static void UpdateTimeStamps(){
        LocalDateTime WeekTimestamp = JsonSaveHandler.GetServerTimeStampData().LoggedWeek;
        LocalDateTime MonthTimestamp = JsonSaveHandler.GetServerTimeStampData().LoggedMonth;

        LocalDateTime now = LocalDateTime.now();
        Duration week = Duration.between(WeekTimestamp, now);
        Duration month = Duration.between(MonthTimestamp, now);

        if(week.toDays() >= 7){
            UpdateServerTimeStampData_Week();
        }
        if(now.getMonth() != MonthTimestamp.getMonth()){
            UpdateServerTimeStampData_Month();
        }
    }

    public static void UpdateServerTimeStampData_Week(){
        JsonSaveHandler.SaveServerTimeStampData(new ServerTimeStampData(LocalDateTime.now(), JsonSaveHandler.GetServerTimeStampData().LoggedMonth));
        JsonSaveHandler.GetPlayerPlaytimeData().forEach((key, value) -> {
            ((PlayerPlaytimeData) value).ResetWeekPlaytime();
        });
        Arson.LOGGER.info("Week has been reset.");
        Arson.LOGGER.info(WebHookFormatter.formatWeekTimeLeaderBoard());
        WebHookFormatter.SendPlaytimeWeekLog();
    }

    public static void UpdateServerTimeStampData_Month(){
        JsonSaveHandler.SaveServerTimeStampData(new ServerTimeStampData(JsonSaveHandler.GetServerTimeStampData().LoggedWeek, LocalDateTime.now()));
        JsonSaveHandler.GetPlayerPlaytimeData().forEach((key, value) -> {
            ((PlayerPlaytimeData) value).ResetMonthPlaytime();
        });
        Arson.LOGGER.info("Month has been reset.");
        Arson.LOGGER.info(WebHookFormatter.formatMonthTimeLeaderBoard());
        WebHookFormatter.SendPlaytimeMonthLog();
    }


}
