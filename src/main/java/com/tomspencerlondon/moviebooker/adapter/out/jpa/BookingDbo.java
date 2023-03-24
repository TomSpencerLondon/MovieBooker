package com.tomspencerlondon.moviebooker.adapter.out.jpa;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
public class BookingDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private MovieProgramDbo movieProgram;

    @Column(name = "user_id")
    private Long userId;

    private Integer numberOfSeatsBooked;

    private BigDecimal amountPaid;

    private Integer updatedLoyaltyPoints;

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

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal pricePaid) {
        this.amountPaid = pricePaid;
    }

    public void setUpdatedLoyaltyPoints(int updatedLoyaltyPoints) {
        this.updatedLoyaltyPoints = updatedLoyaltyPoints;
    }

    public Integer getUpdatedLoyaltyPoints() {
        return updatedLoyaltyPoints;
    }
}
