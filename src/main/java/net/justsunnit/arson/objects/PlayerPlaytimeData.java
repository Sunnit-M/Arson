package net.justsunnit.arson.objects;

public class PlayerPlaytimeData{
    public long WeekPlaytime;
    public long MonthPlaytime;
    public long TotalPlaytime;

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

    public void ResetWeekPlaytime(){
        this.WeekPlaytime = 0;
    }

    public void ResetMonthPlaytime(){
        this.MonthPlaytime = 0;
    }
}
