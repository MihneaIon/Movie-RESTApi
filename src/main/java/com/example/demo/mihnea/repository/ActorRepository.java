package com.example.demo.mihnea.repository;

import com.example.demo.mihnea.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    Actor findByName(String name);

    @Query("SELECT DISTINCT a FROM Actor a " +
            "JOIN a.movies m " +
            "JOIN m.studio s " +
            "JOIN m.director d " +
            "WHERE s.name = :studioName " +
            "AND d.name = :directorName")
    List<Actor> findActorsByStudioAndDirector(@Param("studioName") String studioName, @Param("directorName") String directorName);
}
