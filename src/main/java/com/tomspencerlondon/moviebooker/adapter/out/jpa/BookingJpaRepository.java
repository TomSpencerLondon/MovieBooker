package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingJpaRepository extends JpaRepository<BookingDbo, Long> {
    List<BookingDbo> findByUserId(Long userId);

    @Query(value="SELECT b FROM BookingDbo b WHERE b.scheduleId = :scheduleId")
    List<BookingDbo> findBookingsByProgramId(Long scheduleId);
}
