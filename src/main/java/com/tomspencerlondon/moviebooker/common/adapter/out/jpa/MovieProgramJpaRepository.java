package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieProgramJpaRepository extends JpaRepository<MovieProgramDbo, Long> {
    @Query(value="SELECT mp FROM MovieProgramDbo mp WHERE mp.movie.movieId = :movieId")
    List<MovieProgramDbo> findMovieProgramDbosBy(Long movieId);
}
