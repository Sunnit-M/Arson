package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.justsunnit.arson.automod.BannedData;
import net.justsunnit.arson.objects.BannedPlayer;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.time.LocalDateTime;

public class Ban {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register((CommandManager.literal("arson")
                .then(CommandManager.literal("ban")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("days", IntegerArgumentType.integer(0))
                .then(CommandManager.argument("hours", IntegerArgumentType.integer(0))
                .then(CommandManager.argument("minutes", IntegerArgumentType.integer(0))
                .then(CommandManager.argument("seconds", IntegerArgumentType.integer(1))
                .then(CommandManager.argument("reason", StringArgumentType.greedyString())
                .executes(Ban::run))))))))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try {
            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
            int Days = IntegerArgumentType.getInteger(context, "days");
            int Hours = IntegerArgumentType.getInteger(context, "hours");
            int Minutes = IntegerArgumentType.getInteger(context, "minutes");
            int Seconds = IntegerArgumentType.getInteger(context, "seconds");
            String Reason = StringArgumentType.getString(context, "reason");

            if (Days < 0 || Hours < 0 || Minutes < 0 || Seconds < 0) {
                context.getSource().sendError(Text.of("Time values cannot be negative."));
                return 0;
            }

            BannedPlayer data = new BannedPlayer(player.getName().toString(), Reason, LocalDateTime.now(), Days * 86400L + Hours * 3600L + Minutes * 60L + Seconds, false);

            BannedData.banPlayer(data);

            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
