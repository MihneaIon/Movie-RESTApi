package com.example.demo.mihnea.util;

import com.example.demo.mihnea.model.Movie;
import jakarta.inject.Inject;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.logging.Logger;


public class MovieListener {

    private static final Logger logger = Logger.getLogger(MovieListener.class.getName());

    @PrePersist
    public void beforePersist(Movie movie){
        logger.info("Start persisiting data");
    }

    @PreUpdate
    public void beforeUpdate(Movie movie) { logger.info("The update will be executed");}

    @PostPersist
    public void postPersist(Movie movie){
        logger.info("The data was persisited!");
    }

    @PostUpdate
    public void postrUpdate(Movie movie) { logger.info("The data was updated!");}

}
