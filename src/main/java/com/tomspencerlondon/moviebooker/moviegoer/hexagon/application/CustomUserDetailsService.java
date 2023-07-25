package com.tomspencerlondon.moviebooker.moviegoer.hexagon.application;

import com.tomspencerlondon.moviebooker.common.hexagon.application.port.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.tomspencerlondon.moviebooker.common.hexagon.User> user = userRepository.findByUserName(username);
        User myUser = user.map((u) -> new User(
                        u.userName(),
                        u.password(),
                        List.of(new SimpleGrantedAuthority(u.role().value()))))
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        System.out.println("USER!!!!! - " + user.get().role().value());

        return myUser;
    }

}
