package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import java.util.List;
import java.util.stream.Collectors;

public class BookingDboCollection {
    private final List<BookingDbo> bookingDbos;

    public BookingDboCollection(List<BookingDbo> bookingDbos) {
        this.bookingDbos = bookingDbos;
    }

    public int totalSeatsBooked() {
        return bookingDbos.stream()
                .map(BookingDbo::getNumberOfSeatsBooked)
                .reduce(0, Integer::sum);
    }
}
