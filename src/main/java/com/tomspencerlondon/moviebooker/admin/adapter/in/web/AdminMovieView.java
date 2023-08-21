package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;

public class AdminMovieView {
    private Long movieId;
    private String movieName;
    private String image;
    private String releaseDate;
    private String description;

    public static AdminMovieView from(AdminMovie movie, String imagePath) {
        AdminMovieView adminMovieView = new AdminMovieView();
        adminMovieView.setMovieId(movie.getId());
        adminMovieView.setMovieName(movie.movieName());
        adminMovieView.setImage(movie.image());
        adminMovieView.setReleaseDate(movie.releaseDateText());
        adminMovieView.setDescription(movie.description());
        return adminMovieView;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
