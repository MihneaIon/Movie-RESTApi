package com.example.demo.mihnea.service.impl;

import com.example.demo.mihnea.model.Genre;
import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.modelDto.GenreDto;
import com.example.demo.mihnea.modelDto.MovieDto;
import com.example.demo.mihnea.repository.GenreRepository;
import com.example.demo.mihnea.service.GenreService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class GenreServiceImpl implements GenreService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GenreRepository genreRepository;

    private static final int batchSize = 10;

    @Override
    public GenreDto create(GenreDto genreDto) {
        Genre genre;
        if (genreDto.getId() != null) {
            genre = genreRepository.findById(genreDto.getId()).get();
        } else {
            genre = new Genre();
        }
        if (genre != null) {
            genre = convertDtoToEntity(genreDto);
            entityManager.persist(genre);
            entityManager.flush();
            return convertEntityToDto(genre);
        } else {
            return null;
        }
    }

    @Override
    public GenreDto findById(Long id) {
        Genre genre = entityManager.find(Genre.class, id);
        GenreDto genreDto = convertEntityToDto(genre);
        return genreDto;
    }

    @Override
    public List<Genre> findAllGenre() {
        String jpql = "SELECT g FROM Genre g";
        TypedQuery<Genre> query = entityManager.createQuery(jpql, Genre.class);
        return query.getResultList();
    }

    @Override
    public void deleteGenre(Long id) {
        Genre genre = genreRepository.findById(id).get();
        genre.getMovies().forEach(movie -> movie.getGenres().remove(genre));
        entityManager.remove(genre);
    }

    @Override
    public GenreDto updateGenre(GenreDto genreDto) {
        Genre genre = genreRepository.findById(genreDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Genre not found!"));
        return convertEntityToDto(genre);
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

    private GenreDto convertEntityToDto(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        if (genre.getMovies() != null) {
            List<MovieDto> movieDtos = genre.getMovies().stream()
                    .map(movie -> new MovieDto(movie.getId(), movie.getTitle()))
                    .collect(Collectors.toList());
            genreDto.setMovie(movieDtos);
        }
        return genreDto;
    }

    private Genre convertDtoToEntity(GenreDto genreDto) {
        Genre genre = new Genre();
        if (genreDto.getId() != null) {
            genre.setId(genreDto.getId());
        }
        genre.setName(genreDto.getName());
        if (genreDto.getMovie() != null) {
            List<Movie> movies = genreDto.getMovie().stream()
                    .map(movieDto -> new Movie(movieDto.getId(), movieDto.getTitle()))
                    .collect(Collectors.toList());
            genre.setMovies(movies);
        }
        return genre;
    }
}
