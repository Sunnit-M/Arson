package net.justsunnit.arson.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.justsunnit.arson.commands.*;
import net.justsunnit.arson.event.ModPlayerCopyFrom;

public class ModRegistries {
    public static void register() {
        // Register your items, blocks, entities, etc here
        registerCommands();
        registerEvents();
    }


    private static void registerCommands() {
        // Register your commands here
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, thing) -> {
            SpectatePlayer.register(dispatcher, dedicated);
            SpectateReturn.register(dispatcher, dedicated);
            TestLogPlaytime.register(dispatcher, dedicated);
        });
    }

    public static void registerEvents() {
        // Register your events here
        ServerPlayerEvents.COPY_FROM.register(new ModPlayerCopyFrom());
    }
}
