package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class UserDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    private String password;

    private Integer loyaltyPoints;
    private Boolean isLoyaltyUser;
    private Boolean askedForLoyalty;

    @Enumerated(EnumType.STRING)
    private Role role;


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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
