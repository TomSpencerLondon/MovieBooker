package com.tomspencerlondon.moviebooker.adapter.in.web;

import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;

public class MovieGoerView {
    private String userName;
    private Integer loyaltyPoints;
    private boolean isLoyaltyUser;
    private boolean askedForLoyalty;

    public static MovieGoerView from(MovieGoer movieGoer) {
        MovieGoerView movieGoerView = new MovieGoerView();
        movieGoerView.setUserName(movieGoer.userName());
        movieGoerView.setLoyaltyPoints(movieGoer.loyaltyPoints());
        movieGoerView.setAskedForLoyalty(movieGoer.isAskedForLoyalty());
        movieGoerView.setLoyaltyUser(movieGoer.isLoyaltyUser());
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

    public boolean isLoyaltyUser() {
        return isLoyaltyUser;
    }

    public void setLoyaltyUser(boolean loyaltyUser) {
        isLoyaltyUser = loyaltyUser;
    }

    public boolean isAskedForLoyalty() {
        return askedForLoyalty;
    }

    public void setAskedForLoyalty(boolean askedForLoyalty) {
        this.askedForLoyalty = askedForLoyalty;
    }
}
