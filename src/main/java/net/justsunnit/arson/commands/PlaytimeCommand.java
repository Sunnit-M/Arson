package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.justsunnit.arson.objects.PlayerPlaytimeData;
import net.justsunnit.arson.util.PlaytimeLogger;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class PlaytimeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("playtime")
                        .executes(PlaytimeCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        try {
            PlayerPlaytimeData data = PlaytimeLogger.getPlayerPlaytimeData(context.getSource().getPlayer().getName().getLiteralString());

            context.getSource().sendMessage(Text.literal("[ArsonUtils] Your playtime: " + data.getFormattedPlaytime())
                    .styled(style -> style.withBold(true)));

            return 1;
        }
        catch (Exception e) {
            context.getSource().sendError(Text.of("[ArsonUtils] An error occurred while trying to get playtime."));
            e.printStackTrace();
            return 0;
        }
    }
}
