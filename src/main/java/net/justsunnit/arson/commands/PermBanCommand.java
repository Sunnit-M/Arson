package net.justsunnit.arson.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.justsunnit.arson.automod.BannedData;
import net.justsunnit.arson.objects.BannedPlayer;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.PlayerConfigEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.time.LocalDateTime;
import java.util.Collection;

public class PermBanCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("permanentBan").requires(source ->
                                !source.isExecutedByPlayer() || source.hasPermissionLevel(4))
                        .then(CommandManager.argument("player", GameProfileArgumentType.gameProfile())
                                .then(CommandManager.argument("reason", StringArgumentType.greedyString())
                                        .executes(PermBanCommand::run)))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try {
            Collection<PlayerConfigEntry> profiles = GameProfileArgumentType.getProfileArgument(context, "player");
            String reason = StringArgumentType.getString(context, "reason");
            PlayerConfigEntry player = profiles.iterator().next();

            BannedPlayer data = new BannedPlayer(player.name(), reason, LocalDateTime.now(), 0L, true);

            BannedData.banPlayer(data, player.id().toString());

            context.getSource().sendMessage(Text.literal("[ArsonUtils] Player " + player.name() + " has been permanently banned. Reason: " + data.Reason.toString()).styled(style -> style.withBold(true)));

            return 1;
        }
        catch (Exception e) {
            context.getSource().sendError(Text.of("An error occurred while trying to ban the player."));
            e.printStackTrace();
            return 0;
        }
    }
}

