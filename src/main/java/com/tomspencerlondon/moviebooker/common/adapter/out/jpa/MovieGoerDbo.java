package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "moviegoer")
public class MovieGoerDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moviegoerId;
    private Integer loyaltyPoints;
    private Boolean isLoyaltyUser;
    private Boolean askedForLoyalty;

    @Column(name = "user_id")
    private Long userId;


    public void setMoviegoerId(Long id) {
        this.moviegoerId = id;
    }

    public Long getMoviegoerId() {
        return moviegoerId;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public boolean getIsLoyaltyUser() {
        return isLoyaltyUser;
    }

    public boolean getAskedForLoyalty() {
        return askedForLoyalty;
    }

    public void setIsLoyaltyUser(boolean loyaltyUser) {
        this.isLoyaltyUser = loyaltyUser;
    }

    public void setAskedForLoyalty(boolean askedForLoyalty) {
        this.askedForLoyalty = askedForLoyalty;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
