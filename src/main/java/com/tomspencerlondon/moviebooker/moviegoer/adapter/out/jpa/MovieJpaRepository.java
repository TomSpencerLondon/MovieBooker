package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieJpaRepository extends JpaRepository<MovieDbo, Long> {
}
