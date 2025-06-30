package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.justsunnit.arson.Arson;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ConfigReloadCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("Reload")
                        .requires(source -> !source.isExecutedByPlayer() || source.hasPermissionLevel(4))
                        .executes(ConfigReloadCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        try{
            Arson.config.LoadConfig();
            return 1;
        }
        catch (Exception e){
            context.getSource().sendError(Text.literal("[ArsonUtils] Config Relaod Failed").styled(style -> style.withColor(Formatting.RED)));
            return 0;
        }
    }
}
