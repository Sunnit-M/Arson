package net.justsunnit.arson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.justsunnit.arson.ArsonServer;
import net.justsunnit.arson.config.ArsonConfig;
import net.justsunnit.arson.config.ServerConfigModal;
import net.justsunnit.arson.util.CountdownBuilder;
import net.justsunnit.arson.util.JsonSaveHandler;
import net.justsunnit.fern.Fern;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.List;

public class MaintenanceCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("maintenance")
                        .requires(source -> !source.isExecutedByPlayer() ||
                                source.hasPermissionLevel(4) || Fern.check(source, "Admin.maintenance") || Fern.checkGroup(source, "Admin"))
                        .then(CommandManager.argument("type" ,StringArgumentType.greedyString()).suggests((context, builder) ->
                                builder.suggest("on").suggest("off").suggest("status").buildFuture())
                                .executes(MaintenanceCommand::run))));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String _type = StringArgumentType.getString(context, "type").toLowerCase();
        String type;

        if (_type.contains("on")) {
            type = "on";
        } else if (_type.contains("off")) {
            type = "off";
        } else if (_type.contains("status")) {
            type = "status";
        } else {
            context.getSource().sendError(Text.of("[ArsonUtils] Invalid argument. Use 'on', 'off', or 'status'."));
            return 0;
        }

        switch (type) {
            case "on":
                List<String> admins = ArsonServer.config.admins();
                if (admins.isEmpty()) {
                    context.getSource().sendError(Text.of("[ArsonUtils] No admins configured. Please set at least one admin before enabling maintenance mode."));
                    return 0;
                }

                Object[] args = new Object[4];
                args[0] = admins;
                args[1] = context;
                args[2] = ArsonServer.config;
                args[3] = JsonSaveHandler.getAllPlayers();

                CountdownBuilder.Builder countdownBuilder = new CountdownBuilder.Builder();
                countdownBuilder.setDuration(60)
                        .setArgs(args)
                        .setOnTick((secondsLeft, _args) -> {
                            long seconds = secondsLeft % 60;
                            long minutes = (secondsLeft / 60) % 60;
                            for (ServerPlayerEntity plr : context.getSource().getWorld().getPlayers()) {
                                plr.sendMessage(Text.of("[ArsonUtils] Maintenance mode will be enabled in " + minutes + " minutes and " + seconds + " seconds."), false);
                            }
                        })
                    .setOnFinish((ticks, _args) -> {
                        CommandContext<ServerCommandSource> ctx = (CommandContext<ServerCommandSource>) _args[1];
                        List<String> adms = (List<String>) _args[0];
                        ArsonConfig cng = (ArsonConfig) _args[2];
                        HashMap<String,String> allPlayers = (HashMap<String, String>) _args[3];

                        cng.maintenanceMode(true);

                        for(ServerPlayerEntity player : ctx.getSource().getWorld().getPlayers()) {
                            if (player != null && !adms.contains(player.getName().getString())) {
                                player.networkHandler.disconnect(Text.of("[ArsonUtils] The server is now in maintenance mode. Please try again later."));
                            }
                        }
                });

                countdownBuilder.build().exe();

            case "off":
                ArsonServer.config.maintenanceMode(false);
                context.getSource().sendFeedback(() -> Text.of("[ArsonUtils] Maintenance mode is now OFF"), false);
                break;
            case "status":
                boolean status = ArsonServer.config.maintenanceMode();
                context.getSource().sendFeedback(() -> Text.of("[ArsonUtils] Maintenance mode is currently " + (status ? "ON" : "OFF")), false);
                break;

            default:
                context.getSource().sendError(Text.of("[ArsonUtils] Invalid argument. Use 'on', 'off', or 'status'."));
                return 0;
        }

        return 1;
    }
}
