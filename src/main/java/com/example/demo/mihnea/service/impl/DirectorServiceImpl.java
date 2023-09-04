package com.example.demo.mihnea.service.impl;

import com.example.demo.mihnea.model.Director;
import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.modelDto.DirectorDto;
import com.example.demo.mihnea.modelDto.MovieDto;
import com.example.demo.mihnea.repository.DirectorRepsoitory;
import com.example.demo.mihnea.service.DirectorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class DirectorServiceImpl implements DirectorService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private DirectorRepsoitory directorRepsoitory;

    @Override
    public Director create(DirectorDto directorDto) {
        Director director;
        if (directorDto.getId() != null) {
            director = findById(directorDto.getId().intValue());
        } else {
            director = new Director();
        }
        director = convertDtoToEntity(directorDto);
        entityManager.persist(director);
        entityManager.flush();
        return director;
    }

    @Override
    public Director findById(Integer id) {
        return entityManager.find(Director.class, id);
    }

    @Override
    public List<DirectorDto> findAllDirector() {
        List<Director> directors = directorRepsoitory.findAll();
        List<DirectorDto> directorDtos = directors.stream()
                .map(this::convertEntityToDto)
                .toList();
        return directorDtos;
    }

    @Override
    public void deleteDirector(Integer id) {
        Director director = this.findById(id);
        director.getDirectedMovies().forEach(movie -> movie.setDirector(null));
        entityManager.remove(director);
    }

    @Override
    public List<DirectorDto> getDirectorWithMoreThanXMovies(int movieCount) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> query = criteriaBuilder.createQuery(Director.class);

        Root<Director> directorRoot = query.from(Director.class);

        // Join with movies
        Join<Director, Movie> moviesJoin = directorRoot.join("directedMovies", JoinType.INNER);

        // Group by directors
        query.groupBy(directorRoot.get("id"));

        // clause to filter directors by movie count
        query.having(criteriaBuilder.gt(criteriaBuilder.count(moviesJoin), movieCount));

        TypedQuery<Director> typedQuery = entityManager.createQuery(query);

        List<Director> movies = typedQuery.getResultList();

        List<DirectorDto> directorDtos = movies.stream()
                .map(this::convertEntityToDto).toList();
        return directorDtos;
    }

    @Override
    public Director updateDirector(DirectorDto directorDto) {
        Director director = directorRepsoitory.findById(directorDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Director not found!"));
        return director;
    }

    private DirectorDto convertEntityToDto(Director director) {
        DirectorDto directorDto = new DirectorDto();
        directorDto.setId(director.getId());
        directorDto.setName(director.getName());
        if (director.getDirectedMovies() != null) {
            List<MovieDto> movieDtos = director.getDirectedMovies().stream()
                    .map(movie -> new MovieDto(movie.getId(), movie.getTitle()))
                    .collect(Collectors.toList());
            directorDto.setMovieDto(movieDtos);
        }
        return directorDto;
    }

    private Director convertDtoToEntity(DirectorDto directorDto) {
        Director director = new Director();
        if (directorDto.getId() != null) {
            director.setId(directorDto.getId());
        }
        director.setName(directorDto.getName());
        if (directorDto.getMovieDto() != null) {
            List<Movie> movies = directorDto.getMovieDto().stream()
                    .map(movieDto -> new Movie(movieDto.getId(), movieDto.getTitle()))
                    .collect(Collectors.toList());
            director.setDirectedMovies(movies);
        }
        return director;
    }
}
