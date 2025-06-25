package net.justsunnit.arson.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandRegistry {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, thing) -> {
            SpectatePlayer.register(dispatcher, dedicated);
            SpectateReturn.register(dispatcher, dedicated);
            TestLogPlaytime.register(dispatcher, dedicated);
        });
    }
}
