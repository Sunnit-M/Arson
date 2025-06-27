package net.justsunnit.arson.commands;

import com.eduardomcb.discord.webhook.WebhookManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.util.IEntityDataSaver;
import net.justsunnit.arson.util.WebHookFormatter;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class TestLogPlaytime {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment env) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("LogPlaytime").requires(source ->
                                !source.isExecutedByPlayer() || Arson.config.isAdmin(source.getName()) || source.hasPermissionLevel(2))
                        .executes(TestLogPlaytime::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context){
            WebHookFormatter.SendPlaytimeMonthLog();
            WebHookFormatter.SendPlaytimeWeekLog();
            return 1;
    }
}
