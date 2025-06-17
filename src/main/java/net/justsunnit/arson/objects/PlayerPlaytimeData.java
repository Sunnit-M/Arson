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

    public void ResetWeekPlaytime(){
        this.WeekPlaytime = 0;
    }

    public void ResetMonthPlaytime(){
        this.MonthPlaytime = 0;
    }
}
