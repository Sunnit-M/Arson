package net.justsunnit.arson.util;

import com.eduardomcb.discord.webhook.*;
import com.eduardomcb.discord.webhook.models.*;
import net.justsunnit.arson.*;

import static net.justsunnit.arson.util.TextFormatter.formatMonthTimeLeaderBoard;
import static net.justsunnit.arson.util.TextFormatter.formatWeekTimeLeaderBoard;


public class WebHookFormatter {

    public static String modWebhookUrl;
    public static String commandWebhookUrl;
    public static String playtimeWebhookUrl;

    public static String avatarURL;

    public static String PlaytimeUsername;
    public static String CommandUsername;
    public static String HandshakeUsername;

    public static void InitializeWebHook() {
        UpdateProfiles();
        ConfigManger.OnUpdateSubscribers.add(WebHookFormatter::UpdateProfiles);
    }

    public static void UpdateProfiles() {
        avatarURL = Arson.config.GetConfig().getOrDefault("config.webhook.avatarUrl","https://cdn.discordapp.com/embed/avatars/index.png").toString();
        modWebhookUrl = Arson.config.GetConfig().getOrDefault("config.webhook.modWebhookUrl", "").toString();
        commandWebhookUrl = Arson.config.GetConfig().getOrDefault("config.webhook.commandWebhookUrl", "").toString();
        playtimeWebhookUrl = Arson.config.GetConfig().getOrDefault("config.webhook.playtimeWebhookUrl", "").toString();
        PlaytimeUsername = Arson.config.GetConfig().getOrDefault("config.webhook.PlaytimeLoggerUsername","Playtime Logger").toString();
        CommandUsername = Arson.config.GetConfig().getOrDefault("config.webhook.CommandLoggerUsername","Command Logger").toString();
        HandshakeUsername = Arson.config.GetConfig().getOrDefault("config.webhook.HandshakeLoggerUsername", "Handshake Logger").toString();
    }

    public static void SendCommandHook(String content) {
        WebhookManager webhook = new WebhookManager();
        Message message = new Message().setUsername(CommandUsername);
        webhook.setChannelUrl(modWebhookUrl);
        message.setAvatarUrl(avatarURL);
        message.setContent(content);
        webhook.setMessage(message);
        if(modWebhookUrl.matches("https://discord\\.com/api/webhooks/[0-9]+/[A-Za-z0-9_\\-]+"))
        {
            webhook.setChannelUrl(modWebhookUrl);

            webhook.setListener(new WebhookClient.Callback() {
                @Override
                public void onSuccess(String s) {
                    Arson.LOGGER.info("[ArsonUtils] Mod Log sent successfully: " + s);
                }

                @Override
                public void onFailure(int i, String s) {
                    Arson.LOGGER.error("[ArsonUtils] Failed to send Mod Log: " + s);
                }
            });

            webhook.exec();
        }
        else
        {
            Arson.LOGGER.info(message.getContent());
        }
    }


    public static void SendPlaytimeMonthLog(){
        WebhookManager webhook = new WebhookManager();
        Message message = new Message().setUsername(PlaytimeUsername);
        webhook.setChannelUrl(playtimeWebhookUrl);
        message.setAvatarUrl(avatarURL);
        message.setContent(formatMonthTimeLeaderBoard());
        webhook.setMessage(message);
        if(playtimeWebhookUrl.matches("https://discord\\.com/api/webhooks/[0-9]+/[A-Za-z0-9_\\-]+"))
        {
            webhook.setChannelUrl(playtimeWebhookUrl);

            webhook.setListener(new WebhookClient.Callback() {
                @Override
                public void onSuccess(String s) {
                    Arson.LOGGER.info("[ArsonUtils] Spark Log sent successfully: " + s);
                }

                @Override
                public void onFailure(int i, String s) {
                    Arson.LOGGER.error("[ArsonUtils] Failed to send Spark Log: " + s);
                }
            });

            webhook.exec();
        }
        else
        {
            Arson.LOGGER.info(message.getContent());
        }
    }

    public static void SendPlaytimeWeekLog(){
        WebhookManager webhook = new WebhookManager();
        Message message = new Message().setUsername(PlaytimeUsername);
        webhook.setChannelUrl(playtimeWebhookUrl);
        message.setAvatarUrl(avatarURL);
        message.setContent(formatWeekTimeLeaderBoard());
        webhook.setMessage(message);
        if(playtimeWebhookUrl.matches("https://discord\\.com/api/webhooks/[0-9]+/[A-Za-z0-9_\\-]+"))
        {
            webhook.setChannelUrl(playtimeWebhookUrl);

            webhook.setListener(new WebhookClient.Callback() {
                @Override
                public void onSuccess(String s) {
                    Arson.LOGGER.info("[Arson] Spark Log sent successfully: " + s);
                }

                @Override
                public void onFailure(int i, String s) {
                    Arson.LOGGER.error("[Arson] Failed to send Spark Log: " + s);
                }
            });

            webhook.exec();
        }
        else
        {
            Arson.LOGGER.info(message.getContent());
        }
    }
}
