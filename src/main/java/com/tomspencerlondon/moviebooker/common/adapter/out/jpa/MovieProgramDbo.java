package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieDbo;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_programs")
public class MovieProgramDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private BigDecimal price;

    public void setScheduleId(Long id) {
        this.scheduleId = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieDbo movie;

    public MovieDbo getMovie() {
        return movie;
    }

    public void setMovie(MovieDbo movie) {
        this.movie = movie;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
