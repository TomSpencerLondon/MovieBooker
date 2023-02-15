package com.tomspencerlondon.moviebooker.hexagon.application.port;

import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Booking save(Booking booking);
    List<Booking> findAll();

    Optional<Booking> findById(Long bookingId);
    void deleteById(Long bookingId);

}
