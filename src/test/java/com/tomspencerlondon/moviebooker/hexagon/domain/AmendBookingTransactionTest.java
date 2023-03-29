package com.tomspencerlondon.moviebooker.hexagon.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class AmendBookingTransactionTest {

    @Test
    void amendBookingCreatesPaymentWithUpdatedLoyaltyPoints() {

        Booking booking = createBooking(2, 2);
        MovieGoer movieGoer = new MovieGoer(
                "Tom", "password",
                2, true,
                true);
        int extraSeats = 2;
        AmendBookingTransaction amendBooking = new AmendBookingTransaction(booking, movieGoer, extraSeats, LocalDateTime.now());

        Payment payment = amendBooking.payment();

        assertThat(payment.amountPaid())
                .isEqualTo(new BigDecimal(10));
        assertThat(payment.updatedLoyaltyPoints())
                .isEqualTo(4);
    }

    private Booking createBooking(int seatsAvailable, int numberOfSeats) {
        Movie movie = new Movie(1L, "Godfather", "image", LocalDate.MIN, "description");
        MovieProgram movieProgram = new MovieProgram(
                1L,
                LocalDateTime.now(),
                seatsAvailable,
                movie,
                0,
                new BigDecimal(5)
        );
        Booking booking = new Booking(1L,
                movieProgram,
                numberOfSeats,
                new BigDecimal(5)
        );
        return booking;
    }
}