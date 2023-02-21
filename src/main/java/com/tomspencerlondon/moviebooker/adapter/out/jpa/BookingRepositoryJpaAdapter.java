package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.application.port.BookingRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BookingRepositoryJpaAdapter implements BookingRepository {

    public final MovieProgramRepository movieProgramRepository;
    private final BookingJpaRepository bookingJpaRepository;

    private final BookingTransformer bookingTransformer;

    public BookingRepositoryJpaAdapter(MovieProgramRepository movieProgramRepository, BookingJpaRepository bookingJpaRepository, BookingTransformer bookingTransformer) {
        this.movieProgramRepository = movieProgramRepository;
        this.bookingJpaRepository = bookingJpaRepository;
        this.bookingTransformer = bookingTransformer;
    }


    @Override
    public Booking save(Booking booking) {
        MovieProgram movieProgram = movieProgramRepository
                .findById(booking.scheduleId())
                .orElseThrow(UnsupportedOperationException::new);

        BookingDbo bookingDbo = bookingTransformer.toBookingDbo(booking, movieProgram);

        BookingDbo savedBookingDbo = bookingJpaRepository
                .save(bookingDbo);

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
        Optional<BookingDbo> bookingDbo = bookingJpaRepository.findById(bookingId);
        bookingDbo.ifPresent(bookingJpaRepository::delete);
    }

    @Override
    public List<Booking> findByUserId(Long userId) {
        return bookingJpaRepository.findByUserId(userId)
                .stream()
                .map(bookingTransformer::toBooking)
                .toList();
    }
}
