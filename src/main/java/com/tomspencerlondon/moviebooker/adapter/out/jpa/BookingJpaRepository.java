package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingJpaRepository extends JpaRepository<BookingDbo, Long> {
}
