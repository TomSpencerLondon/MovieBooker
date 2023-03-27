package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private BigDecimal amountPaid;
    private final int updatedLoyaltyPoints;
    private LocalDateTime paymentDate;
    private Long bookingId;

    public Payment(BigDecimal amountPaid, int updatedLoyaltyPoints, LocalDateTime paymentDate) {
        this.amountPaid = amountPaid;
        this.updatedLoyaltyPoints = updatedLoyaltyPoints;
        this.paymentDate = paymentDate;
    }

    public BigDecimal amountPaid() {
        return amountPaid;
    }

    public LocalDateTime paymentDate() {
        return paymentDate;
    }

    public int updatedLoyaltyPoints() {
        return updatedLoyaltyPoints;
    }

    public void associateBooking(Booking booking) {
        this.bookingId = booking.getBookingId();
    }

    public Long bookingId() {
        return bookingId;
    }
}
