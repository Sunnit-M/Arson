package net.justsunnit.arson.config;

import io.wispforest.owo.config.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "arson-config", wrapperName = "ArsonConfig")
public class ServerConfigModal {
    public webhookSettings webhook = new webhookSettings();
    public static class webhookSettings {
        public String alertWebhookUrl = "";
        public String avatarUrl = "https://cdn.discordapp.com/embed/avatars/index.png";
        public String alertUsername = "Arson";
        public boolean webhookEnabled = false;
    }

    public String defaultMessage = "COMMIT ARSON";
    public String maintenanceMessage = "Server is currently under maintenance, please try again later.";
    public boolean maintenanceMode = false;

    public List<String> admins = new ArrayList<>();
    public List<String> keyedUsers = new ArrayList<>();

    public modWhitelists modWhitelist = new modWhitelists();
    public static class modWhitelists {
        public List<String> requiredMods = new ArrayList<>();
        public List<String> optionalMods = new ArrayList<>();
        public boolean modWhitelistEnabled = false;
    }
}
