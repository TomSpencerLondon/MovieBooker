package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.UserDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieGoerJpaRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MovieGoerRepositoryJpaAdapter implements MovieGoerRepository {
    private final MovieGoerTransformer movieGoerTransformer;
    private final MovieGoerJpaRepository movieGoerJpaRepository;

    public MovieGoerRepositoryJpaAdapter(MovieGoerTransformer movieGoerTransformer, MovieGoerJpaRepository movieGoerJpaRepository) {
        this.movieGoerTransformer = movieGoerTransformer;
        this.movieGoerJpaRepository = movieGoerJpaRepository;
    }

    @Override
    public MovieGoer save(MovieGoer movieGoer) {
        UserDbo userDbo = movieGoerTransformer.toMovieGoerDbo(movieGoer);
        UserDbo saved = movieGoerJpaRepository.save(userDbo);
        return new MovieGoer(
                saved.getUserName(),
                saved.getPassword(),
                saved.getLoyaltyPoints(),
                saved.getIsLoyaltyUser(), saved.getAskedForLoyalty(), Role.USER);
    }

    @Override
    public Optional<MovieGoer> findById(Long userId) {
        return movieGoerJpaRepository.findById(userId)
                .map(movieGoerTransformer::toMovieGoer);
    }

    @Override
    public Optional<MovieGoer> findByUserName(String username) {
        return movieGoerJpaRepository.findByUserName(username)
                .map(movieGoerTransformer::toMovieGoer);
    }
}
