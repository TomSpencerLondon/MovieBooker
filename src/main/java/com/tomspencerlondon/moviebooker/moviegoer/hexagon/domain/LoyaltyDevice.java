package com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain;

public interface LoyaltyDevice {
    void addSeatsToCard(int seats);

    int loyaltySeats();

    int updatedLoyaltyPoints();
}
