package com.tomspencerlondon.moviebooker.hexagon.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingTest {


    @Test
    void canAddSeatsWhenSeatsAreAvailable() {
        assertWhenSeatsAvailable(2, 2, true);
    }

    @Test
    void notAllowedToAddSeatsWhenSeatsNotAvailable() {
        assertWhenSeatsAvailable(2, 3, false);
    }

    @Test
    void addSeatsIncreasesBookingSeatsWhenSeatsAvailable() {
        Booking booking = createBooking(2, 2);

        booking.addSeats(2);

        assertThat(booking.numberOfSeatsBooked())
                .isEqualTo(4);
    }

    void assertWhenSeatsAvailable(int seatsAvailable, int extraSeats, boolean expectedResult) {
        Booking booking = createBooking(seatsAvailable, 2);

        // TODO: refactor test
        assertThat(booking.movieProgram().seatsAvailableFor(extraSeats))
                .isEqualTo(expectedResult);
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
