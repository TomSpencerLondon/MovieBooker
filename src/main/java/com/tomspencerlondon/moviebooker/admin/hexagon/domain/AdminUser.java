package com.tomspencerlondon.moviebooker.admin.hexagon.domain;

import com.tomspencerlondon.moviebooker.common.hexagon.User;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Role;

public class AdminUser extends User {
    private Long userId;

    private String userName;

    private String password;

    private Role role;

    public AdminUser(Long userId, String userName, String password, Role role) {
        super(userId, userName, password, role);
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    @Override
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

    public Role role() {
        return role;
    }
}
