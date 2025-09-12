package net.justsunnit.arson.packets;

import net.justsunnit.arson.Arson;
import net.justsunnit.arson.packets.objects.ClientHandshakePacket;
import net.justsunnit.arson.packets.objects.PlaytimePacket;
import net.justsunnit.arson.packets.objects.RequestPacket;

public class RegisterPackets {
    public static void registerPackets() {
        Arson.CLIENT_HANDSHAKE_CHANNEL.registerServerbound(ClientHandshakePacket.class, ClientHandshake::run);
        Arson.REQUEST_PLAYTIME_CHANNEL.registerServerbound(RequestPacket.class, RequestPlaytime::run);
        Arson.SEND_PLAYTIME_CHANNEL.registerClientbound(PlaytimePacket.class, SendPlaytime::run);
    }
}
