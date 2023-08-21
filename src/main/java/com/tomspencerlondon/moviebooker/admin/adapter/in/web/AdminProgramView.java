package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AdminProgramView {
    private Long scheduleId;
    private LocalDateTime scheduleDate;
    private Integer totalSeats;
    private AdminMovieView movie;
    private BigDecimal seatPrice;
    private String screenName;

    public static AdminProgramView from(AdminProgram adminProgram, AdminMovieView adminMovieView) {
        AdminProgramView adminProgramView = new AdminProgramView();
        adminProgramView.setScheduleId(adminProgram.getScheduleId());
        adminProgramView.setScheduleDate(adminProgram.scheduleDate());
        adminProgramView.setTotalSeats(adminProgram.totalSeats());
        adminProgramView.setMovie(adminMovieView);
        adminProgramView.setSeatPrice(adminProgram.price());
        adminProgramView.setScreenName(adminProgram.getScreen().name());
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

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
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
