package com.tomspencerlondon.moviebooker.hexagon.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {
    private BigDecimal amountPaid;
    private LocalDateTime paymentDate;

    public Payment(BigDecimal amountPaid, LocalDateTime paymentDate) {
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
    }

    public BigDecimal amountPaid() {
        return amountPaid;
    }

    public LocalDateTime paymentDate() {
        return paymentDate;
    }
}
