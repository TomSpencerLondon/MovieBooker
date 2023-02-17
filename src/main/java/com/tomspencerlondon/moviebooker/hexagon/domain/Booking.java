package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.time.LocalDateTime;

public class Booking {
    private Long bookingId;
    private Long scheduleId;
    private int numberOfSeatsBooked;

    private final String filmName;
    private final LocalDateTime bookingTime;

    public Booking(String filmName, LocalDateTime bookingTime, Long scheduleId, int numberOfSeatsBooked) {
        this.filmName = filmName;
        this.bookingTime = bookingTime;
        this.scheduleId = scheduleId;
        this.numberOfSeatsBooked = numberOfSeatsBooked;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Long scheduleId() {
        return scheduleId;
    }

    public String filmName() {
        return filmName;
    }

    public LocalDateTime bookingTime() {
        return bookingTime;
    }

    public int numberOfSeatsBooked() {
        return numberOfSeatsBooked;
    }
}
