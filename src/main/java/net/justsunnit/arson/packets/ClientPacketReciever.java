package net.justsunnit.arson.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.justsunnit.arson.automod.Handshake;
import net.justsunnit.arson.objects.HandshakePacketServer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;

public class ClientPacketReciever {
    public static void packet(MinecraftServer minecraftServer, ServerLoginNetworkHandler serverLoginNetworkHandler, boolean b, PacketByteBuf packetByteBuf, ServerLoginNetworking.LoginSynchronizer loginSynchronizer, PacketSender packetSender) {
        if (b) {
            HandshakePacketServer packet = HandshakePacketServer.fromPacket(packetByteBuf);

            Handshake.addHandshake(packet.getPlayerUUID(), packet);
        }
    }
}
