package net.justsunnit.arson.util;

import net.justsunnit.arson.Arson;
import net.justsunnit.arson.objects.PlayerPlaytimeData;
import net.justsunnit.arson.objects.ServerTimeStampData;

import java.time.*;
import java.util.HashMap;
import java.util.Map;

public class PlaytimeLogger {
    public static Map<String, LocalDateTime> playerLoginTimeStamp = new HashMap<>();

    public static void initializeLogger(){
        UpdateTimeStamps();
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
        Arson.LOGGER.info("[ArsonUtils] Week has been reset.");
        Arson.LOGGER.info(TextFormatter.formatWeekTimeLeaderBoard());
        WebHookFormatter.SendPlaytimeWeekLog();
    }

    public static void UpdateServerTimeStampData_Month(){
        JsonSaveHandler.SaveServerTimeStampData(new ServerTimeStampData(JsonSaveHandler.GetServerTimeStampData().LoggedWeek, LocalDateTime.now()));
        JsonSaveHandler.GetPlayerPlaytimeData().forEach((key, value) -> {
            ((PlayerPlaytimeData) value).ResetMonthPlaytime();
        });
        Arson.LOGGER.info("[ArsonUtils] Month has been reset.");
        Arson.LOGGER.info(TextFormatter.formatMonthTimeLeaderBoard());
        WebHookFormatter.SendPlaytimeMonthLog();
    }


}
