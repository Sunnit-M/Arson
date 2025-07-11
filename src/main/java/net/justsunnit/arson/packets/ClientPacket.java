package net.justsunnit.arson.packets;

import io.wispforest.owo.network.ClientAccess;
import net.justsunnit.arson.packets.objects.HasClientPacket;
import net.justsunnit.arson.util.StaticData;

public class ClientPacket {
    public static void run(HasClientPacket hasClientPacket, ClientAccess clientAccess) {
        StaticData.playersWithClient.add(clientAccess.player());
    }
}
