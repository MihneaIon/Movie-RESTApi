package com.example.demo.mihnea.service;


import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.model.MovieStatus;
import com.example.demo.mihnea.modelDto.MovieDto;

import java.util.List;

public interface MovieService {

    MovieDto create(MovieDto movieDto);

    MovieDto findById(Long id);

    List<MovieDto> findAllMovie();

    void deleteMovie(Long id);


    MovieDto updateMovie(MovieDto movieDto);

    List<MovieDto> findByGenresName(String name);

    List<MovieDto> findByTitleContainingIgnoreCase(String name);

    public void updateMovieTitle(Long id, String newTitle, MovieStatus movieStatus);

    public List<MovieDto> searchMovies(String keyword);

    public List<MovieDto> searchMoviesNative(String keyword);

    public MovieDto findMovieWithActors(Long movieId);

    public List<MovieDto> findMoviesWithSpecificReview(String name);

    public List<MovieDto> findMoviesWithSpecificGenre(String name);

    public MovieDto updateOrAddMovie(MovieDto movieDto);

}
