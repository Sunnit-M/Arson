package net.justsunnit.arson.packets;

import net.justsunnit.arson.Arson;
import net.justsunnit.arson.packets.objects.HasClientPacket;

public class RegisterPackets {
    public static void registerPackets() {
        Arson.HAS_CLIENT_CHANNEL.registerClientbound(HasClientPacket.class, ClientPacket::run);
    }
}
