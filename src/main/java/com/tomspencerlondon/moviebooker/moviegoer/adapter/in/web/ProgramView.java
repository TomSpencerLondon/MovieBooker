package com.tomspencerlondon.moviebooker.moviegoer.adapter.in.web;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProgramView {
    private Long scheduleId;
    private LocalDateTime scheduleDate;
    private Integer availableSeats;
    private MovieView movie;
    private BigDecimal seatPrice;
    private boolean bookable;

    public static ProgramView from(MovieProgram movieProgram, MovieView movieView) {
        ProgramView programView = new ProgramView();
        programView.setScheduleId(movieProgram.getScheduleId());
        programView.setScheduleDate(movieProgram.scheduleDate());
        programView.setAvailableSeats(movieProgram.availableSeats());
        programView.setMovie(movieView);
        programView.setSeatPrice(movieProgram.price());
        programView.setBookable(movieProgram.canBook());
        return programView;
    }

    private void setBookable(boolean bookable) {
        this.bookable = bookable;
    }

    public boolean isBookable() {
        return bookable;
    }

    private void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
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

    public String getSeatsText() {
        return availableSeats > 0 ? availableSeats + " available" : "Sold Out!";
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public MovieView getMovie() {
        return movie;
    }

    public void setMovie(MovieView movie) {
        this.movie = movie;
    }

    public String getMovieName() {
        return this.movie.getMovieName();
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }

    public String getSeatPriceText() {
        return String.format("£%.2f", seatPrice);
    }
}
