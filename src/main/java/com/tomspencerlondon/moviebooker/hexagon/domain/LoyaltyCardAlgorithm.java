package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;

public interface LoyaltyCardAlgorithm {
    void addSeatsToCard(int seats);

    int loyaltySeats();

    int updatedLoyaltyPoints();
}
