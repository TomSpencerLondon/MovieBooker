package com.tomspencerlondon.moviebooker.common.hexagon.application.port;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieGoerDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.UserDbo;
import java.util.Optional;

public interface UserRepository {
  Optional<UserDbo> findByUserName(String username);
}
