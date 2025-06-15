package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.util.IEntityDataSaver;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class TestLogPlaytime {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated) {
        dispatcher.register(CommandManager.literal(Arson.CommandStarter)
                .then(CommandManager.literal("LogPlaytime")
                        .executes(context -> {
                            return 1;
                        })
                )
        ) ;
    }
}
