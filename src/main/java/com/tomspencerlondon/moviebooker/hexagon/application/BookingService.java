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


    public Booking makeBookingFor(String userName, Long scheduleId, Integer numberOfSeatsBooked) {
        MovieGoer movieGoer = movieGoerRepository.findByUserName(userName).orElseThrow(UnsupportedOperationException::new);
        MovieProgram movieProgram = movieProgramRepository.findById(scheduleId)
                .orElseThrow(UnsupportedOperationException::new);

        Booking booking = new Booking(
                movieGoer.getUserId(),
                movieProgram.movie().movieName(),
                movieProgram.scheduleDate(),
                movieProgram.getScheduleId(),
                numberOfSeatsBooked);

        return bookingRepository.save(booking);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public void cancelBookingFor(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}
