package net.justsunnit.arson.util;

import net.justsunnit.arson.objects.PlayerPlaytimeData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextFormatter {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static String formatDuration(long totalSeconds) {
        long days = totalSeconds / 86400;
        long hours = (totalSeconds % 86400) / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
    }

    public static String formatWeekTimeLeaderBoard(){
        List<Map.Entry<String, PlayerPlaytimeData>> sortedList = new ArrayList<>(JsonSaveHandler.GetPlayerPlaytimeData().entrySet());
        sortedList.sort((a, b) -> Long.compare(b.getValue().WeekPlaytime, a.getValue().WeekPlaytime));

        StringBuilder leaderboard = new StringBuilder("Weekly Playtime Leaderboard for " + LocalDate.now().format(DATE_TIME_FORMATTER) + "\n");
        int rank = 1;
        for (Map.Entry<String, PlayerPlaytimeData> entry : sortedList) {
            leaderboard.append(String.format("%d. %s - %d minutes\n", rank++, entry.getKey(), entry.getValue().WeekPlaytime));
        }

        return leaderboard.toString();
    }

    public static String formatMonthTimeLeaderBoard(){
        List<Map.Entry<String, PlayerPlaytimeData>> sortedList = new ArrayList<>(JsonSaveHandler.GetPlayerPlaytimeData().entrySet());
        sortedList.sort((a, b) -> Long.compare(b.getValue().MonthPlaytime, a.getValue().MonthPlaytime));

        StringBuilder leaderboard = new StringBuilder("Monthly Playtime Leaderboard" + LocalDate.now().format(DATE_TIME_FORMATTER) +"\n");
        int rank = 1;
        for (Map.Entry<String, PlayerPlaytimeData> entry : sortedList) {
            leaderboard.append(String.format("%d. %s - %d minutes\n", rank++, entry.getKey(), entry.getValue().MonthPlaytime));
        }

        return leaderboard.toString();
    }
}
