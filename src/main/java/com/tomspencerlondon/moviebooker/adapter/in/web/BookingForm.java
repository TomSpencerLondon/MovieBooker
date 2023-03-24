package com.tomspencerlondon.moviebooker.adapter.in.web;

import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
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


    public static BookingForm from(Booking booking, Payment payment) {
        BookingForm bookingForm = new BookingForm();
        bookingForm.setMovieGoerId(booking.movieGoerId());
        bookingForm.setMovieName(booking.filmName());
        bookingForm.setScheduleDate(booking.bookingTime());
        bookingForm.setScheduleId(booking.scheduleId());
        bookingForm.setNumberOfSeats(booking.numberOfSeatsBooked());
        bookingForm.setAmountToPay(payment.amountPaid());
        bookingForm.setUpdatedLoyaltyPoints(payment.updatedLoyaltyPoints());
        return bookingForm;
    }

    public static Booking to(BookingForm bookingForm) {
        Booking booking = new Booking(
                bookingForm.getMovieGoerId(),
                bookingForm.getMovieName(),
                bookingForm.getScheduleDate(),
                bookingForm.getScheduleId(),
                bookingForm.getNumberOfSeats(),
                bookingForm.amountToPay(),
                bookingForm.getUpdatedLoyaltyPoints()
                );
        return booking;
    }

    public static Payment toPayment(BookingForm bookingForm) {
        return new Payment(bookingForm.getMovieGoerId(), bookingForm.amountToPay(), bookingForm.getUpdatedLoyaltyPoints(), LocalDateTime.now());
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
}
