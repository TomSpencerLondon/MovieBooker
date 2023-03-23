package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;

public class NonLoyaltyLoyaltyCardAlgorithm implements LoyaltyCardAlgorithm {

    @Override
    public void addSeatsToCard(int seats) {
    }

    @Override
    public int loyaltySeats() {
        return 0;
    }

    @Override
    public int updatedLoyaltyPoints() {
        return 0;
    }
}
