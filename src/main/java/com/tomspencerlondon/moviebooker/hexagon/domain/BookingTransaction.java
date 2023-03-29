package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingTransaction {
    private final Booking booking;
    private final MovieGoer movieGoer;
    private final LocalDateTime transactionTime;

    public BookingTransaction(Booking booking, MovieGoer movieGoer, LocalDateTime transactionTime) {
        this.booking = booking;
        this.movieGoer = movieGoer;
        this.transactionTime = transactionTime;
    }

    public Payment payment() {
        LoyaltyDevice loyaltyDevice = movieGoer.loyaltyCard();
        int numberOfSeats = booking.numberOfSeatsBooked();
        loyaltyDevice.addSeatsToCard(numberOfSeats);
        int seatsToPayFor = numberOfSeats - loyaltyDevice.loyaltySeats();
        BigDecimal amountPaid = booking.seatPrice().multiply(new BigDecimal(seatsToPayFor));

        return new Payment(amountPaid, loyaltyDevice.updatedLoyaltyPoints(), transactionTime);
    }

}
