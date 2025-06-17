package net.justsunnit.arson.objects;

import java.time.LocalDateTime;

public class ServerTimeStampData {
    public LocalDateTime LoggedWeek;
    public LocalDateTime LoggedMonth;

    public ServerTimeStampData(LocalDateTime LoggedWeek, LocalDateTime LoggedMonth) {
        this.LoggedWeek = LoggedWeek;
        this.LoggedMonth = LoggedMonth;
    }
}