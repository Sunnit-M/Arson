package net.justsunnit.arson.objects;

import java.time.LocalDateTime;

public class BannedPlayer {
    public BannedPlayer (String name, String reason, LocalDateTime banDate, long banSeconds, boolean timeless) {
        this.Name = name;
        this.Reason = new StringBuilder(reason);
        this.BanDate = banDate;
        this.BanSeconds = banSeconds;
        this.timeless = timeless;
    }

    public String Name;
    public StringBuilder Reason;
    public LocalDateTime BanDate;
    public long BanSeconds;
    public boolean timeless;
}
