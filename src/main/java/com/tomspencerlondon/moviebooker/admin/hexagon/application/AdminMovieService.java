package com.tomspencerlondon.moviebooker.admin.hexagon.application;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.AdminMovieRepository;
import java.util.List;

public class AdminMovieService {

    private final AdminMovieRepository adminMovieRepository;

    public AdminMovieService(AdminMovieRepository adminMovieRepository) {
        this.adminMovieRepository = adminMovieRepository;
    }

    public List<AdminMovie> findAll() {
        return adminMovieRepository.findAll();
    }

    public AdminMovie findById(Long movieId) {
        return adminMovieRepository.findById(movieId)
                .orElseThrow(UnsupportedOperationException::new);
    }

    public AdminMovie save(AdminMovie adminMovie) {
        return adminMovieRepository.save(adminMovie);
    }
}
