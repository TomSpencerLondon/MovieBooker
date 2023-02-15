package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.application.port.BookingRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookingRepositoryJpaAdapter implements BookingRepository {

    private final BookingJpaRepository bookingJpaRepository;
    private final BookingTransformer bookingTransformer;

    public BookingRepositoryJpaAdapter(BookingJpaRepository bookingJpaRepository, BookingTransformer bookingTransformer) {
        this.bookingJpaRepository = bookingJpaRepository;
        this.bookingTransformer = bookingTransformer;
    }


    @Override
    public Booking save(Movie movie) {
        return null;
    }

    @Override
    public List<Booking> findAll() {
        return null;
    }

    @Override
    public Optional<Booking> findById(Long movieId) {
        return Optional.empty();
    }
}
