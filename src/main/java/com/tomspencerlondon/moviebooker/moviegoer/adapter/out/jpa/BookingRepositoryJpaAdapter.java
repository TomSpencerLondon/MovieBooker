package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.BookingDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.BookingJpaRepository;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.PaymentJpaRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.BookingRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BookingRepositoryJpaAdapter implements BookingRepository {

    public final MovieProgramRepository movieProgramRepository;
    private final BookingJpaRepository bookingJpaRepository;

    private final BookingTransformer bookingTransformer;
    private final PaymentJpaRepository paymentJpaRepository;

    public BookingRepositoryJpaAdapter(MovieProgramRepository movieProgramRepository, BookingJpaRepository bookingJpaRepository, BookingTransformer bookingTransformer,
                                       PaymentJpaRepository paymentJpaRepository) {
        this.movieProgramRepository = movieProgramRepository;
        this.bookingJpaRepository = bookingJpaRepository;
        this.bookingTransformer = bookingTransformer;
        this.paymentJpaRepository = paymentJpaRepository;
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
        return bookingJpaRepository.findById(bookingId)
                .map(bookingTransformer::toBooking);
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
