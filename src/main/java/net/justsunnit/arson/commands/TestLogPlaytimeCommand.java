package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.justsunnit.arson.Arson;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class TestLogPlaytimeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment env) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("LogPlaytime").requires(source ->
                                !source.isExecutedByPlayer()  || source.hasPermissionLevel(4))
                        .executes(TestLogPlaytimeCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context){
            return 1;
    }
}
