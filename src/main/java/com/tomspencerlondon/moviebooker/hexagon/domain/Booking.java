package com.tomspencerlondon.moviebooker.hexagon.domain;

public class Booking {
    private Long bookingId;
    private MovieProgram movieProgram;

    public Booking(MovieProgram movieProgram) {
        this.movieProgram = movieProgram;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public MovieProgram getMovieProgram() {
        return movieProgram;
    }
}
