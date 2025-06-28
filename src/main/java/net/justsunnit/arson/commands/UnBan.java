package net.justsunnit.arson.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.automod.BannedData;
import net.justsunnit.arson.util.WebHookFormatter;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Collection;

public class UnBan {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("unban").requires(source ->
                                !source.isExecutedByPlayer() || source.hasPermissionLevel(4))
                .then(CommandManager.argument("player", GameProfileArgumentType.gameProfile())
                        .executes(UnBan::run))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try{
            Collection<GameProfile> profiles = GameProfileArgumentType.getProfileArgument(context, "player");
            GameProfile player = profiles.iterator().next();

            if (player == null) {
                context.getSource().sendError(Text.of("[ArsonUtils] Player not found."));
                return 0;
            }

            if(BannedData.unbanPlayer(player.getId().toString())){
                context.getSource().sendMessage(Text.literal("[ArsonUtils] Player " + player.getName() + " has been unbanned.").styled(style -> style.withBold(true)));

                WebHookFormatter.SendCommandHook("[ArsonUtils] " + context.getSource().getName() + " has unbanned " + player.getName() + ".");
                return 1;
            } else {
                context.getSource().sendError(Text.of("[ArsonUtils] Player " + player.getName() + " is not banned."));
                return 1;
            }
        } catch (Exception e) {
            context.getSource().sendError(Text.of("[ArsonUtils] An error occurred while trying to unban the player."));
            e.printStackTrace();
            return 0;
        }
    }
}
