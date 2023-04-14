package com.tomspencerlondon.moviebooker.common.hexagon.application.port;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;

import java.util.List;

public interface AdminMovieRepository {
    List<AdminMovie> findAll();
}
