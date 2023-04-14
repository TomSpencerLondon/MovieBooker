package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieGoerDbo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieGoerJpaRepository extends JpaRepository<MovieGoerDbo, Long> {
    Optional<MovieGoerDbo> findByUserName(String username);
}
