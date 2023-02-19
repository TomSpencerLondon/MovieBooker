package com.tomspencerlondon.moviebooker.hexagon.application.port;

import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;

import java.util.Optional;

public interface MovieGoerRepository {
    MovieGoer save(MovieGoer movieGoer);

    Optional<MovieGoer> findById(Long userId);

    Optional<MovieGoer> findByUserName(String username);
}
