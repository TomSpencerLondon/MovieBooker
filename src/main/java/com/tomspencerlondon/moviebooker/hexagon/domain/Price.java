package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;

public class Price {
    public static final int LOYALTY_POINTS_PER_SEAT = 5;
    private int loyaltyPointChange;
    private BigDecimal amountPaid;

    public Price(int loyaltyPointChange, BigDecimal amountPaid) {
        this.loyaltyPointChange = loyaltyPointChange;
        this.amountPaid = amountPaid;
    }

    public static Price calculatePrice(int numberOfSeats,
                                       int movieGoerCurrentLoyaltyPoints,
                                       BigDecimal seatPrice) {
        int runningLoyaltyPoints = movieGoerCurrentLoyaltyPoints;
        BigDecimal amountPaid = new BigDecimal(0);
        for (int i = 0; i < numberOfSeats; i++) {
            if (runningLoyaltyPoints >= 5) {
                runningLoyaltyPoints = runningLoyaltyPoints - 5;
                continue;
            }
            runningLoyaltyPoints++;
            amountPaid = amountPaid.add(seatPrice);
        }

        int loyaltyPointChange = runningLoyaltyPoints - movieGoerCurrentLoyaltyPoints;
        return new Price(loyaltyPointChange, amountPaid);
    }


    public int loyaltyPointChange() {
        return loyaltyPointChange;
    }

    public BigDecimal amountPaid() {
        return amountPaid;
    }
}
