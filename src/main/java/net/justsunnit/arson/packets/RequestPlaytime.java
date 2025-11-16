package net.justsunnit.arson.packets;

import io.wispforest.owo.network.ServerAccess;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.objects.PlayerPlaytimeData;
import net.justsunnit.arson.packets.objects.PlaytimePacket;
import net.justsunnit.arson.packets.objects.RequestPacket;
import net.justsunnit.arson.util.JsonSaveHandler;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class RequestPlaytime {
    public static void run(RequestPacket requestPacket, ServerAccess serverAccess) {
        if (requestPacket.type() == 0) {
            Arson.LOGGER.info("Received playtime request from " + serverAccess.player().getName().getString());

            Map<String, Long> week = new HashMap<>();
            Map<String, Long> monthly = new HashMap<>();
            Map<String, Long> allTime = new HashMap<>();

            for (String id : JsonSaveHandler.GetPlayerPlaytimeData().keySet()) {
                PlayerPlaytimeData data = JsonSaveHandler.GetPlayerPlaytimeData().get(id);
                week.put(id, data.getWeekPlaytime());
                monthly.put(id, data.getMonthPlaytime());
                allTime.put(id, data.getTotalPlaytime());
            }

            Arson.SEND_PLAYTIME_CHANNEL.serverHandle(serverAccess.player()).send(new PlaytimePacket(week, monthly, allTime));
        }
    }
}
