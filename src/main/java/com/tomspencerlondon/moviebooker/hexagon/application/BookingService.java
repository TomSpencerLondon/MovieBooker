package com.tomspencerlondon.moviebooker.hexagon.application;

import com.tomspencerlondon.moviebooker.hexagon.application.port.BookingRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;

import java.util.List;

public class BookingService {
    private BookingRepository bookingRepository;
    private MovieProgramRepository movieProgramRepository;

    private MovieGoerRepository movieGoerRepository;

    public BookingService(BookingRepository bookingRepository, MovieProgramRepository movieProgramRepository, MovieGoerRepository movieGoerRepository) {
        this.bookingRepository = bookingRepository;
        this.movieProgramRepository = movieProgramRepository;
        this.movieGoerRepository = movieGoerRepository;
    }

    public List<Booking> findAllBookingsFor(String userName) {
        MovieGoer movieGoer = movieGoerRepository.findByUserName(userName).orElseThrow(IllegalArgumentException::new);

        return bookingRepository.findByUserId(movieGoer.getUserId());
    }

    public Booking createBooking(String userName, Long movieProgramId, int numberOfSeats) {
        MovieGoer movieGoer = movieGoerRepository.findByUserName(userName).orElseThrow(IllegalArgumentException::new);
        MovieProgram movieProgram = movieProgramRepository.findById(movieProgramId)
                .orElseThrow(IllegalArgumentException::new);

        return movieProgram.createBooking(movieGoer, numberOfSeats);
    }

    public Booking save(Booking booking) {
        MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
                .orElseThrow(IllegalArgumentException::new);
        movieGoer.confirmBooking(booking);
        movieGoerRepository.save(movieGoer);
        return bookingRepository.save(booking);
    }

}
