package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovieProgram {
    private Long scheduleId;
    private final LocalDateTime scheduleDate;
    private final Integer seats;

    public MovieProgram(Long scheduleId, LocalDateTime scheduleDate, Integer seats) {
        this.scheduleId = scheduleId;
        this.scheduleDate = scheduleDate;
        this.seats = seats;
    }

    public void setScheduleId(Long id) {
        this.scheduleId = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public String scheduleDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return scheduleDate.format(formatter);
    }

    public String seats() {
        return seats > 0 ? seats + " available" : "Sold Out!";
    }
}
