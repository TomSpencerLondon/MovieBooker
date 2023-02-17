package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_programs")
public class MovieProgramDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long scheduleId;

    public void setScheduleId(Long id) {
        this.scheduleId = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    private LocalDateTime scheduleDate;

    private Integer seats;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private List<BookingDbo> bookings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieDbo movie;

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public List<BookingDbo> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDbo> bookings) {
        this.bookings = bookings;
    }

    public MovieDbo getMovie() {
        return movie;
    }

    public void setMovie(MovieDbo movie) {
        this.movie = movie;
    }
}
