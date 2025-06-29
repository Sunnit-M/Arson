package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.util.PlaytimeLogger;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class GetPlayerPlaytimeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("getPlaytime")
                        .requires(source -> !source.isExecutedByPlayer() || source.hasPermissionLevel(4) ||
                                Arson.config.isAdmin(source.getPlayer().getName().getLiteralString()))
                        .then(CommandManager.argument("player", GameProfileArgumentType.gameProfile())
                                .executes(GetPlayerPlaytimeCommand::run))));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        try {
            String playerName = GameProfileArgumentType.getProfileArgument(context, "player").iterator().next().getName();
            String playtime = PlaytimeLogger.getPlayerPlaytimeData(playerName).getFormattedPlaytime();
            context.getSource().sendMessage(Text.literal("[ArsonUtils] Player " + playerName + " has a total playtime of " + playtime).styled(style -> style.withBold(true)));
            return 1;
        } catch (Exception e) {
            context.getSource().sendError(Text.of("An error occurred while trying to retrieve the player's playtime."));
            e.printStackTrace();
            return 0;
        }
    }
}
