package com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain;

public class LoyaltyCard implements LoyaltyDevice {
    public static final int LOYALTY_POINTS_PER_SEAT = 5;

    private int updatedLoyaltyPoints;
    private int loyaltySeats;

    public LoyaltyCard(MovieGoer movieGoer) {
        this.updatedLoyaltyPoints = movieGoer.loyaltyPoints();
        loyaltySeats = 0;
    }

    public void addSeatsToCard(int seats) {
        for (int i = 0; i < seats; i++) {
            if (updatedLoyaltyPoints >= LOYALTY_POINTS_PER_SEAT) {
                loyaltySeats++;
                updatedLoyaltyPoints = updatedLoyaltyPoints - LOYALTY_POINTS_PER_SEAT;
                continue;
            }
            updatedLoyaltyPoints++;
        }
    }

    @Override
    public int loyaltySeats() {
        return loyaltySeats;
    }

    @Override
    public int updatedLoyaltyPoints() {
        return updatedLoyaltyPoints;
    }
}
