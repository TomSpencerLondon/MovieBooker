package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;

public class AdminMovieView {
    private Long movieId;
    private String movieName;

    public static AdminMovieView from(AdminMovie movie) {
        AdminMovieView adminMovieView = new AdminMovieView();
        adminMovieView.setMovieId(movie.getId());
        adminMovieView.setMovieName(movie.movieName());

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
}
