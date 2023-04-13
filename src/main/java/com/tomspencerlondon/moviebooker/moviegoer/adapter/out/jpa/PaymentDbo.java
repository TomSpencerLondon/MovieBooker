package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class PaymentDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int loyaltyPoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "booking_id")
    private Long bookingId;

    private BigDecimal amount;
    private LocalDateTime date;


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setLoyaltyPoints(int updatedLoyaltyPoints) {
        this.loyaltyPoints = updatedLoyaltyPoints;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }
}
