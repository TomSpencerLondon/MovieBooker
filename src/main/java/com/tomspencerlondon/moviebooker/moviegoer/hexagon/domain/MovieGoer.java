package com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain;

public class MovieGoer {
    private Long userId;

    private String userName;

    private String password;

    private Integer loyaltyPoints;

    private boolean isLoyaltyUser;
    private boolean askedForLoyalty;
    private Role role;

    public MovieGoer(String userName, String password, Integer loyaltyPoints, boolean isLoyaltyUser, boolean askedForLoyalty,
        Role role) {
        this.userName = userName;
        this.password = password;
        this.loyaltyPoints = loyaltyPoints;
        this.isLoyaltyUser = isLoyaltyUser;
        this.askedForLoyalty = askedForLoyalty;
        this.role = role;
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

    public void confirmPayment(Payment payment) {
        this.loyaltyPoints = payment.updatedLoyaltyPoints();
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

    public LoyaltyDevice loyaltyCard() {
        if (this.isLoyaltyUser) {
            return new LoyaltyCard(this);
        }

        return new NonLoyaltyLoyaltyCard();
    }

    public void updateLoyaltyPoints(int runningLoyaltyPoints) {
        this.loyaltyPoints = runningLoyaltyPoints;
    }

    public Role role() {
        return role;
    }
}
