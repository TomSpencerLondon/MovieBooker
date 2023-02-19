package com.tomspencerlondon.moviebooker.hexagon.application;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;

import java.util.Optional;

public class MovieGoerService {

    private MovieGoerRepository movierGoerRepository;

    public MovieGoerService(MovieGoerRepository movierGoerRepository) {
        this.movierGoerRepository = movierGoerRepository;
    }

    public MovieGoer save(MovieGoer movieGoer) {
        Optional<MovieGoer> foundMovieGoer = movierGoerRepository.findByUserName(movieGoer.userName());
        if (foundMovieGoer.isPresent()) {
            throw new RuntimeException("User is already registered");
        }

        return movierGoerRepository.save(movieGoer);
    }
}
