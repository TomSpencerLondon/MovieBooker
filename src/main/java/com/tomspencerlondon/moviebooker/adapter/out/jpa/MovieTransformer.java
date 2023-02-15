package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import org.springframework.stereotype.Service;

@Service("jpaMovieTransformer")
public class MovieTransformer {

    public Movie toMovie(MovieDbo movieDbo) {
        return new Movie(
                movieDbo.getMovieId(),
                movieDbo.getMovieName(),
                movieDbo.getMovieImage(),
                movieDbo.getReleaseDate(),
                movieDbo.getDescription()
        );
    }

    public MovieDbo toMovieDbo(Movie movie) {
        MovieDbo movieDbo = new MovieDbo();
        movieDbo.setMovieId(movie.getId());
        movieDbo.setMovieName(movie.movieName());
        movieDbo.setDescription(movie.description());
        movieDbo.setMovieImage(movie.image());
        movieDbo.setReleaseDate(movie.releaseDate());

        return movieDbo;
    }
}
