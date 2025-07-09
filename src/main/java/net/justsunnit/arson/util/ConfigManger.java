package net.justsunnit.arson.util;

import net.justsunnit.arson.ArsonServer;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigManger {
    public static List<Runnable> OnUpdateSubscribers = new ArrayList<>();
    public static final Path CONFIG_FOLER_PATH = new File("Arson_Config").toPath();
    public static final Path CONFIG_FILE = new File("Arson_Config/config.yml").toPath();
    private Map<String, Object> configData;

    public ConfigManger() {
        LoadConfig();
    }

    public void LoadConfig() {
        try {
            if (!CONFIG_FILE.toFile().exists()) {
                CreateDefault();
            }
            Yaml yaml = new Yaml();
            try (InputStream input = Files.newInputStream(CONFIG_FILE)) {
                configData = yaml.load(input);
            }
        } catch (IOException e) {
            ArsonServer.LOGGER.info("[ArsonUtils] Failed to load config.yml");
        }
    }

    private void CreateDefault() throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.yml");
             OutputStream output = Files.newOutputStream(CONFIG_FILE)) {
            if (input != null) {
                input.transferTo(output);
            }
        }
    }

    public Map<String,Object> GetConfig() {
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
        Map<String, Object> config = ArsonServer.config.GetConfig();
        config.put("maintenanceMode", enabled);
        ArsonServer.config.OverwriteConfig(config);

        ArsonServer.server.setMotd(enabled ? (String) config.get("maintenanceMessage") : (String) config.get("defaultMessage"));
    }

    public void OverwriteConfig(Map<String, Object> newConfig) {
        try (Writer writer = Files.newBufferedWriter(CONFIG_FILE)) {
            Yaml yaml = new Yaml();
            yaml.dump(newConfig, writer);
        } catch (IOException e) {
            ArsonServer.LOGGER.info("[ArsonUtils] Failed to overwrite config.yml");
        }
        LoadConfig();
        for(Runnable r : OnUpdateSubscribers){
            r.run();
        }
    }

    public boolean isMaintenanceMode() {
        LoadConfig();
        return (boolean) configData.getOrDefault("maintenanceMode", false);
    }
}
