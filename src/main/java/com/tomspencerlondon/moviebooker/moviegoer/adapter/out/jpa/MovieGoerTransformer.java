package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.UserDbo;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;
import org.springframework.stereotype.Service;

@Service("jpaMovieGoerTransformer")
public class MovieGoerTransformer {
    public UserDbo toMovieGoerDbo(MovieGoer movieGoer) {
        UserDbo userDbo = new UserDbo();
        userDbo.setUserId(movieGoer.getUserId());
        userDbo.setUserName(movieGoer.userName());
        userDbo.setPassword(movieGoer.password());
        userDbo.setLoyaltyPoints(movieGoer.loyaltyPoints());
        userDbo.setIsLoyaltyUser(movieGoer.isLoyaltyUser());
        userDbo.setAskedForLoyalty(movieGoer.isAskedForLoyalty());
        userDbo.setRole(movieGoer.role());
        return userDbo;
    }

    public MovieGoer toMovieGoer(UserDbo userDbo) {
        MovieGoer movieGoer = new MovieGoer(
                userDbo.getUserName(),
                userDbo.getPassword(),
                userDbo.getLoyaltyPoints(),
                userDbo.getIsLoyaltyUser(),
                userDbo.getAskedForLoyalty(),
                userDbo.getRole());

        movieGoer.setUserId(userDbo.getUserId());

        return movieGoer;
    }
}
