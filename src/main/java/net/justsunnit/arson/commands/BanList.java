package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.automod.BannedData;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.util.TextFormatter;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.HashMap;

public class BanList {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("banlist")
                        .requires(source ->
                        !source.isExecutedByPlayer() || Arson.config.isAdmin(source.getName()) || source.hasPermissionLevel(2))
                .executes(BanList::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try {
            StringBuilder banList = new StringBuilder("[ArsonUtils] Banned Players:\n");
            HashMap<String, BannedPlayer> bannedPlayers = BannedData.loadBannedData();

            if (bannedPlayers.isEmpty()) {
                banList.append("No players are currently banned.");
            } else {
                for (String uuid : bannedPlayers.keySet()) {
                    BannedPlayer player = bannedPlayers.get(uuid);
                    banList.append(player.Name).append(" - Reason: ").append(player.Reason).append("\n")
                            .append("   For").append((player.timeless ? "inf" : TextFormatter.formatDuration(player.BanSeconds)))
                }
            }

            context.getSource().sendMessage(Text.literal(banList.toString()).styled(style -> style.withBold(true)));

            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
