package com.example.demo.mihnea.service.impl;

import com.example.demo.mihnea.model.Director;
import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.modelDto.DirectorDto;
import com.example.demo.mihnea.service.DirectorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DirectorServiceImpl implements DirectorService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Director create(DirectorDto directorDto) {
        Director director;
        if(directorDto.getId() != null){
            director = findById(directorDto.getId().intValue());
        } else {
            director = new Director();
        }
        director.setName(directorDto.getName());
        if(directorDto.getMovie() != null && !directorDto.getMovie().isEmpty()){
            List<Movie> movies = new ArrayList<>();
            movies.addAll(directorDto.getMovie());
            directorDto.getMovie().addAll(movies);
        }
        entityManager.persist(director);
        return director;
    }

    @Override
    public Director findById(Integer id) {
        return entityManager.find(Director.class, id);
    }

    @Override
    public List<Director> findAllDirector() {
        return null;
    }

    @Override
    public void deleteDirector(Integer id) {
        Director director = this.findById(id);
        entityManager.remove(director);
    }

    @Override
    public List<Director> getDirectorWithMoreThanXMovies(int numberOfMovies) {
        return null;
    }

    @Override
    public Director updateDirector(DirectorDto directorDto) {
        Director director = this.findById(directorDto.getId().intValue());
        if(directorDto.getId() != null){
            director.setName(directorDto.getName());
            if(directorDto.getMovie() != null){
                director.getDirectedMovies().addAll(directorDto.getMovie());
            }
            entityManager.merge(director);
        }
        return director;
    }
}
