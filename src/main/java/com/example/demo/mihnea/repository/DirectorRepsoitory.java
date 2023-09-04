package com.example.demo.mihnea.repository;

import com.example.demo.mihnea.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepsoitory extends JpaRepository<Director, Long> {

    Director findByName(String name);

//    @Query("SELECT d FROM Director d JOIN FETCH d.movies WHERE SIZE(d.movies) > :movieCount")
//    List<Director> findDirectorsWithMoreMoviesThan(@Param("movieCount") int movieCount);
}
