package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Service;

@Service("jpaBookingTransformer")
public class BookingTransformer {

    MovieProgramTransformer movieProgramTransformer;

    public BookingTransformer(MovieProgramTransformer movieProgramTransformer) {
        this.movieProgramTransformer = movieProgramTransformer;
    }

    public BookingDbo toBookingDbo(Booking booking) {
        BookingDbo bookingDbo = new BookingDbo();
        bookingDbo.setBookingId(booking.getBookingId());
        MovieProgramDbo movieProgramDbo = movieProgramTransformer.toMovieProgramDbo(booking.getMovieProgram());
        bookingDbo.setMovieProgram(movieProgramDbo);
        return bookingDbo;
    }

    public Booking toBooking(BookingDbo bookingDbo) {
        MovieProgram movieProgram = movieProgramTransformer.toMovieProgram(bookingDbo.getMovieProgram());
        Booking booking = new Booking(movieProgram);
        booking.setBookingId(bookingDbo.getBookingId());
        return booking;
    }
}
