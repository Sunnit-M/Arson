package net.justsunnit.arson.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandRegistry {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(SpectatePlayerCommand::register);
        CommandRegistrationCallback.EVENT.register(SpectateReturnCommand::register);
        CommandRegistrationCallback.EVENT.register(TestLogPlaytimeCommand::register);
        CommandRegistrationCallback.EVENT.register(BanCommand::register);
        CommandRegistrationCallback.EVENT.register(PermBanCommand::register);
        CommandRegistrationCallback.EVENT.register(UnBan::register);
        CommandRegistrationCallback.EVENT.register(BanListCommand::register);
        CommandRegistrationCallback.EVENT.register(MainteneceCommand::register);
        CommandRegistrationCallback.EVENT.register(PlaytimeCommand::register);
    }
}
