package com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovieProgram {
    private Long scheduleId;
    private final LocalDateTime scheduleDate;
    private final Integer totalSeats;
    private Movie movie;

    private final int seatsBooked;
    private final BigDecimal seatPrice;

    public MovieProgram(Long scheduleId, LocalDateTime scheduleDate, Integer totalSeats, Movie movie, int seatsBooked, BigDecimal seatPrice) {
        this.scheduleId = scheduleId;
        this.scheduleDate = scheduleDate;
        this.totalSeats = totalSeats;
        this.movie = movie;
        this.seatsBooked = seatsBooked;
        this.seatPrice = seatPrice;
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

    // TODO: This should not be in the domain - it is view logic
    public String seatsText() {
        return availableSeats() > 0 ? availableSeats() + " available" : "Sold Out!";
    }

    public Movie movie() {
        return movie;
    }

    public int availableSeats() {
        return totalSeats - seatsBooked;
    }

    public boolean canBook() {
        return availableSeats() > 0;
    }

    public Booking createBooking(MovieGoer movieGoer, int numberOfSeats) {
        return new Booking(
                movieGoer.getUserId(),
                this,
                numberOfSeats,
                this.seatPrice);
    }

    public BigDecimal price() {
        return seatPrice;
    }

    public boolean seatsAvailableFor(int additionalSeats) {
        return availableSeats() >= additionalSeats;
    }

    public Long getScreenId() {
        return this.sc;
    }
}
