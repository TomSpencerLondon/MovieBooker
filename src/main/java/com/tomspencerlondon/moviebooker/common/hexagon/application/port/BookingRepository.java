package com.tomspencerlondon.moviebooker.common.hexagon.application.port;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Booking save(Booking booking);
    List<Booking> findAll();

    Optional<Booking> findById(Long bookingId);
    void deleteById(Long bookingId);

    List<Booking> findByUserId(Long userId);

}
