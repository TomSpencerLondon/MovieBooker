package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private BigDecimal amountPaid;
    private final int updatedLoyaltyPoints;
    private LocalDateTime paymentDate;
    private Long movieGoerId;

    public Payment(Long movieGoerId, BigDecimal amountPaid, int updatedLoyaltyPoints, LocalDateTime paymentDate) {
        this.amountPaid = amountPaid;
        this.updatedLoyaltyPoints = updatedLoyaltyPoints;
        this.paymentDate = paymentDate;
        this.movieGoerId = movieGoerId;
    }

    public BigDecimal amountPaid() {
        return amountPaid;
    }

    public LocalDateTime paymentDate() {
        return paymentDate;
    }

    public int updatedLoyaltyPoints() {
        return updatedLoyaltyPoints;
    }

    public Long movieGoerId() {
        return movieGoerId;
    }
}
