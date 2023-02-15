package com.tomspencerlondon.moviebooker.adapter.out.jpa;


import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class BookingDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bookingId;

    public void setBookingId(Long id) {
        this.bookingId = id;
    }

    public Long getBookingId() {
        return bookingId;
    }
}
