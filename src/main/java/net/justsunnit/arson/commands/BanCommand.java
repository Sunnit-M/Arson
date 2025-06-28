package net.justsunnit.arson.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.automod.BannedData;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.util.WebHookFormatter;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.time.LocalDateTime;
import java.util.Collection;

public class BanCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register((CommandManager.literal("arson")
                .then(CommandManager.literal("timedBan").requires(source ->  !source.isExecutedByPlayer() ||
                                Arson.config.isAdmin(source.getPlayer().getName().getLiteralString()) || source.hasPermissionLevel(4))
                .then(CommandManager.argument("player", GameProfileArgumentType.gameProfile())
                .then(CommandManager.argument("days", IntegerArgumentType.integer(0))
                .then(CommandManager.argument("hours", IntegerArgumentType.integer(0))
                .then(CommandManager.argument("minutes", IntegerArgumentType.integer(0))
                .then(CommandManager.argument("seconds", IntegerArgumentType.integer(1))
                .then(CommandManager.argument("reason", StringArgumentType.greedyString())
                .executes(BanCommand::run))))))))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try {
            Collection<GameProfile> Profiles = GameProfileArgumentType.getProfileArgument(context, "player");
            GameProfile player = Profiles.iterator().next();
            int Days = IntegerArgumentType.getInteger(context, "days");
            int Hours = IntegerArgumentType.getInteger(context, "hours");
            int Minutes = IntegerArgumentType.getInteger(context, "minutes");
            int Seconds = IntegerArgumentType.getInteger(context, "seconds");
            String Reason = StringArgumentType.getString(context, "reason");

            if (Days < 0 || Hours < 0 || Minutes < 0 || Seconds < 0) {
                context.getSource().sendError(Text.of("Time values cannot be negative."));
                return 0;
            }

            BannedPlayer data = new BannedPlayer(player.getName(), Reason, LocalDateTime.now(), Days * 86400L + Hours * 3600L + Minutes * 60L + Seconds, false);

            BannedData.banPlayer(data, player.getId().toString());

            context.getSource().sendMessage(Text.literal("[ArsonUtils] Player " + player.getName() + " has been banned for: " + data.BanSeconds + " seconds. Reason: " + data.Reason.toString()).styled(style -> style.withBold(true)));

            WebHookFormatter.SendCommandHook("[ArsonUtils] " + context.getSource().getName() + " has banned " + player.getName() + " for " + data.BanSeconds + " seconds. Reason: " + data.Reason);

            return 1;
        }
        catch (Exception e){
            context.getSource().sendMessage(EntityArgumentType.getPlayer(context, "player").getName());
            e.printStackTrace();
            return 0;
        }
    }
}
