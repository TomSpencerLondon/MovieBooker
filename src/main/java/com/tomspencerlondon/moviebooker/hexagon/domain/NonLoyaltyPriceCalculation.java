package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;

public class NonLoyaltyPriceCalculation implements PriceCalculation {
    @Override
    public Price calculatePrice(int numberOfSeats, BigDecimal seatPrice) {
        return new Price(0, seatPrice.multiply(new BigDecimal(numberOfSeats)));
    }
}
