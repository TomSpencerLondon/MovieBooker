package com.tomspencerlondon.moviebooker.moviegoer.hexagon.application;

import com.tomspencerlondon.moviebooker.common.hexagon.application.port.BookingRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.PaymentRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.*;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private BookingRepository bookingRepository;
    private MovieProgramRepository movieProgramRepository;

    private MovieGoerRepository movieGoerRepository;

    private PaymentRepository paymentRepository;

    public BookingService(BookingRepository bookingRepository, MovieProgramRepository movieProgramRepository, MovieGoerRepository movieGoerRepository, PaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.movieProgramRepository = movieProgramRepository;
        this.movieGoerRepository = movieGoerRepository;
        this.paymentRepository = paymentRepository;
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

    public Notification payForBooking(Booking booking, Payment payment) {
        MovieProgram movieProgram = booking.movieProgram();
        Notification notification = new Notification();
        if (!movieProgram.seatsAvailableFor(booking.numberOfSeatsBooked())) {
            notification.addError("No seats available");
            return notification;
        }

        MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
                .orElseThrow(IllegalArgumentException::new);
        movieGoer.confirmPayment(payment);
        movieGoerRepository.save(movieGoer);
        Booking savedBooking = bookingRepository.save(booking);
        payment.associateBooking(savedBooking);
        paymentRepository.save(payment);

        return notification;
    }

    public Payment calculatePayment(Booking booking) {
        MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
                .orElseThrow(IllegalArgumentException::new);

        BookingTransaction bookingTransaction = new BookingTransaction(booking, movieGoer, LocalDateTime.now());
        return bookingTransaction.payment();
    }

    public AmendBookingTransaction createAmendTransaction(Long bookingId, int extraSeats) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(IllegalArgumentException::new);
        MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
                .orElseThrow(IllegalArgumentException::new);

        return new AmendBookingTransaction(
                booking, movieGoer,
                extraSeats,
                LocalDateTime.now());
    }

    public Notification amendBooking(Long bookingId, int additionalSeats, Payment payment) {
        Notification notification = new Notification();

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(IllegalArgumentException::new);
        boolean seatsAvailable = booking.movieProgram().seatsAvailableFor(additionalSeats);

        if (!seatsAvailable) {
            notification.addError("Seats not available");
            return notification;
        }

        booking.addSeats(additionalSeats);
        MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
                .orElseThrow(IllegalArgumentException::new);
        movieGoer.confirmPayment(payment);
        movieGoerRepository.save(movieGoer);
        Booking savedBooking = bookingRepository.save(booking);
        payment.associateBooking(savedBooking);
        paymentRepository.save(payment);

        return notification;
    }

    public Booking findBooking(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(IllegalArgumentException::new);
    }
}
