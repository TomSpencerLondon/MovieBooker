package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovieProgram {
    private Long scheduleId;
    private final LocalDateTime scheduleDate;
    private final Integer seats;
    private Movie movie;

    public MovieProgram(Long scheduleId, LocalDateTime scheduleDate, Integer seats, Movie movie) {
        this.scheduleId = scheduleId;
        this.scheduleDate = scheduleDate;
        this.seats = seats;
        this.movie = movie;
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

    public String scheduleDateText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return scheduleDate.format(formatter);
    }

    public Integer seats() {
        return seats;
    }

    public String seatsText() {
        return seats > 0 ? seats + " available" : "Sold Out!";
    }

    public Movie movie() {
        return movie;
    }
}
