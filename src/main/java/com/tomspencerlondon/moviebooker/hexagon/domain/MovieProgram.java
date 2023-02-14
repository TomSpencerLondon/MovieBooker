package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.time.LocalDateTime;

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

    public LocalDateTime scheduleDate() {
        return scheduleDate;
    }

    public Integer getSeats() {
        return seats;
    }
}
