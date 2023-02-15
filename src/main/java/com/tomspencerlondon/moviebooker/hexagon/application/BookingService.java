package com.tomspencerlondon.moviebooker.hexagon.application;

import com.tomspencerlondon.moviebooker.hexagon.application.port.BookingRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;

import java.util.List;

public class BookingService {
    private BookingRepository bookingRepository;
    private MovieProgramRepository movieProgramRepository;

    public BookingService(BookingRepository bookingRepository, MovieProgramRepository movieProgramRepository) {
        this.bookingRepository = bookingRepository;
        this.movieProgramRepository = movieProgramRepository;
    }


    public Booking makeBookingFor(Long scheduleId) {
        MovieProgram movieProgram = movieProgramRepository.findById(scheduleId)
                .orElseThrow(UnsupportedOperationException::new);

        Booking booking = new Booking(movieProgram);
        return bookingRepository.save(booking);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public void cancelBookingFor(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}
