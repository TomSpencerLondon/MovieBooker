package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MovieProgram {
    private Long scheduleId;
    private final LocalDateTime scheduleDate;
    private final Integer totalSeats;
    private Movie movie;
    private List<Booking> bookings;

    private final BigDecimal price;

    public MovieProgram(Long scheduleId, LocalDateTime scheduleDate, Integer totalSeats, Movie movie, List<Booking> bookings, BigDecimal price) {
        this.scheduleId = scheduleId;
        this.scheduleDate = scheduleDate;
        this.totalSeats = totalSeats;
        this.movie = movie;
        this.bookings = bookings;
        this.price = price;
    }

    public void setScheduleId(Long id) {
        this.scheduleId = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public LocalDateTime scheduleDate() {
        return scheduleDate;
    }

    public String scheduleDateText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return scheduleDate.format(formatter);
    }

    public Integer totalSeats() {
        return totalSeats;
    }

    public String seatsText() {
        return availableSeats() > 0 ? availableSeats() + " available" : "Sold Out!";
    }

    public Movie movie() {
        return movie;
    }

    public int availableSeats() {
        int total = bookings.stream()
                .mapToInt(Booking::numberOfSeatsBooked)
                .sum();
        return totalSeats - total;
    }

    public boolean canBook() {
        return availableSeats() > 0;
    }

    public Booking createBooking(Long movieGoerId, int numberOfSeats) {

        return new Booking(
                movieGoerId,
                movie.movieName(),
                scheduleDate,
                scheduleId,
                numberOfSeats,
                price.multiply(new BigDecimal(numberOfSeats)));
    }

    public BigDecimal price() {
        return price;
    }
}
