package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.justsunnit.arson.Arson;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SetMotdCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("setmotd")
                        .requires(source -> !source.isExecutedByPlayer() || source.hasPermissionLevel(4))
                        .then(CommandManager.argument("text",StringArgumentType.greedyString())
                                .executes(SetMotdCommand::run))));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        String motd = StringArgumentType.getString(context, "text");
        ServerCommandSource source = context.getSource();

        if (motd.isEmpty()) {
            source.sendError(Text.of("MOTD cannot be empty."));
            return 0;
        }


        if(motd.startsWith("def")){
            Arson.config.setMaintenanceMode(Arson.config.isMaintenanceMode());
            source.sendMessage(Text.of("[ArsonUtils] MOTD has been set to default."));
        }
        else {
            Arson.server.setMotd(motd);
            source.sendMessage(Text.of("[ArsonUtils] MOTD has been set to: " + motd));
        }
        return 1;
    }
}
