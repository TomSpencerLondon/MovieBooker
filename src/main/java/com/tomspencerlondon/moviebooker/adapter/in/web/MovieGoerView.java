package com.tomspencerlondon.moviebooker.adapter.in.web;

import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;

public class MovieGoerView {
    private String userName;
    private Integer loyaltyPoints;

    public static MovieGoerView from(MovieGoer movieGoer) {
        MovieGoerView movieGoerView = new MovieGoerView();
        movieGoerView.setUserName(movieGoer.userName());
        movieGoerView.setLoyaltyPoints(movieGoer.loyaltyPoints());
        return movieGoerView;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
