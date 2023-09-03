package com.example.demo.mihnea.resource;

import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.modelDto.MovieDto;
import com.example.demo.mihnea.modelDto.UpdateMovieTitleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MovieResource {
    public ResponseEntity<Movie> createMovie(MovieDto movieDto);
    //
    public ResponseEntity<MovieDto> findMovieById(@PathVariable Long id);

    public ResponseEntity<List<MovieDto>> findAllMovie();

    public ResponseEntity<Movie> updateMovie(MovieDto movieDto);

    public ResponseEntity<Void> deleteMovie(@PathVariable Long id);

    public ResponseEntity<List<MovieDto>> findByGenresName(@PathVariable String name);

    public ResponseEntity<List<MovieDto>> findByTitleContainingIgnoreCase(@PathVariable String name);

    public ResponseEntity updateMovieTitle(UpdateMovieTitleDto updateMovieTitleDto);

    public ResponseEntity<List<MovieDto>> searchMovie(@RequestParam String keyword);

    public ResponseEntity<List<MovieDto>> searchMovieNAtive(@RequestParam String keyword);

    public ResponseEntity<MovieDto> findMovieWithActors(Long id);

    public ResponseEntity<List<MovieDto>> findMoviesWithSpecificReview(String movieName);
    public ResponseEntity<List<MovieDto>> findMoviesWithSpecificGenre(String genre);
}
