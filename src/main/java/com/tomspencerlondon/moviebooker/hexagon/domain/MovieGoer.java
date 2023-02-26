package com.tomspencerlondon.moviebooker.hexagon.domain;

public class MovieGoer {
    private Long userId;

    private String userName;

    private String password;

    private Integer loyaltyPoints;

    public MovieGoer(String userName, String password, Integer loyaltyPoints) {
        this.userName = userName;
        this.password = password;
        this.loyaltyPoints = loyaltyPoints;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String userName() {
        return userName;
    }

    public String password() {
        return password;
    }

    public Integer loyaltyPoints() {
        return loyaltyPoints;
    }

    public void adjustLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints += loyaltyPoints;
        this.loyaltyPoints = Math.max(this.loyaltyPoints, 0);
    }

    public void decreaseLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints -= loyaltyPoints;
    }
}