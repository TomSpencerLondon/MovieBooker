package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.BookingDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieGoerDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramDbo;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Service;

@Service("jpaBookingTransformer")
public class BookingTransformer {

    private final MovieGoerRepository movieGoerRepository;

    private final MovieProgramTransformer movieProgramTransformer;
    private final MovieProgramRepository movieProgramRepository;

    public BookingTransformer(MovieGoerRepository movieGoerRepository, MovieProgramTransformer movieProgramTransformer, MovieProgramRepository movieProgramRepository) {
        this.movieGoerRepository = movieGoerRepository;
        this.movieProgramTransformer = movieProgramTransformer;
        this.movieProgramRepository = movieProgramRepository;
    }

    public BookingDbo toBookingDbo(Booking booking, MovieProgram movieProgram) {
        BookingDbo bookingDbo = new BookingDbo();

        MovieGoer movieGoer = movieGoerRepository
                .findById(booking.movieGoerId())
                .orElseThrow(UnsupportedOperationException::new);

        // TODO: Is this required - we are not saving MovieGoer at this point
        MovieGoerDbo movieGoerDbo = new MovieGoerDbo();
        movieGoerDbo.setUserId(movieGoer.getUserId());
        movieGoerDbo.setUserName(movieGoer.userName());
        movieGoerDbo.setPassword(movieGoer.password());
        movieGoerDbo.setLoyaltyPoints(movieGoer.loyaltyPoints());

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

        bookingDbo.setBookingId(booking.getBookingId());
        bookingDbo.setScheduleId(movieProgramDbo.getScheduleId());
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
