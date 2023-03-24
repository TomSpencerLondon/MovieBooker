package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Booking {
    private Long bookingId;
    private Long scheduleId;
    private int numberOfSeatsBooked;
    private final BigDecimal amountToPay;
    private final int updatedLoyaltyPoints;

    private Long movieGoerId;
    private final String filmName;
    private final LocalDateTime bookingTime;
    private Payment payment;

    // TODO: Move film name and booking time out and use movieProgram
    public Booking(Long movieGoerId, String filmName, LocalDateTime bookingTime,
                   Long scheduleId, int numberOfSeatsBooked,
                   BigDecimal amountToPay, int updatedLoyaltyPoints) {
        this.movieGoerId = movieGoerId;
        this.filmName = filmName;
        this.bookingTime = bookingTime;
        this.scheduleId = scheduleId;
        this.numberOfSeatsBooked = numberOfSeatsBooked;
        this.amountToPay = amountToPay;
        this.updatedLoyaltyPoints = updatedLoyaltyPoints;
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

    public BigDecimal paymentAmount() {
        return amountToPay;
    }

    public int loyaltyPointsUpdated() {
        return updatedLoyaltyPoints;
    }

    public void addPayment(Payment payment) {
        this.payment = payment;
    }

    public Payment payment() {
        return payment;
    }
}
