package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.BookingDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieGoerDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramDbo;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;

import java.math.BigDecimal;

public class BookingDboBuilder {
    private Long bookingId;
    private int numberOfSeatsBooked;
    private BigDecimal seatPrice;

    private Long movieGoerId;
    private MovieProgram movieProgram;
    private MovieGoer movieGoer;


    public BookingDboBuilder from(Booking booking) {
        bookingId = booking.getBookingId();
        numberOfSeatsBooked = booking.numberOfSeatsBooked();
        seatPrice = booking.seatPrice();
        movieGoerId = booking.movieGoerId();
        movieProgram = booking.movieProgram();

        return this;
    }

    public BookingDboBuilder with(MovieGoer movieGoer) {
        this.movieGoer = movieGoer;
        return this;
    }

    public BookingDbo build() {
        BookingDbo bookingDbo = new BookingDbo();

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

        bookingDbo.setBookingId(bookingId);
        bookingDbo.setScheduleId(movieProgramDbo.getScheduleId());
        bookingDbo.setNumberOfSeatsBooked(numberOfSeatsBooked);
        bookingDbo.setUserId(movieGoerId);
        bookingDbo.setSeatPrice(seatPrice);

        return bookingDbo;
    }
}
