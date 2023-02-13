package com.tomspencerlondon.moviebooker.hexagon.application;

import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;

import java.util.List;

public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
