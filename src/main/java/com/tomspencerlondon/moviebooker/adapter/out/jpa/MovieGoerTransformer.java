package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;
import org.springframework.stereotype.Service;

@Service("jpaMovieGoerTransformer")
public class MovieGoerTransformer {
    public MovieGoerDbo toMovieGoerDbo(MovieGoer movieGoer) {
        MovieGoerDbo movieGoerDbo = new MovieGoerDbo();
        movieGoerDbo.setUserId(movieGoer.getUserId());
        movieGoerDbo.setUserName(movieGoer.userName());
        movieGoerDbo.setPassword(movieGoer.password());
        movieGoerDbo.setLoyaltyPoints(movieGoer.loyaltyPoints());
        movieGoerDbo.setIsLoyaltyUser(movieGoer.isLoyaltyUser());
        movieGoerDbo.setAskedForLoyalty(movieGoer.isAskedForLoyalty());
        return movieGoerDbo;
    }

    public MovieGoer toMovieGoer(MovieGoerDbo movieGoerDbo) {
        MovieGoer movieGoer = new MovieGoer(
                movieGoerDbo.getUserName(),
                movieGoerDbo.getPassword(),
                movieGoerDbo.getLoyaltyPoints(),
                movieGoerDbo.getIsLoyaltyUser(),
                movieGoerDbo.getAskedForLoyalty());

        movieGoer.setUserId(movieGoerDbo.getUserId());

        return movieGoer;
    }
}
