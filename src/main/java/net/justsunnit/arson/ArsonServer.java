package net.justsunnit.arson;

import net.fabricmc.api.DedicatedServerModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.justsunnit.arson.commands.CommandRegistry;
import net.justsunnit.arson.config.ArsonConfig;
import net.justsunnit.arson.event.*;
import net.justsunnit.arson.util.*;
import net.minecraft.server.MinecraftServer;

public class ArsonServer implements DedicatedServerModInitializer {

	public static final ArsonConfig config = ArsonConfig.createAndLoad();

	public static MinecraftServer server;

	public Thread playtimeLoggerThread = new Thread(() -> {
		while (true) {
			try {
				Thread.sleep(1000 * 60 * 60);
				PlaytimeLogger.UpdateTimeStamps();

			} catch (InterruptedException e) {
				break;
			}
		}
	});

	@Override
	public void onInitializeServer() {
		DirectoryManager.checkDir();

		JsonSaveHandler.initializeJsonData();
		PlaytimeLogger.initializeLogger();
		PlaytimeLogger.UpdateTimeStamps();

		EventRegistries.registerEvents_Server();
		CommandRegistry.registerCommands();

		ServerLifecycleEvents.SERVER_STARTED.register(s -> {
			s.setMotd(config.maintenanceMode() ? (String) config.maintenanceMessage() : (String) config.defaultMessage());
			server = s;
		});

		ServerLifecycleEvents.SERVER_STOPPED.register(s ->{
			playtimeLoggerThread.interrupt();
		});

		playtimeLoggerThread.start();

		Arson.LOGGER.info("[ArsonServer] Initialized");
	}
}