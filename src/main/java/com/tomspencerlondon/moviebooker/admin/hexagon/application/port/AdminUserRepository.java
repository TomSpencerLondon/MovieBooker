package com.tomspencerlondon.moviebooker.admin.hexagon.application.port;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminUser;
import com.tomspencerlondon.moviebooker.common.hexagon.User;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;

import java.util.Optional;

public interface AdminUserRepository {
    AdminUser save(AdminUser adminMovie);

    Optional<AdminUser> findByUserName(String username);
}
