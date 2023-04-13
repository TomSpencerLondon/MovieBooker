package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Payment;
import org.springframework.stereotype.Service;

@Service("jpaPaymentTransformer")
public class PaymentTransformer {

    PaymentDbo toPaymentDbo(Payment payment) {
        PaymentDbo paymentDbo = new PaymentDbo();
        paymentDbo.setAmount(payment.amountPaid());
        paymentDbo.setDate(payment.paymentDate());
        paymentDbo.setBookingId(payment.bookingId());
        paymentDbo.setLoyaltyPoints(payment.updatedLoyaltyPoints());
        return paymentDbo;
    }

    public Payment toPayment(PaymentDbo paymentDbo) {
        return new Payment(paymentDbo.getAmount(),
                paymentDbo.getLoyaltyPoints(),
                paymentDbo.getDate());
    }
}
