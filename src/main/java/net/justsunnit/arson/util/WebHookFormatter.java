package net.justsunnit.arson.util;

import net.justsunnit.arson.Arson;
import net.justsunnit.arson.classes.PlayerPlaytimeData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebHookFormatter {

    public static String webhookURL;

    public static String avatarURL;

    public static String PlaytimeUsername;
    public static String CommandUsername;
    public static String HandshakeUsername;

    public static DiscordWebhook webhook;

    public static void InitializeWebHook() {
        UpdateProfiles();
        ConfigManger.OnUpdateSubscribers.add(WebHookFormatter::UpdateProfiles);
    }

    public static void UpdateProfiles() {
        avatarURL = Arson.config.GetConfig().get("config.webhook.avatarUrl").toString();
        webhookURL = Arson.config.GetConfig().get("config.webhook.url").toString();
        PlaytimeUsername = Arson.config.GetConfig().get("config.webhook.PlaytimeLoggerUsername").toString();
        CommandUsername = Arson.config.GetConfig().get("config.webhook.CommandLoggerUsername").toString();
        HandshakeUsername = Arson.config.GetConfig().get("config.webhook.HandshakeLoggerUsername").toString();

        webhook = new DiscordWebhook(webhookURL);
    }

    public static void SendPlaytimeMonthLog(){
        webhook = new DiscordWebhook(webhookURL);
        webhook.setUsername(PlaytimeUsername);
        webhook.setAvatarUrl(avatarURL);
        webhook.setContent("`Monthly Playtime" + LocalDate.now().toString());
        webhook.addEmbed(new DiscordWebhook.EmbedObject().addField("Logs", formatMonthTimeLeaderBoard(), false));
        try {
            webhook.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SendPlaytimeWeekLog(){
        webhook = new DiscordWebhook(webhookURL);
        webhook.setUsername(PlaytimeUsername);
        webhook.setAvatarUrl(avatarURL);
        webhook.setContent("`Weekly Playtime" + LocalDate.now().toString());
        webhook.addEmbed(new DiscordWebhook.EmbedObject().addField("Logs", formatWeekTimeLeaderBoard(), false));
        try {
            webhook.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formatWeekTimeLeaderBoard(){
        List<Map.Entry<String, PlayerPlaytimeData>> sortedList = new ArrayList<>(JsonSaveHandler.GetPlayerPlaytimeData().entrySet());
        sortedList.sort((a, b) -> Long.compare(b.getValue().WeekPlaytime, a.getValue().WeekPlaytime));

        StringBuilder leaderboard = new StringBuilder("🏆 Weekly Playtime Leaderboard 🏆\n");
        int rank = 1;
        for (Map.Entry<String, PlayerPlaytimeData> entry : sortedList) {
            leaderboard.append(String.format("%d. %s - %d minutes\n", rank++, entry.getKey(), entry.getValue().WeekPlaytime));
        }

        return leaderboard.toString();
    }

    public static String formatMonthTimeLeaderBoard(){
        List<Map.Entry<String, PlayerPlaytimeData>> sortedList = new ArrayList<>(JsonSaveHandler.GetPlayerPlaytimeData().entrySet());
        sortedList.sort((a, b) -> Long.compare(b.getValue().MonthPlaytime, a.getValue().MonthPlaytime));

        StringBuilder leaderboard = new StringBuilder("🏆 Monthly Playtime Leaderboard 🏆\n");
        int rank = 1;
        for (Map.Entry<String, PlayerPlaytimeData> entry : sortedList) {
            leaderboard.append(String.format("%d. %s - %d minutes\n", rank++, entry.getKey(), entry.getValue().MonthPlaytime));
        }

        return leaderboard.toString();
    }
}
