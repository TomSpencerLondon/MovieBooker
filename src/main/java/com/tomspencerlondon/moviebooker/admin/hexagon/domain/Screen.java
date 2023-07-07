package com.tomspencerlondon.moviebooker.admin.hexagon.domain;

public class Screen {
    private Long id;
    private int numberOfSeats;
    private String name;

    public Screen(Long id, int numberOfSeats, String name) {
        this.id = id;
        this.numberOfSeats = numberOfSeats;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public int numberOfSeats() {
        return numberOfSeats;
    }

    public String name() {
        return name;
    }
}
