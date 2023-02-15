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

}
