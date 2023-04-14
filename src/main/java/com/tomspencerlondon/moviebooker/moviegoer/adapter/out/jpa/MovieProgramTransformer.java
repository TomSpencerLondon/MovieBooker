package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramDbo;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Service;

@Service("jpaMovieProgramTransformer")
public class MovieProgramTransformer {
    private final MovieTransformer movieTransformer;

    public MovieProgramTransformer(MovieTransformer movieTransformer) {
        this.movieTransformer = movieTransformer;
    }

    public MovieProgram toMovieProgram(MovieProgramDbo movieProgramDbo, int seatsBooked) {
        MovieDbo movieDbo = movieProgramDbo.getMovie();
        Movie movie = new Movie(
                movieDbo.getMovieId(),
                movieDbo.getMovieName(),
                movieDbo.getMovieImage(),
                movieDbo.getReleaseDate(),
                movieDbo.getDescription());

        return new MovieProgram(
                movieProgramDbo.getScheduleId(),
                movieProgramDbo.getScheduleDate(),
                movieProgramDbo.getSeats(),
                movie,
                seatsBooked, movieProgramDbo.getPrice());
    }

    public MovieProgramDbo toMovieProgramDbo(MovieProgram movieProgram) {
        MovieProgramDbo movieProgramDbo = new MovieProgramDbo();
        movieProgramDbo.setScheduleId(movieProgram.getScheduleId());
        movieProgramDbo.setMovie(
                movieTransformer.toMovieDbo(movieProgram.movie())
        );
        movieProgramDbo.setScheduleDate(movieProgram.scheduleDate());
        movieProgramDbo.setSeats(movieProgram.totalSeats());
        movieProgramDbo.setPrice(movieProgram.price());

        return movieProgramDbo;
    }
}
