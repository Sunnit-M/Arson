package net.justsunnit.arson.packets;

import io.wispforest.owo.network.ClientAccess;
import net.justsunnit.arson.data.ClientStaticData;
import net.justsunnit.arson.objects.PlayerPlaytimeData;
import net.justsunnit.arson.packets.objects.PlaytimePacket;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SendPlaytime {
    public static void run(PlaytimePacket playtimePacket, ClientAccess clientAccess) {
        ArrayList<HashMap.Entry<String, Long>> allTimeRanked =
                new ArrayList<>(playtimePacket.allTime().entrySet());
        allTimeRanked.sort((a, b) ->
                Integer.compare((int) b.getValue().intValue(), (int) a.getValue().intValue()));
        ArrayList<HashMap.Entry<String, Long>> monthlyRanked =
                new ArrayList<>(playtimePacket.monthly().entrySet());
        monthlyRanked.sort((a, b) ->
                Integer.compare((int) b.getValue().intValue(), (int) a.getValue().intValue()));
        ArrayList<HashMap.Entry<String, Long>> weeklyRanked =
                new ArrayList<>(playtimePacket.week().entrySet());
        weeklyRanked.sort((a, b) ->
                Integer.compare((int) b.getValue().intValue(), (int) a.getValue().intValue()));

        ClientStaticData.allTimeRanked = allTimeRanked;
        ClientStaticData.monthlyRanked = monthlyRanked;
        ClientStaticData.weeklyRanked = weeklyRanked;

        clientAccess.player().sendMessage(Text.literal("[Arson] Playtime data received. Press a Button to load"), false);
    }
}
