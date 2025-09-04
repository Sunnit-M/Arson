package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.justsunnit.arson.ArsonServer;
import net.justsunnit.arson.util.JsonSaveHandler;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.joml.Vector3L;

import java.util.HashMap;

public class SpectatePlayerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("spectate").requires(source ->
                                !source.isExecutedByPlayer() || ArsonServer.config.admins().contains(source.getPlayer().getName().getLiteralString()) || source.hasPermissionLevel(4))
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

            HashMap<String, Vector3L> data = JsonSaveHandler.GetSpectateData();

            if(data.containsKey(sender.getUuidAsString())) {
                context.getSource().sendFeedback(() -> Text.of("[ArsonUtils] You are already spectating a player. Use /arson return to stop spectating."), false);
                return 0;
            }

            data.put(sender.getUuidAsString(), new Vector3L((int) sender.getX(), (int) sender.getY(), (int) sender.getZ()));

            JsonSaveHandler.SaveSpectateData(data);

            sender.changeGameMode(GameMode.SPECTATOR);
            sender.requestTeleport(player.getX(), player.getY(), player.getZ());
            return 1;
        }

}
