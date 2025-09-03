package net.justsunnit.arson.event;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.data.ClientStaticData;
import net.justsunnit.arson.packets.objects.ClientHandshakePacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class ClientJoinServerEvent {
    public static void playerJoin(ClientPlayNetworkHandler clientPlayNetworkHandler, PacketSender packetSender, MinecraftClient minecraftClient) {
        Arson.CLIENT_HANDSHAKE_CHANNEL.clientHandle().send(new ClientHandshakePacket(ClientStaticData.loadedModIDs));
    }
}
