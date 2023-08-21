package com.tomspencerlondon.moviebooker.moviegoer.adapter.in.web;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;

public class MovieView {
  private Long movieId;
  private String movieName;
  private String image;
  private String releaseDate;
  private String description;

  public static MovieView from(Movie movie, String imagePath) {
    MovieView movieView = new MovieView();
    movieView.setMovieId(movie.getId());
    movieView.setMovieName(movie.movieName());
    movieView.setImage(imagePath);
    movieView.setReleaseDate(movie.releaseDateText());
    movieView.setDescription(movie.description());
    return movieView;
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
