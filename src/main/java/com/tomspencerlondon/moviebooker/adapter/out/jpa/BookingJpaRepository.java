package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingJpaRepository extends JpaRepository<BookingDbo, Long> {
    List<BookingDbo> findByUserId(Long userId);
}
