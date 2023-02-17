package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("jpaMovieProgramTransformer")
public class MovieProgramTransformer {
    private final MovieTransformer movieTransformer;
    private final BookingTransformer bookingTransformer;

    public MovieProgramTransformer(MovieTransformer movieTransformer, BookingTransformer bookingTransformer) {
        this.movieTransformer = movieTransformer;
        this.bookingTransformer = bookingTransformer;
    }

    public MovieProgram toMovieProgram(MovieProgramDbo movieProgramDbo) {
        MovieDbo movieDbo = movieProgramDbo.getMovie();
        Movie movie = new Movie(
                movieDbo.getMovieId(),
                movieDbo.getMovieName(),
                movieDbo.getMovieImage(),
                movieDbo.getReleaseDate(),
                movieDbo.getDescription());

        List<Booking> bookings = movieProgramDbo.getBookings()
                .stream()
                .map(bookingTransformer::toBooking).toList();

        return new MovieProgram(
                movieProgramDbo.getScheduleId(),
                movieProgramDbo.getScheduleDate(),
                movieProgramDbo.getSeats(),
                movie,
                bookings);
    }

    public MovieProgramDbo toMovieProgramDbo(MovieProgram movieProgram) {
        MovieProgramDbo movieProgramDbo = new MovieProgramDbo();

        movieProgramDbo.setScheduleId(movieProgram.getScheduleId());
        movieProgramDbo.setScheduleDate(movieProgram.scheduleDate());
        movieProgramDbo.setSeats(movieProgram.totalSeats());
        movieProgramDbo.setMovie(
                movieTransformer.toMovieDbo(movieProgram.movie())
        );

        return movieProgramDbo;
    }
}
