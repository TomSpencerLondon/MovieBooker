package com.tomspencerlondon.moviebooker.hexagon.domain;

public class Movie {

    private Long id;
    private final String movieName;

    public Movie(String movieName) {
        this.movieName = movieName;
    }

    public String movieName() {
        return movieName;
    }
}
