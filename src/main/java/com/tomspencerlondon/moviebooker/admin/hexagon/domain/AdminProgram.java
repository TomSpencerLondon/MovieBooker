package com.tomspencerlondon.moviebooker.admin.hexagon.domain;


import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminProgram {
    private Long scheduleId;
    private final LocalDateTime scheduleDate;
    private final Integer totalSeats;
    private Movie movie;
    private final BigDecimal seatPrice;

    public AdminProgram(Long scheduleId, LocalDateTime scheduleDate, Integer totalSeats, Movie movie, BigDecimal seatPrice) {
        this.scheduleId = scheduleId;
        this.scheduleDate = scheduleDate;
        this.totalSeats = totalSeats;
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
        return totalSeats;
    }

    public Movie movie() {
        return movie;
    }

    public BigDecimal price() {
        return seatPrice;
    }

}

