package com.example.demo.mihnea.repository;

import com.example.demo.mihnea.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    Actor findByName(String name);
}
