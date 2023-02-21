package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Service;

@Service("jpaBookingTransformer")
public class BookingTransformer {

    private final MovieGoerRepository movieGoerRepository;

    public BookingTransformer(MovieGoerRepository movieGoerRepository) {
        this.movieGoerRepository = movieGoerRepository;
    }

    public BookingDbo toBookingDbo(Booking booking, MovieProgram movieProgram) {
        BookingDbo bookingDbo = new BookingDbo();
        bookingDbo.setBookingId(booking.getBookingId());

        MovieGoer movieGoer = movieGoerRepository
                .findById(booking.movieGoerId())
                .orElseThrow(UnsupportedOperationException::new);

        MovieGoerDbo movieGoerDbo = new MovieGoerDbo();
        movieGoerDbo.setUserId(movieGoer.getUserId());
        movieGoerDbo.setUserName(movieGoer.userName());
        movieGoerDbo.setPassword(movieGoer.password());
        movieGoerDbo.setLoyaltyPoints(movieGoer.loyaltyPoints());
        bookingDbo.setMovieGoerDbo(movieGoerDbo);

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
        bookingDbo.setPrice(booking.price());
        bookingDbo.setUserId(booking.movieGoerId());
        return bookingDbo;
    }

    public Booking toBooking(BookingDbo bookingDbo) {
        MovieProgramDbo movieProgramDbo = bookingDbo.getMovieProgram();
        Long bookingId = bookingDbo.getBookingId();
        Long movieGoerId = bookingDbo.getUserId();
        Booking booking = new Booking(
                movieGoerId, movieProgramDbo.getMovie().getMovieName(),
                movieProgramDbo.getScheduleDate(),
                movieProgramDbo.getScheduleId(),
                bookingDbo.getNumberOfSeatsBooked(),
                bookingDbo.getPrice());
        booking.setBookingId(bookingId);
        return booking;
    }
}
