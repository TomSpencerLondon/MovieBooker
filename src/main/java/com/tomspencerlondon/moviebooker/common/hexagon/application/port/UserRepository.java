package com.tomspencerlondon.moviebooker.common.hexagon.application.port;

import com.tomspencerlondon.moviebooker.common.hexagon.User;

import java.util.Optional;

public interface UserRepository {
  Optional<User> findByUserName(String username);
}
