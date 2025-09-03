package net.justsunnit.arson.packets;

import io.wispforest.owo.network.ServerAccess;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.data.ServerStaticData;
import net.justsunnit.arson.packets.objects.ClientHandshakePacket;

public class ClientHandshake {
    public static void run(ClientHandshakePacket clientHandshake, ServerAccess serverAccess) {
        Arson.LOGGER.info("Received client handshake from " + serverAccess.player().getName().getString() + " with mods: " + String.join(", ", clientHandshake.loadedMods()));
        ServerStaticData.playersWithClient.add(serverAccess.player().getUuidAsString());
    }
}
