package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class HelpCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("help")
                .executes(HelpCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        source.sendFeedback(() -> Text.of("""
                Arson Utils Commands:
                /arson help - Show this help message (All)\s
                /arson unban <player> - Unban a player (Only Ops)\s
                /arson ban <player> <days> <hours> <minutes> <seconds> <reason> - Ban a player (Only Admins)
                /arson permanentBan <player> <reason> - Permanently ban a player (Only Ops)
                /arson banList - Show the list of banned players (Only Admins)\s
                /arson spectate <player> - spectates a player (Only Mods)
                /arson return - Returns to your position before spectating (Only Mods)\s
                /arson maintenance <on, off, status> - Toggle maintenance mode (Only Ops)\s
                /arson reload - Reloads the config file (Only ops)
               """), false);
        return 1;
    }
}
