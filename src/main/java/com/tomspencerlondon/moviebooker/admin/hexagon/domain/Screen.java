package com.tomspencerlondon.moviebooker.admin.hexagon.domain;

public class Screen {
    private Long id;
    private int numberOfSeats;

    public Screen(Long id, int numberOfSeats) {
        this.id = id;
        this.numberOfSeats = numberOfSeats;
    }

    public Long getId() {
        return id;
    }

    public int numberOfSeats() {
        return numberOfSeats;
    }
}
