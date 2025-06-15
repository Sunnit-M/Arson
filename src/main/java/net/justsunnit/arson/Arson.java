package net.justsunnit.arson;

import net.fabricmc.api.ModInitializer;

import net.justsunnit.arson.event.PlayerJoinLeaveEvents;
import net.justsunnit.arson.playtime_logging.PlaytimeLogger;
import net.justsunnit.arson.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arson implements ModInitializer {
	public static final String MOD_ID = "arson";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final String CommandStarter = "arson";

	public static DiscordWebhook playtimeLoggerWebhook;
	public static DiscordWebhook handshakeLoggerWebhook;
	public static DiscordWebhook commandExecuteLoggerWebhook;
	public static ConfigManger config;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		DirectoryManager.checkDir();
		config = new ConfigManger();

		ModRegistries.register();
		JsonSaveHandler.initializeJsonData();
		PlayerJoinLeaveEvents.registerEvents();
		PlaytimeLogger.initializeLogger();
		PlaytimeLogger.UpdateTimeStamps();
		WebHookFormatter.InitializeWebHook();

		playtimeLoggerWebhook = new DiscordWebhook(config.GetConfig().get("webhooks.PlaytimeLogsURL").toString());
		handshakeLoggerWebhook = new DiscordWebhook(config.GetConfig().get("webhooks.HandshakeLogsURL").toString());
		commandExecuteLoggerWebhook = new DiscordWebhook(config.GetConfig().get("webhooks.CommandExecuteLogsURL").toString());






		LOGGER.info("[Arson] Initialized");
	}
}