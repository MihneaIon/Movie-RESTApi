package com.example.demo.mihnea.service.impl;

import com.example.demo.mihnea.model.Genre;
import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.modelDto.GenreDto;
import com.example.demo.mihnea.service.GenreService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class GenreServiceImpl implements GenreService {

//    @PersistenceContext
//    EntityManager entityManager;

    @Autowired
    private EntityManager entityManager;

    private static final int batchSize = 10;

    @Override
    public Genre create(GenreDto genreDto) {
        Genre genre;
        if(genreDto.getId() != null){
            genre = findById(genreDto.getId());
        } else {
            genre = new Genre();
        }
        genre.setName(genreDto.getName());
        if(genreDto.getMovie() != null && !genreDto.getMovie().isEmpty()){
            List<Movie> movies = new ArrayList<>();
            movies.addAll(genreDto.getMovie());
            genreDto.getMovie().addAll(movies);
        }
        entityManager.persist(genre);
        return genre;
    }

    @Override
    public Genre findById(Long id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    public List<Genre> findAllGenre() {
        String jpql = "SELECT g FROM Genre g";
        TypedQuery<Genre> query = entityManager.createQuery(jpql, Genre.class);
        return query.getResultList();
    }

    @Override
    public void deleteGenre(Long id) {
        Genre genre = this.findById(id);
        entityManager.remove(genre);
    }

    @Override
    public Genre updateGenre(GenreDto genreDto) {
        Genre genre = this.findById(genreDto.getId());
        if(genreDto.getId() != null){
            genre.setName(genreDto.getName());
            if(genreDto.getMovie() != null){
                genre.getMovies().addAll(genreDto.getMovie());
            }
            entityManager.merge(genre);
        }
        return genre;
    }

    @Override
    @Transactional
    public void batchInsertGenre(List<Genre> genres) {
        for (int i = 0; i < genres.size(); i++) {
            entityManager.persist(genres.get(i));
            if (i % batchSize == 0 && i > 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}
