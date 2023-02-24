package com.tomspencerlondon.moviebooker.hexagon.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void loyaltyPointsIncreaseAsSeatsAreBought() {
        loyaltyPointAssertion(
                4, 0,
                new BigDecimal(5), 4);
        loyaltyPointAssertion(
                5, 0,
                new BigDecimal(5), 5);
    }

    @Test
    void amountPaidIncreasesAsSeatsAreBought() {
        BigDecimal seatPrice = new BigDecimal(5);
        amountPaidAssertion(4,
                0,
                seatPrice,
                new BigDecimal(20));
        amountPaidAssertion(5, 0,
                seatPrice,
                new BigDecimal(25)
                );
    }

    @Test
    void loyaltyPointsAreReducedInExchangeForFreeSeats() {
        loyaltyPointAssertion(
                6, 0,
                new BigDecimal(5), 0);

        loyaltyPointAssertion(
                6, 5,
                new BigDecimal(5), 5);

        loyaltyPointAssertion(
                5, 6,
                new BigDecimal(5), 5);
    }

    @Test
    void priceIsReducedInExchangeForLoyaltyPoints() {
        BigDecimal seatPrice = new BigDecimal(5);
        amountPaidAssertion(6, 0,
                seatPrice, new BigDecimal(25));

        amountPaidAssertion(
                6, 5,
                seatPrice, new BigDecimal(25));

        amountPaidAssertion(
                5, 6,
                new BigDecimal(5), new BigDecimal(20));

    }

    private static void amountPaidAssertion(int numberOfSeats, int movieGoerCurrentLoyaltyPoints, BigDecimal seatPrice, BigDecimal expected) {
        Price price = Price.calculatePrice(numberOfSeats,
                movieGoerCurrentLoyaltyPoints, seatPrice);

        assertThat(price.amountPaid())
                .isEqualTo(expected);
    }


    private static void loyaltyPointAssertion(int numberOfSeats, int movieGoerCurrentLoyaltyPoints, BigDecimal seatPrice, int result) {
        Price price = Price.calculatePrice(numberOfSeats,
                movieGoerCurrentLoyaltyPoints, seatPrice);


        assertThat(price.loyaltyPointChange())
                .isEqualTo(result - movieGoerCurrentLoyaltyPoints);
    }
}