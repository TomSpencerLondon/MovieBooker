package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieGoerJpaRepository extends JpaRepository<MovieGoerDbo, Long> {

}
