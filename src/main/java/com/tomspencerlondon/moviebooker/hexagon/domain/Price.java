package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;

public class Price {
    public static final int LOYALTY_POINTS_PER_SEAT = 5;
    private int loyaltyPointsUpdate;
    private BigDecimal amountPaid;

    public Price(int loyaltyPointsUpdate, BigDecimal amountPaid) {
        this.loyaltyPointsUpdate = loyaltyPointsUpdate;
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

        return new Price(runningLoyaltyPoints, amountPaid);
    }


    public int loyaltyPointsUpdate() {
        return loyaltyPointsUpdate;
    }

    public BigDecimal amountPaid() {
        return amountPaid;
    }
}
