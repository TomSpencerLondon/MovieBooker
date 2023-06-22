package com.tomspencerlondon.moviebooker.admin.hexagon.domain;


import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminProgram {
    private final Screen screen;
    private Long scheduleId;
    private final LocalDateTime scheduleDate;
    private AdminMovie movie;
    private final BigDecimal seatPrice;

    public AdminProgram(Long scheduleId, LocalDateTime scheduleDate, Screen screen, AdminMovie movie, BigDecimal seatPrice) {
        this.scheduleId = scheduleId;
        this.scheduleDate = scheduleDate;
        this.screen = screen;
        this.movie = movie;
        this.seatPrice = seatPrice;
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

    public Integer totalSeats() {
        return screen.numberOfSeats();
    }

    public AdminMovie movie() {
        return movie;
    }

    public BigDecimal price() {
        return seatPrice;
    }

}

