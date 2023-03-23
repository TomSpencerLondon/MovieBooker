package com.tomspencerlondon.moviebooker.hexagon.domain;

public class MovieGoer {
    private Long userId;

    private String userName;

    private String password;

    private Integer loyaltyPoints;

    private boolean isLoyaltyUser;
    private boolean askedForLoyalty;

    public MovieGoer(String userName, String password, Integer loyaltyPoints, boolean isLoyaltyUser, boolean askedForLoyalty) {
        this.userName = userName;
        this.password = password;
        this.loyaltyPoints = loyaltyPoints;
        this.isLoyaltyUser = isLoyaltyUser;
        this.askedForLoyalty = askedForLoyalty;
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

    public void confirmBooking(Booking booking) {
        requireBookingIsForUser(booking);

        this.loyaltyPoints = booking.loyaltyPointsUpdated();
    }

    private void requireBookingIsForUser(Booking booking) {
        if (booking.movieGoerId() != this.userId) {
            throw new IllegalArgumentException();
        }
    }

    public void askForLoyalty(boolean isLoyaltyUser) {
        this.askedForLoyalty = true;
        this.isLoyaltyUser = isLoyaltyUser;
    }

    public boolean isLoyaltyUser() {
        return isLoyaltyUser;
    }

    public boolean isAskedForLoyalty() {
        return askedForLoyalty;
    }

    public LoyaltyCardAlgorithm loyaltyCard() {
        if (this.isLoyaltyUser) {
            return new LoyaltyCard(this);
        }

        return new NonLoyaltyLoyaltyCardAlgorithm();
    }

    public void updateLoyaltyPoints(int runningLoyaltyPoints) {
        this.loyaltyPoints = runningLoyaltyPoints;
    }
}
