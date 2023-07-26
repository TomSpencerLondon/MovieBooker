package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieGoerJpaRepository extends JpaRepository<MovieGoerDbo, Long> {

    Optional<MovieGoerDbo> findByUserId(Long userId);
}
