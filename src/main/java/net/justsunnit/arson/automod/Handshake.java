package net.justsunnit.arson.automod;

import net.justsunnit.arson.Arson;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.objects.HandshakePacketServer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Handshake {
    public static HashMap<String, HandshakePacketServer> handshakeMap = new HashMap<>();

    public static void addHandshake(String playerUUID, HandshakePacketServer handshakePacketServer) {
        handshakeMap.put(playerUUID, handshakePacketServer);

        ArrayList<String> illegal =  Arson.config.ModsAllowed(handshakePacketServer.getMods());

        if(!illegal.isEmpty()) {
            Arson.LOGGER.warn("[ArsonUtils] Player " + playerUUID + " has illegal mods: " + String.join(", ", illegal));

            BannedPlayer ban = new BannedPlayer(handshakePacketServer.getPlayerName(), "[Automod] Illegal mods: " + String.join("\n", illegal), LocalDateTime.now(),
                    (long) Arson.config.GetConfig().getOrDefault("config.mods.handshakeBan", 300), false);

        } else {
            Arson.LOGGER.info("[ArsonUtils] Player " + playerUUID + " has passed the handshake with mods: " + String.join(", ", handshakePacketServer.getMods()));
        }
    }

    public static void removeHandshake(String playerUUID) {
        handshakeMap.remove(playerUUID);
    }
}
