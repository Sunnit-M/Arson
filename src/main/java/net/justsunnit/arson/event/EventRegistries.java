package net.justsunnit.arson.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class EventRegistries {
    public static void registerEvents_Server() {
        ServerPlayConnectionEvents.JOIN.register(PlayerJoinLeaveEvents::playerJoin);
        ServerPlayConnectionEvents.DISCONNECT.register(PlayerJoinLeaveEvents::playerLeave);

        ServerLoginConnectionEvents.QUERY_START.register(PlayerLoginEvents::onQuerty);
    }

    public static void registerEvents_Client() {
        ClientPlayConnectionEvents.JOIN.register(ClientJoinServerEvent::playerJoin);
    }
}
