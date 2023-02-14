package com.tomspencerlondon.moviebooker.adapter.out.jpa;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class MovieDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    private String movieName;
    private String movieImage;

    private LocalDate releaseDate;

    @Column(length = 1000)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private List<MovieProgramDbo> moviePrograms = new ArrayList<>();

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

    public List<MovieProgramDbo> getMoviePrograms() {
        return moviePrograms;
    }

    public void setMoviePrograms(List<MovieProgramDbo> moviePrograms) {
        this.moviePrograms = moviePrograms;
    }
}
