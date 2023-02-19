package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieGoerJpaRepository extends JpaRepository<MovieGoerDbo, Long> {
    Optional<MovieGoerDbo> findByUserName(String username);
}
