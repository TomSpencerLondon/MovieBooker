package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.Payment;

public class PaymentTransformer {

    PaymentDbo toPaymentDbo(Payment payment) {
        PaymentDbo paymentDbo = new PaymentDbo();
        paymentDbo.setAmount(payment.amountPaid());
        paymentDbo.setDate(payment.paymentDate());

        return paymentDbo;
    }
}
