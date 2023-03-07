package com.tomspencerlondon.moviebooker.hexagon.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class NonLoyaltyPriceCalculationTest {

    @Test
    void nonLoyaltyUserPriceIsUpdated() {
        NonLoyaltyPriceCalculation nonLoyaltyPriceCalculation = new NonLoyaltyPriceCalculation();
        Price price = nonLoyaltyPriceCalculation
                .calculatePrice(6, new BigDecimal(5));

        assertThat(price.amountPaid())
                .isEqualTo(new BigDecimal(30));
    }
}