package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.hexagon.User;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryJpaAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryJpaAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> findByUserName(String username) {
        Optional<UserDbo> userDbo = userJpaRepository.findByUserName(username);

        if (userDbo.isEmpty()) {
            return Optional.empty();
        } else {
            UserDbo foundUserDbo = userDbo.get();

            return Optional.of(
                    new User(foundUserDbo.getUserId(),
                            foundUserDbo.getUserName(),
                            foundUserDbo.getPassword(),
                            foundUserDbo.getRole())
            );
        }
    }
}
