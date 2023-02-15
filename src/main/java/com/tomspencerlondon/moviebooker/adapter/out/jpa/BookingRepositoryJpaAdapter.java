package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.application.port.BookingRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BookingRepositoryJpaAdapter implements BookingRepository {

    private final BookingJpaRepository bookingJpaRepository;
    private final BookingTransformer bookingTransformer;

    public BookingRepositoryJpaAdapter(BookingJpaRepository bookingJpaRepository, BookingTransformer bookingTransformer) {
        this.bookingJpaRepository = bookingJpaRepository;
        this.bookingTransformer = bookingTransformer;
    }


    @Override
    public Booking save(Booking booking) {
        BookingDbo savedBookingDbo = bookingJpaRepository
                .save(bookingTransformer.toBookingDbo(booking));
        return bookingTransformer.toBooking(savedBookingDbo);
    }

    @Override
    public List<Booking> findAll() {
        return bookingJpaRepository.findAll()
                .stream()
                .map(bookingTransformer::toBooking)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Booking> findById(Long bookingId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long bookingId) {
        bookingJpaRepository.deleteById(bookingId);
    }
}
