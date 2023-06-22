package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class AddProgramForm {

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String scheduleDate;

    @NotNull
    private Integer seats;

    @NotNull
    private Long movieId;

    private List<AdminMovieView> adminMovies;
    private List<Screen> screens;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
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

    public void setScreens(List<Screen> screens) {
        this.screens = screens;
    }

    public List<Screen> getScreens() {
        return screens;
    }
}

