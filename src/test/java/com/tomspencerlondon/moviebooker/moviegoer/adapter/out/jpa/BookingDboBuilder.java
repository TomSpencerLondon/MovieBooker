package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.BookingDbo;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;

import java.math.BigDecimal;

public class BookingDboBuilder {
    private Long bookingId;
    private int numberOfSeatsBooked;
    private BigDecimal seatPrice;

    private Long movieGoerId;


    public BookingDboBuilder from(Booking booking) {
        bookingId = booking.getBookingId();
        numberOfSeatsBooked = booking.numberOfSeatsBooked();
        seatPrice = booking.seatPrice();
        movieGoerId = booking.movieGoerId();

        return this;
    }

    public BookingDbo build() {
        BookingDbo bookingDbo = new BookingDbo();
        bookingDbo.setBookingId(bookingId);
        bookingDbo.setScheduleId(null);
        bookingDbo.setNumberOfSeatsBooked(numberOfSeatsBooked);
        bookingDbo.setUserId(movieGoerId);
        bookingDbo.setSeatPrice(seatPrice);

        return bookingDbo;
    }
}
