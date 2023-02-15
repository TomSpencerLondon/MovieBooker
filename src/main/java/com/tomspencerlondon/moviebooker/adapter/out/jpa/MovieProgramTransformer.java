package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Service;

@Service("jpaMovieProgramTransformer")
public class MovieProgramTransformer {
    MovieTransformer movieTransformer;

    public MovieProgramTransformer(MovieTransformer movieTransformer) {
        this.movieTransformer = movieTransformer;
    }

    public MovieProgram toMovieProgram(MovieProgramDbo movieProgramDbo) {
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
                movie);
    }

    public MovieProgramDbo toMovieProgramDbo(MovieProgram movieProgram) {
        MovieProgramDbo movieProgramDbo = new MovieProgramDbo();

        movieProgramDbo.setScheduleId(movieProgram.getScheduleId());
        movieProgramDbo.setScheduleDate(movieProgram.scheduleDate());
        movieProgramDbo.setSeats(movieProgram.seats());
        movieProgramDbo.setMovie(
                movieTransformer.toMovieDbo(movieProgram.movie())
        );

        return movieProgramDbo;
    }
}
