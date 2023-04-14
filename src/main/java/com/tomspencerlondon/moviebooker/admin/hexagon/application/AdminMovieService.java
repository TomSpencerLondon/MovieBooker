package com.tomspencerlondon.moviebooker.admin.hexagon.application;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.AdminMovieRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMovieService {

    private final AdminMovieRepository adminMovieRepository;

    public AdminMovieService(AdminMovieRepository adminMovieRepository) {
        this.adminMovieRepository = adminMovieRepository;
    }

    public List<AdminMovie> findAll() {
        return adminMovieRepository.findAll();
    }
}
