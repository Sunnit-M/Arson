package net.justsunnit.arson.util;

import net.justsunnit.arson.Arson;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigManger {
    public static List<Runnable> OnUpdateSubscribers = new ArrayList<>();
    public final Path CONFIG_FILE = new File("Arson_Config/config.yml").toPath();
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
            Arson.LOGGER.info("[Arson] Failed to load config.yml");
        }
    }

    private void CreateDefault() throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/config.yml");
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

    public void OverwriteConfig(Map<String, Object> newConfig) {
        try (Writer writer = Files.newBufferedWriter(CONFIG_FILE)) {
            Yaml yaml = new Yaml();
            yaml.dump(newConfig, writer);
        } catch (IOException e) {
            Arson.LOGGER.info("[Arson] Failed to overwrite config.yml");
        }
        LoadConfig();
        for(Runnable r : OnUpdateSubscribers){
            r.run();
        }
    }
}
