package com.tomspencerlondon.moviebooker.hexagon.application.port;

import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Booking save(Movie movie);
    List<Booking> findAll();

    Optional<Booking> findById(Long movieId);
}
