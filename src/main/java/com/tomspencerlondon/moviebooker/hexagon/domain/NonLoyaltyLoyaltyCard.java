package com.tomspencerlondon.moviebooker.hexagon.domain;

public class NonLoyaltyLoyaltyCard implements LoyaltyDevice {

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
