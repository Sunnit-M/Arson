package net.justsunnit.arson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.justsunnit.arson.commands.CommandRegistry;
import net.justsunnit.arson.event.*;
import net.justsunnit.arson.util.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arson implements ModInitializer {
	public static final String MOD_ID = "arson";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ConfigManger config;

	public static MinecraftServer server;

	@Override
	public void onInitialize() {;
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
		});

		LOGGER.info("[Arson] Initialized");
	}
}