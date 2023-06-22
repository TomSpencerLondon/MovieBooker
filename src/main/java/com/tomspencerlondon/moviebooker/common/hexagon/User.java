package com.tomspencerlondon.moviebooker.common.hexagon;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Role;

public class User {
    private Long userId;

    private String userName;

    private String password;

    private Role role;

    public User(Long userId, String userName, String password, Role role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String userName() {
        return userName;
    }

    public String password() {
        return password;
    }

    public Role role() {
        return role;
    }
}
