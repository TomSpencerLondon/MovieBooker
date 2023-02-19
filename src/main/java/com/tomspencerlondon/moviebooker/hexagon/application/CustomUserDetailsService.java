package com.tomspencerlondon.moviebooker.hexagon.application;

import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {

    private final MovieGoerRepository movieGoerRepository;

    public CustomUserDetailsService(MovieGoerRepository movieGoerRepository) {
        this.movieGoerRepository = movieGoerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MovieGoer user = movieGoerRepository.findByUserName(username)
                .orElseThrow(UnsupportedOperationException::new);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.userName(),
                    user.password(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}
