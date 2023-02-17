package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Service;

@Service("jpaBookingTransformer")
public class BookingTransformer {

    public BookingDbo toBookingDbo(Booking booking, MovieProgram movieProgram) {
        BookingDbo bookingDbo = new BookingDbo();
        bookingDbo.setBookingId(booking.getBookingId());

        MovieProgramDbo movieProgramDbo = new MovieProgramDbo();
        movieProgramDbo.setScheduleId(movieProgram.getScheduleId());
        movieProgramDbo.setScheduleDate(movieProgram.scheduleDate());
        movieProgramDbo.setSeats(movieProgram.totalSeats());

        Movie movie = movieProgram.movie();
        MovieDbo movieDbo = new MovieDbo();
        movieDbo.setMovieId(movie.getId());
        movieDbo.setMovieName(movie.movieName());
        movieDbo.setDescription(movie.description());
        movieDbo.setMovieImage(movie.image());
        movieDbo.setReleaseDate(movie.releaseDate());
        movieProgramDbo.setMovie(
                movieDbo
        );
        bookingDbo.setMovieProgram(movieProgramDbo);
        bookingDbo.setNumberOfSeatsBooked(booking.numberOfSeatsBooked());
        return bookingDbo;
    }

    public Booking toBooking(BookingDbo bookingDbo) {
        MovieProgramDbo movieProgramDbo = bookingDbo.getMovieProgram();
        Booking booking = new Booking(
                movieProgramDbo.getMovie().getMovieName(),
                movieProgramDbo.getScheduleDate(),
                movieProgramDbo.getScheduleId(), bookingDbo.getNumberOfSeatsBooked());
        booking.setBookingId(bookingDbo.getBookingId());
        return booking;
    }
}
