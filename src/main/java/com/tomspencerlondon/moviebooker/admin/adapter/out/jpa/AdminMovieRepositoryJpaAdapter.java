package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;


import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieJpaRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.AdminMovieRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AdminMovieRepositoryJpaAdapter implements AdminMovieRepository {

    private final MovieJpaRepository movieJpaRepository;
    private final AdminMovieTransformer adminMovieTransformer;


    public AdminMovieRepositoryJpaAdapter(MovieJpaRepository movieJpaRepository, AdminMovieTransformer adminMovieTransformer) {
        this.movieJpaRepository = movieJpaRepository;
        this.adminMovieTransformer = adminMovieTransformer;
    }

    @Override
    public List<AdminMovie> findAll() {
        return movieJpaRepository.findAll()
                .stream()
                .map(adminMovieTransformer::toMovie)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AdminMovie> findById(Long movieId) {
        return movieJpaRepository.findById(movieId)
                .map(adminMovieTransformer::toMovie);
    }

    @Override
    public AdminMovie save(AdminMovie adminMovie) {
        MovieDbo movieDbo = adminMovieTransformer.toMovieDbo(adminMovie);
        MovieDbo savedMovieDbo = movieJpaRepository.save(movieDbo);

        return new AdminMovie(
                savedMovieDbo.getMovieId(),
                savedMovieDbo.getMovieName(),
                savedMovieDbo.getMovieImage(),
                savedMovieDbo.getReleaseDate(),
                savedMovieDbo.getDescription()
        );
    }
}

