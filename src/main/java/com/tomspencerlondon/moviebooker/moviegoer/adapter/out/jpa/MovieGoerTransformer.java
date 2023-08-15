package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieGoerDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.UserDbo;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;
import org.springframework.stereotype.Service;

@Service("jpaMovieGoerTransformer")
public class MovieGoerTransformer {
    public MovieGoerDbo toMovieGoerDbo(MovieGoer movieGoer, Long userId) {
        MovieGoerDbo movieGoerDbo = new MovieGoerDbo();
        movieGoerDbo.setMoviegoerId(movieGoer.getMovieGoerId());
        movieGoerDbo.setLoyaltyPoints(movieGoer.loyaltyPoints());
        movieGoerDbo.setIsLoyaltyUser(movieGoer.isLoyaltyUser());
        movieGoerDbo.setAskedForLoyalty(movieGoer.isAskedForLoyalty());
        movieGoerDbo.setUserId(userId);
        return movieGoerDbo;
    }

    public MovieGoer toMovieGoer(MovieGoerDbo movieGoerDbo, UserDbo userDbo) {
        MovieGoer movieGoer = new MovieGoer(
                userDbo.getUserId(),
                movieGoerDbo.getMoviegoerId(), userDbo.getUserName(),
                userDbo.getPassword(),
                movieGoerDbo.getLoyaltyPoints(),
                movieGoerDbo.getIsLoyaltyUser(),
                movieGoerDbo.getAskedForLoyalty(),
                userDbo.getRole());

        return movieGoer;
    }
}
