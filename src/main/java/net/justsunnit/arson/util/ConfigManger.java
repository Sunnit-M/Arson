package net.justsunnit.arson.util;

import net.justsunnit.arson.Arson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigManger {
    public static List<Runnable> OnUpdateSubscribers = new ArrayList<>();
    public static final Path CONFIG_FOLER_PATH = new File("Arson_Config").toPath();
    public static final Path CONFIG_FILE = new File("Arson_Config/config.json").toPath();
    private Map<String, Object> configData;
    private final Gson gson = new Gson();

    public ConfigManger() {
        LoadConfig();
    }

    public void LoadConfig() {
        try {
            if (!CONFIG_FILE.toFile().exists()) {
                CreateDefault();
            }
            try (Reader reader = Files.newBufferedReader(CONFIG_FILE)) {
                Type type = new TypeToken<Map<String, Object>>() {}.getType();
                configData = gson.fromJson(reader, type);
            }
        } catch (IOException e) {
            Arson.LOGGER.info("[ArsonUtils] Failed to load config.json");
        }
    }

    private void CreateDefault() throws IOException {
        String defaultConfig = """
        {
          "commandWebhookUrl": "",
          "playtimeWebhookUrl": "",
          "avatarUrl": "https://cdn.discordapp.com/embed/avatars/index.png",
          "CommandLoggerUsername": "Command Logger",
          "PlaytimeLoggerUsername": "Playtime Logger",
          "enabled": true,
          "defaultMessage": "Welcome to the server! Enjoy your stay!",
          "maintenanceMessage": "The server is currently under maintenance. Please check back later.",
          "maintenanceMode": false,
          "admins": ["Sunnit_m"]
        }
        """;

        try (Writer writer = Files.newBufferedWriter(CONFIG_FILE)) {
            writer.write(defaultConfig);
        }
    }

    public Map<String, Object> GetConfig() {
        LoadConfig();
        return configData;
    }

    public ArrayList<String> getAdmins() {
        LoadConfig();
        return (ArrayList<String>) configData.getOrDefault("admins", new ArrayList<>());
    }

    public boolean isAdmin(String playerName) {
        LoadConfig();
        ArrayList<String> admins = (ArrayList<String>) configData.getOrDefault("admins", new ArrayList<String>());
        return admins.contains(playerName);
    }

    public void setMaintenanceMode(boolean enabled) {
        Map<String, Object> config = Arson.config.GetConfig();
        config.put("maintenanceMode", enabled);
        Arson.config.OverwriteConfig(config);

        Arson.server.setMotd(enabled ? (String) config.get("maintenanceMessage") : (String) config.get("defaultMessage"));
    }

    public void OverwriteConfig(Map<String, Object> newConfig) {
        try (Writer writer = Files.newBufferedWriter(CONFIG_FILE)) {
            gson.toJson(newConfig, writer);
        } catch (IOException e) {
            Arson.LOGGER.info("[ArsonUtils] Failed to overwrite config.json");
        }
        LoadConfig();
        for (Runnable r : OnUpdateSubscribers) {
            r.run();
        }
    }

    public boolean isMaintenanceMode() {
        LoadConfig();
        return (boolean) configData.getOrDefault("maintenanceMode", false);
    }
}