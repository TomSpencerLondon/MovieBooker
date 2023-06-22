package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.BookingDbo;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Service;

@Service("jpaBookingTransformer")
public class BookingTransformer {

    private final MovieProgramRepository movieProgramRepository;

    public BookingTransformer(MovieProgramRepository movieProgramRepository) {
        this.movieProgramRepository = movieProgramRepository;
    }

    public BookingDbo toBookingDbo(Booking booking, MovieProgram movieProgram) {
        BookingDbo bookingDbo = new BookingDbo();

        bookingDbo.setBookingId(booking.getBookingId());
        bookingDbo.setScheduleId(movieProgram.getScheduleId());
        bookingDbo.setNumberOfSeatsBooked(booking.numberOfSeatsBooked());
        bookingDbo.setUserId(booking.movieGoerId());
        bookingDbo.setSeatPrice(booking.seatPrice());

        return bookingDbo;
    }

    public Booking toBooking(BookingDbo bookingDbo) {
        Long scheduleId = bookingDbo.getScheduleId();
        Long bookingId = bookingDbo.getBookingId();
        Long movieGoerId = bookingDbo.getUserId();

        MovieProgram movieProgram = movieProgramRepository.findById(scheduleId).orElseThrow(IllegalArgumentException::new);

        Booking booking = new Booking(
                movieGoerId,
                movieProgram,
                bookingDbo.getNumberOfSeatsBooked(),
                bookingDbo.getSeatPrice()
        );
        booking.setBookingId(bookingId);
        return booking;
    }
}
