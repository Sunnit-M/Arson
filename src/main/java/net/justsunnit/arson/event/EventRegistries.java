package net.justsunnit.arson.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class EventRegistries {
    public static void registerEvents() {
        ServerPlayerEvents.COPY_FROM.register(new ModPlayerCopyFrom());

        ServerPlayConnectionEvents.JOIN.register(PlayerJoinLeaveEvents::playerJoin);
        ServerPlayConnectionEvents.DISCONNECT.register(PlayerJoinLeaveEvents::playerLeave);
    }
}
