package net.justsunnit.arson.packets;

import net.justsunnit.arson.Arson;
import net.justsunnit.arson.packets.objects.ClientHandshakePacket;

public class RegisterPackets {
    public static void registerPackets() {
        Arson.CLIENT_HANDSHAKE_CHANNEL.registerServerbound(ClientHandshakePacket.class, ClientHandshake::run);
    }
}
