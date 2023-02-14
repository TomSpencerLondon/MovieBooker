package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movie_programs")
public class MovieProgramDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long scheduleId;

    public void setScheduleId(Long id) {
        this.scheduleId = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    private LocalDateTime scheduleDate;

    private Integer seats;

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}
