package com.tomspencerlondon.moviebooker.hexagon.application.port;

import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    List<Payment> findAll();

    Optional<Payment> findById(Long paymentId);
}
