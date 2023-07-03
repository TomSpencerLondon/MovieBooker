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

    public MovieProgram toMovieProgram(MovieProgramDbo movieProgramDbo, int seatsBooked, int totalSeats) {
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
                totalSeats,
                movie,
                seatsBooked, movieProgramDbo.getPrice());
    }

}
