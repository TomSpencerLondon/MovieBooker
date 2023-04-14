package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "movies")
public class MovieDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    public void setMovieId(Long id) {
        this.movieId = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    private String movieName;
    private String movieImage;

    private LocalDate releaseDate;

    @Column(length = 1000)
    private String description;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
