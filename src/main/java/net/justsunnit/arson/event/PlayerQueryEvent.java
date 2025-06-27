package net.justsunnit.arson.event;

import net.fabricmc.fabric.api.networking.v1.LoginPacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.objects.HandshakePacketServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;

public class PlayerQueryEvent {
    public static void queryStart(ServerLoginNetworkHandler serverLoginNetworkHandler, MinecraftServer minecraftServer, LoginPacketSender loginPacketSender, ServerLoginNetworking.LoginSynchronizer loginSynchronizer) {
        loginPacketSender.sendPacket(Arson.QUERY_PACKET_ID, HandshakePacketServer.REQUEST);
    }
}
