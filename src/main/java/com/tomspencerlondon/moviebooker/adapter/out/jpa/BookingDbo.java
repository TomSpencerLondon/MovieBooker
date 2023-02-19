package com.tomspencerlondon.moviebooker.adapter.out.jpa;


import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class BookingDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private MovieProgramDbo movieProgram;

    private Long userId;

    private Integer numberOfSeatsBooked;

    public void setBookingId(Long id) {
        this.bookingId = id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public MovieProgramDbo getMovieProgram() {
        return movieProgram;
    }

    public void setMovieProgram(MovieProgramDbo scheduleId) {
        this.movieProgram = scheduleId;
    }

    public int getNumberOfSeatsBooked() {
        return numberOfSeatsBooked;
    }

    public void setNumberOfSeatsBooked(Integer numberOfSeatsBooked) {
        this.numberOfSeatsBooked = numberOfSeatsBooked;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @ManyToOne(optional = false)
    private MovieGoerDbo movieGoerDbo;

    public MovieGoerDbo getMovieGoerDbo() {
        return movieGoerDbo;
    }

    public void setMovieGoerDbo(MovieGoerDbo movieGoerDbo) {
        this.movieGoerDbo = movieGoerDbo;
    }
}
