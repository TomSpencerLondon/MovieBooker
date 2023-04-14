package com.tomspencerlondon.moviebooker.admin.adapter.in.web;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class AddProgramForm {

    private BigDecimal price;
    private LocalDateTime scheduleDate;
    private Integer seats;
    private Long movieId;
    private List<AdminMovieView> adminMovies;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

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

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public void setAdminMovies(List<AdminMovieView> adminMovies) {
        this.adminMovies = adminMovies;
    }

    public List<AdminMovieView> getAdminMovies() {
        return adminMovies;
    }
}

