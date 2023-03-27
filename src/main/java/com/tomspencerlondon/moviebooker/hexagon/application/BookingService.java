package com.tomspencerlondon.moviebooker.hexagon.application;

import com.tomspencerlondon.moviebooker.hexagon.application.port.BookingRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.PaymentRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.*;

import java.math.BigDecimal;
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

    public void save(Booking booking, Payment payment) {
        MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
                .orElseThrow(IllegalArgumentException::new);
        movieGoer.confirmPayment(payment);
        movieGoerRepository.save(movieGoer);
        Booking savedBooking = bookingRepository.save(booking);
        payment.associateBooking(savedBooking);
        paymentRepository.save(payment);
    }

    public Payment calculatePayment(Booking booking) {
        MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
                .orElseThrow(IllegalArgumentException::new);

        LoyaltyDevice loyaltyDevice = movieGoer.loyaltyCard();
        int numberOfSeats = booking.numberOfSeatsBooked();
        loyaltyDevice.addSeatsToCard(numberOfSeats);
        int seatsToPayFor = numberOfSeats - loyaltyDevice.loyaltySeats();
        BigDecimal amountPaid = booking.seatPrice().multiply(new BigDecimal(seatsToPayFor));

        return new Payment(amountPaid, loyaltyDevice.updatedLoyaltyPoints(), LocalDateTime.now());
    }
}
