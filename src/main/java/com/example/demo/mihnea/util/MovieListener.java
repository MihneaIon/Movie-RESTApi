package com.example.demo.mihnea.util;

import com.example.demo.mihnea.model.Movie;
import jakarta.inject.Inject;
import jakarta.persistence.PrePersist;


public class MovieListener {


    @PrePersist
    public void beforePersist(Movie movie){
        System.out.println("Start persisiting data");
    }
}
