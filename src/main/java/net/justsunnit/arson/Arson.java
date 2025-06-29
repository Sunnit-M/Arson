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
			s.setMotd(config.isMaintenanceMode() ? (String) config.GetConfig().get("maintenanceMessage") : (String) config.GetConfig().get("defaultMessage"));
			server = s;
		});

		LOGGER.info("[Arson] Initialized");
	}
}