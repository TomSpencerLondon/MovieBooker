package com.tomspencerlondon.moviebooker.hexagon.application;

public class BookingOutcome {
    private final boolean success;


    public BookingOutcome(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
