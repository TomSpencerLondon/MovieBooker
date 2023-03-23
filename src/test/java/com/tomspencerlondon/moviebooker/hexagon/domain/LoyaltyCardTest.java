package com.tomspencerlondon.moviebooker.hexagon.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class LoyaltyCardTest {

    @Test
    void loyaltyPointsIncreaseAsSeatsAreBought() {
        assertSeatsAndLoyaltyPoints(
                4, 0, 0, 4);
        assertSeatsAndLoyaltyPoints(
                5, 0, 0, 5);

        assertSeatsAndLoyaltyPoints(3, 5, 1, 2);
    }

    @Test
    void loyaltyPointsAreReducedInExchangeForFreeSeats() {
        assertSeatsAndLoyaltyPoints(
                6, 0, 1, 0);

        assertSeatsAndLoyaltyPoints(
                6, 5, 1, 5);

        assertSeatsAndLoyaltyPoints(5, 6, 1, 5);
    }

    private static void assertSeatsAndLoyaltyPoints(int numberOfSeatsPurchased, int movieGoerStartLoyaltyPoints,
                                                    int expectedLoyaltySeats, int expectedFinalLoyaltyPoints) {
        MovieGoer movieGoer = new MovieGoer("moviegoer", "password", movieGoerStartLoyaltyPoints, true, true);


        LoyaltyCardAlgorithm loyaltyPriceCalculation = new LoyaltyCard(movieGoer);
        loyaltyPriceCalculation.addSeatsToCard(numberOfSeatsPurchased);

        assertThat(loyaltyPriceCalculation.updatedLoyaltyPoints())
                .isEqualTo(expectedFinalLoyaltyPoints);
        assertThat(loyaltyPriceCalculation.loyaltySeats())
                .isEqualTo(expectedLoyaltySeats);
    }
}