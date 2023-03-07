package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;

public class Price {
    private int loyaltyPointsUpdate;
    private BigDecimal amountPaid;

    public Price(int loyaltyPointsUpdate, BigDecimal amountPaid) {
        this.loyaltyPointsUpdate = loyaltyPointsUpdate;
        this.amountPaid = amountPaid;
    }

    public int loyaltyPointsUpdate() {
        return loyaltyPointsUpdate;
    }

    public BigDecimal amountPaid() {
        return amountPaid;
    }
}
