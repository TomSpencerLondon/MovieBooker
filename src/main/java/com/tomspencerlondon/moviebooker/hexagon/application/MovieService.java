package com.tomspencerlondon.moviebooker.hexagon.application;

import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public List<MovieProgram> programsForFilm(Long filmId) {
        Movie movie = movieRepository.findById(filmId)
                .orElseThrow(UnsupportedOperationException::new);

        return movie.moviePrograms();
    }
}
