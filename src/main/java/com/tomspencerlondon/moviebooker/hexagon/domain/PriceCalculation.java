package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;

public interface PriceCalculation {
    Price calculatePrice(int numberOfSeats,
                         int movieGoerCurrentLoyaltyPoints,
                         BigDecimal seatPrice);
}
