package com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Booking {
    private Long bookingId;
    private int numberOfSeatsBooked;
    private final BigDecimal seatPrice;

    private Long movieGoerId;
    private final MovieProgram movieProgram;

    public Booking(Long movieGoerId, MovieProgram movieProgram, int numberOfSeats, BigDecimal seatPrice) {
        this.movieGoerId = movieGoerId;
        this.movieProgram = movieProgram;
        this.numberOfSeatsBooked = numberOfSeats;
        this.seatPrice = seatPrice;
    }

    public Long movieGoerId() {
        return movieGoerId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Long scheduleId() {
        return movieProgram.getScheduleId();
    }

    // TODO: Possible refactoring - rule of Demeter
    public String filmName() {
        return movieProgram.movie().movieName();
    }

    public LocalDateTime bookingTime() {
        return movieProgram.scheduleDate();
    }

    public int numberOfSeatsBooked() {
        return numberOfSeatsBooked;
    }

    public BigDecimal seatPrice() {
        return seatPrice;
    }

    public MovieProgram movieProgram() {
        return movieProgram;
    }

    public void addSeats(int extraSeats) {
        numberOfSeatsBooked += extraSeats;
    }

}
