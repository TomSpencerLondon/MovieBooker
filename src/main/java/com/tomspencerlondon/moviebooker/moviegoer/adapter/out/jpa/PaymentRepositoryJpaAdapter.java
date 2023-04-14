package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.PaymentDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.PaymentJpaRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.PaymentRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PaymentRepositoryJpaAdapter implements PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentTransformer paymentTransformer;

    public PaymentRepositoryJpaAdapter(PaymentJpaRepository paymentJpaRepository, PaymentTransformer paymentTransformer) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.paymentTransformer = paymentTransformer;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentDbo paymentDbo = paymentTransformer.toPaymentDbo(payment);
        PaymentDbo savedPaymentDbo = paymentJpaRepository.save(paymentDbo);
        return paymentTransformer.toPayment(savedPaymentDbo);
    }

    @Override
    public List<Payment> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Payment> findById(Long paymentId) {
        throw new UnsupportedOperationException();
    }


}
