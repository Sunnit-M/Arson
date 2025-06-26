package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.util.IEntityDataSaver;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class SpectateReturn {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess dedicated, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal(Arson.CommandStarter)
                .then(CommandManager.literal("SpectateReturn")
                        .executes(SpectateReturn::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context){
        ServerPlayerEntity sender = context.getSource().getPlayer();
        IEntityDataSaver playerData = (IEntityDataSaver) sender;

        int[] backpos = playerData.getPersistentData().getIntArray("backpos").get();

        if(backpos.length == 0 || backpos == null) {
            context.getSource().sendFeedback(() -> Text.of("[ArsonUtils] You don't have a back position"), false);
            return 0;
        }

        playerData.getPersistentData().remove("backpos");

        sender.changeGameMode(GameMode.SURVIVAL);
        sender.requestTeleport(backpos[0], backpos[1], backpos[2]);
        sender.setInvulnerable(false);
        return 1;
    }
}
