package com.tomspencerlondon.moviebooker.adapter.in.web;

import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import com.tomspencerlondon.moviebooker.hexagon.domain.Payment;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class BookingForm {

    @NotBlank
    private Long movieGoerId;

    @NotBlank
    private String movieName;

    @NotBlank
    private LocalDateTime scheduleDate;

    @NotBlank
    private Long scheduleId;

    @NotBlank
    private int numberOfSeats;

    @NotBlank
    private BigDecimal amountToPay;

    @NotBlank
    private int updatedLoyaltyPoints;

    @NotBlank
    private BigDecimal seatPrice;


    public static BookingForm from(Booking booking, Payment payment) {
        BookingForm bookingForm = from(booking);
        bookingForm.setAmountToPay(payment.amountPaid());
        bookingForm.setUpdatedLoyaltyPoints(payment.updatedLoyaltyPoints());
        return bookingForm;
    }

    public static BookingForm from(Booking booking) {
        BookingForm bookingForm = new BookingForm();
        bookingForm.setMovieGoerId(booking.movieGoerId());
        bookingForm.setMovieName(booking.filmName());
        bookingForm.setScheduleDate(booking.bookingTime());
        bookingForm.setScheduleId(booking.scheduleId());
        bookingForm.setNumberOfSeats(booking.numberOfSeatsBooked());
        bookingForm.setSeatPrice(booking.seatPrice());

        return bookingForm;
    }

    public static Booking to(BookingForm bookingForm, MovieProgram movieProgram) {
        Booking booking = new Booking(
                bookingForm.getMovieGoerId(),
                movieProgram,
                bookingForm.getNumberOfSeats(),
                bookingForm.getSeatPrice()
        );
        return booking;
    }

    public static Payment toPayment(BookingForm bookingForm) {
        return new Payment(bookingForm.amountToPay(), bookingForm.getUpdatedLoyaltyPoints(), LocalDateTime.now());
    }

    public Long getMovieGoerId() {
        return movieGoerId;
    }

    public void setMovieGoerId(Long movieGoerId) {
        this.movieGoerId = movieGoerId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public String getScheduleDateText() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return scheduleDate.format(dateTimeFormatter);
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public BigDecimal amountToPay() {
        return amountToPay;
    }

    public String getPriceText() {
        return String.format("£%.2f", amountToPay);
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

    public int getUpdatedLoyaltyPoints() {
        return updatedLoyaltyPoints;
    }

    public void setUpdatedLoyaltyPoints(int updatedLoyaltyPoints) {
        this.updatedLoyaltyPoints = updatedLoyaltyPoints;
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }
}
