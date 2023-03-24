package com.tomspencerlondon.moviebooker.hexagon.domain;

public interface LoyaltyDevice {
    void addSeatsToCard(int seats);

    int loyaltySeats();

    int updatedLoyaltyPoints();
}
