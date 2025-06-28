package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.util.IEntityDataSaver;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class SpectatePlayerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("Spectate").requires(source ->
                                !source.isExecutedByPlayer() || Arson.config.isAdmin(source.getName()) || source.hasPermissionLevel(2))
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .executes(SpectatePlayerCommand::run)
                        )
                )
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
            ServerPlayerEntity sender = context.getSource().getPlayer();

            if(player == null) {
                context.getSource().sendFeedback(() -> Text.of("[ArsonUtils," + sender.getName().toString() + "] Player not found"), false);
                return 0;
            }

            if(!player.isPlayer()){
                context.getSource().sendFeedback(() -> Text.of("[ArsonUtils] You can't spectate a non-player entity"), false);
                return 0;
            }

            IEntityDataSaver playerData = (IEntityDataSaver) player;

            playerData.getPersistentData().putIntArray("backpos", new int[]{(int) player.getX(), (int) player.getY(), (int) player.getZ()});

            sender.changeGameMode(GameMode.SPECTATOR);
            sender.requestTeleport(player.getX(), player.getY(), player.getZ());
            sender.setInvulnerable(true);
            return 1;
        }

}
