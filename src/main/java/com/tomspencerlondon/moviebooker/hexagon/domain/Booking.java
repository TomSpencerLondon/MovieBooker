package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Booking {
    private Long bookingId;
    private Long scheduleId;
    private int numberOfSeatsBooked;
    private Price price;

    private Long movieGoerId;
    private final String filmName;
    private final LocalDateTime bookingTime;

    public Booking(Long movieGoerId, String filmName, LocalDateTime bookingTime,
                   Long scheduleId, int numberOfSeatsBooked, Price price) {
        this.movieGoerId = movieGoerId;
        this.filmName = filmName;
        this.bookingTime = bookingTime;
        this.scheduleId = scheduleId;
        this.numberOfSeatsBooked = numberOfSeatsBooked;
        this.price = price;
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

    public BigDecimal price() {
        return price.amountPaid();
    }

    public int loyaltyPointChange() {
        return price.loyaltyPointChange();
    }
}
