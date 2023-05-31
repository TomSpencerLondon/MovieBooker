package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class AddMovieForm {
    @NotNull
    private String movieName;

    @NotNull
    private MultipartFile movieImage;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private String description;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public MultipartFile getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(MultipartFile movieImage) {
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
