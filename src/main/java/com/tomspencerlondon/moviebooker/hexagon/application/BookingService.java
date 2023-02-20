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


    public Booking makeBookingFor(Long userId, Long scheduleId, Integer numberOfSeatsBooked) {
        MovieProgram movieProgram = movieProgramRepository.findById(scheduleId)
                .orElseThrow(UnsupportedOperationException::new);

        Booking booking = new Booking(
                userId,
                movieProgram.movie().movieName(),
                movieProgram.scheduleDate(),
                movieProgram.getScheduleId(),
                numberOfSeatsBooked, movieProgram.price());

        return bookingRepository.save(booking);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public void cancelBookingFor(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public Booking createBooking(String userName, Long movieProgramId, int numberOfSeats) {
        MovieGoer movieGoer = movieGoerRepository.findByUserName(userName).orElseThrow(IllegalArgumentException::new);
        MovieProgram movieProgram = movieProgramRepository.findById(movieProgramId)
                .orElseThrow(IllegalArgumentException::new);

        return movieProgram.createBooking(movieGoer.getUserId(), numberOfSeats);
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
}
