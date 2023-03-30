package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AmendBookingTransaction {
    private final Booking booking;
    private final MovieGoer movieGoer;
    private final int extraSeats;
    private final LocalDateTime transactionTime;

    public AmendBookingTransaction(Booking booking, MovieGoer movieGoer, int extraSeats, LocalDateTime transactionTime) {
        this.booking = booking;
        this.movieGoer = movieGoer;
        this.extraSeats = extraSeats;
        this.transactionTime = transactionTime;
    }

    public int extraSeats() {
        return extraSeats;
    }

    public Payment payment() {
        LoyaltyDevice loyaltyDevice = movieGoer.loyaltyCard();
        loyaltyDevice.addSeatsToCard(extraSeats);
        int seatsToPayFor = extraSeats - loyaltyDevice.loyaltySeats();
        BigDecimal amountPaid = booking.seatPrice().multiply(new BigDecimal(seatsToPayFor));

        return new Payment(amountPaid, loyaltyDevice.updatedLoyaltyPoints(), transactionTime);
    }

    public Booking booking() {
        return booking;
    }
}
