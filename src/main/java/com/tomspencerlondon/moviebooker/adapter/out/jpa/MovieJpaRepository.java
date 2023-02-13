package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieJpaRepository extends JpaRepository<MovieDbo, Long> {
}
