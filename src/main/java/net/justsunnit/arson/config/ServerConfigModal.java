package net.justsunnit.arson.config;

import io.wispforest.owo.config.annotation.Config;

import java.util.List;

@Config(name = "server_config", wrapperName = "ServerConfig")
public class ServerConfig {
    public String alertWebhookUrl = "";
    public String avatarUrl = "https://cdn.discordapp.com/embed/avatars/index.png";
    public String alertUsername = "Arson";
    public boolean webhookEnabled = false;
    public String defaultMessage = "COMMIT ARSON";
    public String maintenanceMessage = "Server is currently under maintenance, please try again later.";
    public boolean maintenanceMode = false;

    public List<String>

}
