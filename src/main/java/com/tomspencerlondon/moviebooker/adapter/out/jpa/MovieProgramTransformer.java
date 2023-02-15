package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Service;

@Service("jpaMovieProgramTransformer")
public class MovieProgramTransformer {
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
}
