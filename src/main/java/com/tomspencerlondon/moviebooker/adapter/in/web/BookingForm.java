package com.tomspencerlondon.moviebooker.adapter.in.web;

import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private BigDecimal price;


    public static BookingForm from(Booking booking) {
        BookingForm bookingForm = new BookingForm();
        bookingForm.setMovieGoerId(booking.movieGoerId());
        bookingForm.setMovieName(booking.filmName());
        bookingForm.setScheduleDate(booking.bookingTime());
        bookingForm.setScheduleId(booking.scheduleId());
        bookingForm.setNumberOfSeats(booking.numberOfSeatsBooked());
        bookingForm.setPrice(booking.price());
        return bookingForm;
    }

    public static Booking to(BookingForm bookingForm) {
        Booking booking = new Booking(
                bookingForm.getMovieGoerId(),
                bookingForm.getMovieName(),
                bookingForm.getScheduleDate(),
                bookingForm.getScheduleId(),
                bookingForm.getNumberOfSeats(),
                bookingForm.getPrice());
        booking.setBookingId(booking.getBookingId());

        return booking;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
