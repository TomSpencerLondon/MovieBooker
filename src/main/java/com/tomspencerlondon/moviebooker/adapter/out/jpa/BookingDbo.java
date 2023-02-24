package com.tomspencerlondon.moviebooker.adapter.out.jpa;


import jakarta.persistence.*;

import java.math.BigDecimal;

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

    private BigDecimal price;

    private Integer loyaltyPointCost;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal pricePaid) {
        this.price = pricePaid;
    }

    public void setLoyaltyPointCost(int loyaltyPointCost) {
        this.loyaltyPointCost = loyaltyPointCost;
    }

    public Integer getLoyaltyPointCost() {
        return loyaltyPointCost;
    }
}
