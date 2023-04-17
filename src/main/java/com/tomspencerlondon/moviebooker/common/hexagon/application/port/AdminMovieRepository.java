package com.tomspencerlondon.moviebooker.common.hexagon.application.port;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;

import java.util.List;
import java.util.Optional;

public interface AdminMovieRepository {
    List<AdminMovie> findAll();

    Optional<AdminMovie> findById(Long movieId);
}
