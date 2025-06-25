package net.justsunnit.arson.util;

import com.eduardomcb.discord.webhook.*;
import com.eduardomcb.discord.webhook.models.*;
import net.justsunnit.arson.*;
import net.justsunnit.arson.objects.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class WebHookFormatter {

    public static String webhookURL;

    public static String avatarURL;

    public static String PlaytimeUsername;
    public static String CommandUsername;
    public static String HandshakeUsername;

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static void InitializeWebHook() {
        UpdateProfiles();
        ConfigManger.OnUpdateSubscribers.add(WebHookFormatter::UpdateProfiles);
    }

    public static void UpdateProfiles() {
        avatarURL = Arson.config.GetConfig().getOrDefault("config.webhook.avatarUrl","https://cdn.discordapp.com/embed/avatars/index.png").toString();
        webhookURL = Arson.config.GetConfig().getOrDefault("config.webhook.url", "").toString();
        PlaytimeUsername = Arson.config.GetConfig().getOrDefault("config.webhook.PlaytimeLoggerUsername","Playtime Logger").toString();
        CommandUsername = Arson.config.GetConfig().getOrDefault("config.webhook.CommandLoggerUsername","Command Logger").toString();
        HandshakeUsername = Arson.config.GetConfig().getOrDefault("config.webhook.HandshakeLoggerUsername", "Handshake Logger").toString();
    }

    public static void SendPlaytimeMonthLog(){
        WebhookManager webhook = new WebhookManager();
        Message message = new Message().setUsername(PlaytimeUsername);
        webhook.setChannelUrl(webhookURL);
        message.setAvatarUrl(avatarURL);
        message.setContent(formatMonthTimeLeaderBoard());
        webhook.setMessage(message);
        if(webhookURL.matches("https://discord\\.com/api/webhooks/[0-9]+/[A-Za-z0-9_\\-]+"))
        {
            webhook.setChannelUrl(webhookURL);

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
        webhook.setChannelUrl(webhookURL);
        message.setAvatarUrl(avatarURL);
        message.setContent(formatWeekTimeLeaderBoard());
        webhook.setMessage(message);
        if(webhookURL.matches("https://discord\\.com/api/webhooks/[0-9]+/[A-Za-z0-9_\\-]+"))
        {
            webhook.setChannelUrl(webhookURL);

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

    public static String formatWeekTimeLeaderBoard(){
        List<Map.Entry<String, PlayerPlaytimeData>> sortedList = new ArrayList<>(JsonSaveHandler.GetPlayerPlaytimeData().entrySet());
        sortedList.sort((a, b) -> Long.compare(b.getValue().WeekPlaytime, a.getValue().WeekPlaytime));

        StringBuilder leaderboard = new StringBuilder("Weekly Playtime Leaderboard for " + LocalDate.now().format(formatter) + "\n");
        int rank = 1;
        for (Map.Entry<String, PlayerPlaytimeData> entry : sortedList) {
            leaderboard.append(String.format("%d. %s - %d minutes\n", rank++, entry.getKey(), entry.getValue().WeekPlaytime));
        }

        return leaderboard.toString();
    }

    public static String formatMonthTimeLeaderBoard(){
        List<Map.Entry<String, PlayerPlaytimeData>> sortedList = new ArrayList<>(JsonSaveHandler.GetPlayerPlaytimeData().entrySet());
        sortedList.sort((a, b) -> Long.compare(b.getValue().MonthPlaytime, a.getValue().MonthPlaytime));

        StringBuilder leaderboard = new StringBuilder("Monthly Playtime Leaderboard" + LocalDate.now().format(formatter) +"\n");
        int rank = 1;
        for (Map.Entry<String, PlayerPlaytimeData> entry : sortedList) {
            leaderboard.append(String.format("%d. %s - %d minutes\n", rank++, entry.getKey(), entry.getValue().MonthPlaytime));
        }

        return leaderboard.toString();
    }
}
