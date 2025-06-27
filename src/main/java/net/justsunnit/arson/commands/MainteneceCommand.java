package net.justsunnit.arson.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sun.jdi.connect.Connector;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.automod.BannedData;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.util.ConfigManger;
import net.justsunnit.arson.util.CountdownBuilder;
import net.justsunnit.arson.util.JsonSaveHandler;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.UserCache;

import javax.naming.Context;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainteneceCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("arson")
                .then(CommandManager.literal("maintenece")
                        .requires(source -> !source.isExecutedByPlayer() ||
                                source.hasPermissionLevel(2) || Arson.config.isAdmin(source.getName()))
                        .then(CommandManager.argument("type" ,StringArgumentType.greedyString()).suggests((context, builder) ->
                                builder.suggest("on").suggest("off").suggest("status").buildFuture())
                                .executes(MainteneceCommand::run))));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String type = StringArgumentType.getString(context, "type").toLowerCase();

        switch (type) {
            case "on":
                List<String> admins = Arson.config.getAdmins();
                if (admins.isEmpty()) {
                    context.getSource().sendError(Text.of("[ArsonUtils] No admins configured. Please set at least one admin before enabling maintenance mode."));
                    return 0;
                }

                Object[] args = new Object[4];
                args[0] = admins;
                args[1] = context;
                args[2] = Arson.config;
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
                        ConfigManger cng = (ConfigManger) _args[2];
                        HashMap<String,String> allPlayers = (HashMap<String, String>) _args[3];

                        cng.setMaintenanceMode(true);

                        for(ServerPlayerEntity player : ctx.getSource().getWorld().getPlayers()) {
                            if (player != null && !adms.contains(player.getName().getString())) {
                                player.networkHandler.disconnect(Text.of("[ArsonUtils] The server is now in maintenance mode. Please try again later."));
                            }
                        }
                });

                countdownBuilder.build().exe();

            case "off":
                Arson.config.setMaintenanceMode(false);
                context.getSource().sendFeedback(() -> Text.of("[ArsonUtils] Maintenance mode is now OFF"), false);
                break;
            case "status":
                boolean status = Arson.config.isMaintenanceMode();
                context.getSource().sendFeedback(() -> Text.of("[ArsonUtils] Maintenance mode is currently " + (status ? "ON" : "OFF")), false);
                break;

            default:
                context.getSource().sendError(Text.of("[ArsonUtils] Invalid argument. Use 'on', 'off', or 'status'."));
                return 0;
        }

        return 1;
    }
}
