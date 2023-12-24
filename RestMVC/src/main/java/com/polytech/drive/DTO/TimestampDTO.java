package com.polytech.drive.DTO;

import java.time.LocalDateTime;

public class TimestampDTO {
    private final LocalDateTime localDateTime;

    public TimestampDTO(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getLocalDateTimeInCorrectFormat() {
        return  localDateTime.toString().replaceAll(":", ".");
    }
}
