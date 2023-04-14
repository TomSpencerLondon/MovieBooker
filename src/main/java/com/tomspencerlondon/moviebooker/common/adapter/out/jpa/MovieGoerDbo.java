package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "moviegoers", uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class MovieGoerDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    private String password;

    private Integer loyaltyPoints;
    private Boolean isLoyaltyUser;
    private Boolean askedForLoyalty;


    public void setUserId(Long id) {
        this.userId = id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
