package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

public class AdminProgramView {
    private Long scheduleId;
    private LocalDateTime scheduleDate;
    private Integer totalSeats;
    private AdminMovieView movie;
    private BigDecimal seatPrice;

    public static AdminProgramView from(AdminProgram adminProgram) {
        AdminProgramView adminProgramView = new AdminProgramView();
        adminProgramView.setScheduleId(adminProgram.getScheduleId());
        adminProgramView.setScheduleDate(adminProgram.scheduleDate());
        adminProgramView.setTotalSeats(adminProgram.totalSeats());
        adminProgramView.setMovie(AdminMovieView.from(adminProgram.movie()));
        adminProgramView.setSeatPrice(adminProgram.price());
        return adminProgramView;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public AdminMovieView getMovie() {
        return movie;
    }

    public void setMovie(AdminMovieView movie) {
        this.movie = movie;
    }

    public String getMovieName() {
        return this.movie.getMovieName();
    }

    public String getSeatPriceText() {
        return String.format("£%.2f", seatPrice);
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }
}
