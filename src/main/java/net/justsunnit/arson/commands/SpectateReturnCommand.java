package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.justsunnit.arson.ArsonServer;
import net.justsunnit.arson.util.JsonSaveHandler;
import net.justsunnit.fern.Fern;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.joml.Vector3L;

import java.util.HashMap;

public class SpectateReturnCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("return").requires(source ->
                                !source.isExecutedByPlayer() || Fern.check(source, "Mod.spectate") || Fern.checkGroup(source, "Mod") || source.hasPermissionLevel(4))
                        .executes(SpectateReturnCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context){
        ServerPlayerEntity sender = context.getSource().getPlayer();

        HashMap<String, Vector3L> data = JsonSaveHandler.GetSpectateData();

        if(!data.containsKey(sender.getUuidAsString())) {
            context.getSource().sendFeedback(() -> Text.of("[ArsonUtils] You are not spectating any player."), false);
            return 0;
        }

        Vector3L backpos = data.get(sender.getUuidAsString());

        sender.changeGameMode(GameMode.SURVIVAL);
        sender.requestTeleport(backpos.x, backpos.y, backpos.z);

        data.remove(sender.getUuidAsString());

        JsonSaveHandler.SaveSpectateData(data);

        return 1;
    }
}
