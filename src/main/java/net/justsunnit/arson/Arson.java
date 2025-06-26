package net.justsunnit.arson;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.justsunnit.arson.commands.CommandRegistry;
import net.justsunnit.arson.event.*;
import net.justsunnit.arson.util.*;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arson implements ModInitializer {
	public static final String MOD_ID = "arson";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final String CommandStarter = "arson";

	public static WebHookFormatter playtimeLoggerWebhook;
	public static WebHookFormatter handshakeLoggerWebhook;
	public static WebHookFormatter commandExecuteLoggerWebhook;
	public static ConfigManger config;

	public static MinecraftServer server;


	@Override
	public void onInitialize() {
		DirectoryManager.checkDir();
		config = new ConfigManger();

		JsonSaveHandler.initializeJsonData();
		PlaytimeLogger.initializeLogger();
		PlaytimeLogger.UpdateTimeStamps();
		WebHookFormatter.InitializeWebHook();

		EventRegistries.registerEvents();
		CommandRegistry.registerCommands();

		ServerLifecycleEvents.SERVER_STARTED.register(s -> {
			server = s;
			System.out.println("Server started: " + server.getName());
		});

		LOGGER.info("[Arson] Initialized");
	}
}