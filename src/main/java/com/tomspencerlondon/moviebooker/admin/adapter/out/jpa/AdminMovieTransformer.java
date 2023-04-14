package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieDbo;
import org.springframework.stereotype.Service;

@Service("jpaAdminMovieTransformer")
public class AdminMovieTransformer {

    public AdminMovie toMovie(MovieDbo movieDbo) {
        return new AdminMovie(
                movieDbo.getMovieId(),
                movieDbo.getMovieName(),
                movieDbo.getMovieImage(),
                movieDbo.getReleaseDate(),
                movieDbo.getDescription()
        );
    }

    public MovieDbo toMovieDbo(AdminMovie movie) {
        MovieDbo movieDbo = new MovieDbo();
        movieDbo.setMovieId(movie.getId());
        movieDbo.setMovieName(movie.movieName());
        movieDbo.setDescription(movie.description());
        movieDbo.setMovieImage(movie.image());
        movieDbo.setReleaseDate(movie.releaseDate());

        return movieDbo;
    }
}

