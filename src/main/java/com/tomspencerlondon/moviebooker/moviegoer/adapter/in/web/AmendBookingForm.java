package com.tomspencerlondon.moviebooker.moviegoer.adapter.in.web;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.AmendBookingTransaction;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Payment;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AmendBookingForm {

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

    @NotBlank
    private int additionalSeats;

    @NotBlank
    private Long bookingId;


    public static AmendBookingForm from(AmendBookingTransaction bookingUpdate, Payment payment) {
        AmendBookingForm amendBookingForm = new AmendBookingForm();
        Booking booking = bookingUpdate.booking();
        amendBookingForm.setBookingId(booking.getBookingId());
        amendBookingForm.setMovieGoerId(booking.movieGoerId());
        amendBookingForm.setMovieName(booking.filmName());
        amendBookingForm.setScheduleDate(booking.bookingTime());
        amendBookingForm.setScheduleId(booking.scheduleId());
        amendBookingForm.setNumberOfSeats(booking.numberOfSeatsBooked());
        amendBookingForm.setAmountToPay(payment.amountPaid());
        amendBookingForm.setSeatPrice(booking.seatPrice());
        amendBookingForm.setUpdatedLoyaltyPoints(payment.updatedLoyaltyPoints());
        amendBookingForm.setAdditionalSeats(bookingUpdate.extraSeats());
        return amendBookingForm;
    }

    private void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    private void setAdditionalSeats(int additionalSeats) {
        this.additionalSeats = additionalSeats;
    }

    public int getAdditionalSeats() {
        return additionalSeats;
    }

    public static Payment toPayment(AmendBookingForm bookingForm) {
        return new Payment(bookingForm.getAmountToPay(), bookingForm.getUpdatedLoyaltyPoints(), LocalDateTime.now());
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
