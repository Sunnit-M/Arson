package net.justsunnit.arson.packets;

import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.justsunnit.arson.Arson;

public class PacketRegistries {
    public static void registerPackets() {
        ServerLoginNetworking.registerGlobalReceiver(Arson.QUERY_PACKET_ID, ClientPacketReciever::packet);
    }
}
