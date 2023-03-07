package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;

public class LoyaltyPriceCalculation implements PriceCalculation {
    public static final int LOYALTY_POINTS_PER_SEAT = 5;

    @Override
    public Price calculatePrice(int numberOfSeats, int movieGoerCurrentLoyaltyPoints, BigDecimal seatPrice) {
        int runningLoyaltyPoints = movieGoerCurrentLoyaltyPoints;
        BigDecimal amountPaid = new BigDecimal(0);
        for (int i = 0; i < numberOfSeats; i++) {
            if (runningLoyaltyPoints >= LOYALTY_POINTS_PER_SEAT) {
                runningLoyaltyPoints = runningLoyaltyPoints - LOYALTY_POINTS_PER_SEAT;
                continue;
            }
            runningLoyaltyPoints++;
            amountPaid = amountPaid.add(seatPrice);
        }

        return new Price(runningLoyaltyPoints, amountPaid);

    }
}
