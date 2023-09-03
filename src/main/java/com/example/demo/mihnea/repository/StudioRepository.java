package com.example.demo.mihnea.repository;

import com.example.demo.mihnea.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Integer> {

        Studio findByName(String name);

//    @Query("SELECT s FROM Studio s JOIN s.movies m GROUP BY s HAVING COUNT(m) > 1")
//    List<Studio> findStudiosWithMultipleMovies();


}
