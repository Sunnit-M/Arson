package net.justsunnit.arson.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public class EventRegistries {
    public static void registerEvents() {
        ServerPlayerEvents.COPY_FROM.register(new ModPlayerCopyFrom());
    }
}
