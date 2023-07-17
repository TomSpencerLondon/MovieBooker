package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieGoerDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieGoerJpaRepository;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.UserDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.UserJpaRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MovieGoerRepositoryJpaAdapter implements MovieGoerRepository {
    private final MovieGoerTransformer movieGoerTransformer;
    private final MovieGoerJpaRepository movieGoerJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public MovieGoerRepositoryJpaAdapter(MovieGoerTransformer movieGoerTransformer, MovieGoerJpaRepository movieGoerJpaRepository,
        UserJpaRepository userJpaRepository) {
        this.movieGoerTransformer = movieGoerTransformer;
        this.movieGoerJpaRepository = movieGoerJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public MovieGoer save(MovieGoer movieGoer) {
        UserDbo userDbo = new UserDbo();
        userDbo.setUserName(movieGoer.userName());
        userDbo.setPassword(movieGoer.password());
        userDbo.setRole(movieGoer.role());
        UserDbo savedUser = userJpaRepository.save(userDbo);
        MovieGoerDbo movieGoerDbo = movieGoerTransformer.toMovieGoerDbo(movieGoer, savedUser.getUserId());
        MovieGoerDbo saved = movieGoerJpaRepository.save(movieGoerDbo);

        return new MovieGoer(
                savedUser.getUserName(),
                savedUser.getPassword(),
                saved.getLoyaltyPoints(),
                saved.getIsLoyaltyUser(), saved.getAskedForLoyalty(), Role.USER);
    }

    @Override
    public Optional<MovieGoer> findById(Long userId) {
        UserDbo userDbo = userJpaRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        MovieGoerDbo movieGoerDbo = movieGoerJpaRepository.findById(userId).orElseThrow(
            IllegalArgumentException::new);

        return Optional.of(movieGoerTransformer.toMovieGoer(movieGoerDbo, userDbo));
    }

    @Override
    public Optional<MovieGoer> findByUserName(String username) {
        Optional<UserDbo> userDbo = userJpaRepository.findByUserName(username);

        if (userDbo.isEmpty()) {
            return Optional.empty();
        } else {
            MovieGoerDbo movieGoerDbo = movieGoerJpaRepository
                .findById(userDbo.get().getUserId())
                .orElseThrow(IllegalArgumentException::new);
            return Optional.of(movieGoerTransformer.toMovieGoer(movieGoerDbo, userDbo.get()));
        }

    }
}
