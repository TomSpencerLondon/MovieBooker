package com.tomspencerlondon.moviebooker.hexagon.application;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;

import java.util.Optional;

public class MovieGoerService {

    private MovieGoerRepository movierGoerRepository;

    public MovieGoerService(MovieGoerRepository movierGoerRepository) {
        this.movierGoerRepository = movierGoerRepository;
    }

    public void askForLoyalty(String userName, boolean optIn) {
        MovieGoer movieGoer = findByUserName(userName);
        movieGoer.askForLoyalty(optIn);

        movierGoerRepository.save(movieGoer);
    }

    public MovieGoer findByUserName(String userName) {
        return movierGoerRepository.findByUserName(userName)
                .orElseThrow(IllegalArgumentException::new);
    }

    public MovieGoer save(MovieGoer movieGoer) {
        Optional<MovieGoer> foundMovieGoer = movierGoerRepository.findByUserName(movieGoer.userName());
        if (foundMovieGoer.isPresent()) {
            throw new RuntimeException("User is already registered");
        }

        return movierGoerRepository.save(movieGoer);
    }
}
