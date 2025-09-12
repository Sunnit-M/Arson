package net.justsunnit.arson.objects;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;

public class PlayerPlaytimeData{
    public long WeekPlaytime;
    public long MonthPlaytime;
    public long TotalPlaytime;

    public PlayerPlaytimeData(long week, long month, long total) {
        WeekPlaytime = week;
        MonthPlaytime = month;
        TotalPlaytime = total;
    }

    public long getMonthPlaytime() {
        return MonthPlaytime;
    }

    public long getWeekPlaytime() {
        return WeekPlaytime;
    }

    public long getTotalPlaytime() {
        return TotalPlaytime;
    }

    public PlayerPlaytimeData(){
        this.WeekPlaytime = 0;
        this.MonthPlaytime = 0;
        this.TotalPlaytime = 0;
    }

    public void AddPlaytime(long playtime){
        this.WeekPlaytime += playtime;
        this.MonthPlaytime += playtime;
        this.TotalPlaytime += playtime;
    }

    public String getFormattedPlaytime(){
        long hours = this.TotalPlaytime / 3600;
        long minutes = (this.TotalPlaytime % 3600) / 60;
        long seconds = this.TotalPlaytime % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String formattedPlaytime(long playtime){
        long hours = playtime / 3600;
        long minutes = (playtime % 3600) / 60;
        long seconds = playtime % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void ResetWeekPlaytime(){
        this.WeekPlaytime = 0;
    }

    public void ResetMonthPlaytime(){
        this.MonthPlaytime = 0;
    }
}
