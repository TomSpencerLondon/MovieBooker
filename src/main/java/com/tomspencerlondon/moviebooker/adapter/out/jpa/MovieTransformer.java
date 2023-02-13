package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import org.springframework.stereotype.Service;

@Service("jpaQuestionTransformer")
public class MovieTransformer {

    public Movie toMovie(MovieDbo movieDbo) {
        return new Movie(movieDbo.getMovieName());
    }
}
