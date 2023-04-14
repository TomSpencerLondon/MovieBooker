package com.tomspencerlondon.moviebooker.common.hexagon.application.port;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;

import java.util.Optional;

public interface MovieGoerRepository {
    MovieGoer save(MovieGoer movieGoer);

    Optional<MovieGoer> findById(Long userId);

    Optional<MovieGoer> findByUserName(String username);
}
