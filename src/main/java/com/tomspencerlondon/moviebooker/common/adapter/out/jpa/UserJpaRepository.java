package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserDbo, Long> {
  Optional<UserDbo> findByUserName(String username);
}
