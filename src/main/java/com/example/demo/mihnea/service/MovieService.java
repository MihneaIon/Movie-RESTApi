package com.example.demo.mihnea.service;


import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.modelDto.MovieDto;

import java.util.List;

public interface MovieService {

    Movie create(MovieDto movieDto);

    MovieDto findById(Long id);

    List<MovieDto> findAllMovie();

    void deleteMovie(Long id);


    Movie updateMovie(MovieDto movieDto);

    List<MovieDto> findByGenresName(String name);

    List<MovieDto> findByTitleContainingIgnoreCase(String name);

    public void updateMovieTitle(Long id, String newTitle);

    public List<MovieDto> searchMovies(String keyword);

    public List<MovieDto> searchMoviesNative(String keyword);

    public MovieDto findMovieWithActors(Long movieId);

    public List<MovieDto> findMoviesWithSpecificReview(String name);

    public List<MovieDto> findMoviesWithSpecificGenre(String name);

}
