package net.justsunnit.arson.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandRegistry {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(SpectatePlayer::register);
        CommandRegistrationCallback.EVENT.register(SpectateReturn::register);
        CommandRegistrationCallback.EVENT.register(TestLogPlaytime::register);
        CommandRegistrationCallback.EVENT.register(Ban::register);
        CommandRegistrationCallback.EVENT.register(PermBan::register);
        CommandRegistrationCallback.EVENT.register(UnBan::register);
    }
}
