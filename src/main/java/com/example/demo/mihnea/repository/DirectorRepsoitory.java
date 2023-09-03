package com.example.demo.mihnea.repository;

import com.example.demo.mihnea.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepsoitory extends JpaRepository<Director, Long> {

    Director findByName(String name);
}
