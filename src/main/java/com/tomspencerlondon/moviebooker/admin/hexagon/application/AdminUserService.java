package com.tomspencerlondon.moviebooker.admin.hexagon.application;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.port.AdminUserRepository;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminUser;

import java.util.Optional;

public class AdminUserService {
    private AdminUserRepository adminUserRepository;

    public AdminUserService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    public AdminUser save(AdminUser adminUser) {
        Optional<AdminUser> foundAdminUser = adminUserRepository.findByUserName(adminUser.userName());

        if (foundAdminUser.isPresent()) {
            throw new RuntimeException("User is already registered");
        }

        return adminUserRepository.save(adminUser);
    }
}
