package com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain;

import java.time.LocalDate;
import java.util.List;

public class Movie {

    private Long id;
    private final String movieName;
    private final String image;
    private final LocalDate releaseDate;
    private final String description;

    public Movie(Long id, String movieName, String image, LocalDate releaseDate, String description) {
        this.id = id;
        this.movieName = movieName;
        this.image = image;
        this.releaseDate = releaseDate;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String movieName() {
        return movieName;
    }

    public String image() {
        return image;
    }

    public LocalDate releaseDate() {
        return releaseDate;
    }

    public String releaseDateText() {
        return String.valueOf(releaseDate.getYear());
    }

    public String description() {
        return description;
    }

}
