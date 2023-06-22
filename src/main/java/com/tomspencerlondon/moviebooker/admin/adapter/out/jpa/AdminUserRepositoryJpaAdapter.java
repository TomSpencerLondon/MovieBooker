package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.port.AdminUserRepository;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminUser;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieGoerDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.UserDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.UserJpaRepository;
import com.tomspencerlondon.moviebooker.common.utils.Optionals;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AdminUserRepositoryJpaAdapter implements AdminUserRepository {

    private final UserJpaRepository userJpaRepository;

    public AdminUserRepositoryJpaAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }


    @Override
    public AdminUser save(AdminUser adminUser) {
        UserDbo userDbo = new UserDbo();
        userDbo.setUserName(adminUser.userName());
        userDbo.setPassword(adminUser.password());
        userDbo.setRole(adminUser.role());

        UserDbo savedUser = userJpaRepository.save(userDbo);

        return new AdminUser(savedUser.getUserId(), savedUser.getUserName(), savedUser.getPassword(), savedUser.getRole());
    }

    @Override
    public Optional<AdminUser> findByUserName(String username) {
        Optional<AdminUser> adminUser = userJpaRepository
                .findByUserName(username)
                .map(u -> new AdminUser(u.getUserId(), u.getUserName(), u.getPassword(), u.getRole()));

        return Optionals.or(adminUser, Optional.empty());
    }
}
