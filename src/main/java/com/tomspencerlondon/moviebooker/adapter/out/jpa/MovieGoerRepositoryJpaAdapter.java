package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;
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
        MovieGoerDbo movieGoerDbo = movieGoerTransformer.toMovieGoerDbo(movieGoer);
        MovieGoerDbo saved = movieGoerJpaRepository.save(movieGoerDbo);
        return new MovieGoer(
                saved.getUserName(),
                saved.getPassword(),
                saved.getLoyaltyPoints(),
                saved.getIsLoyaltyUser(), saved.getAskedForLoyalty());
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
