package com.example.demo.mihnea.resource.impl;

import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.modelDto.MovieDto;
import com.example.demo.mihnea.modelDto.UpdateMovieTitleDto;
import com.example.demo.mihnea.resource.MovieResource;
import com.example.demo.mihnea.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(MovieResourceImpl.PATH)
public class MovieResourceImpl implements MovieResource {

    public static final String PATH = "/movie";

    @Inject
    private MovieService movieService;

    @Override
    @PostMapping("/new-movie")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a studio.", responses = {
            @ApiResponse(responseCode = "201", description = "The studio was created.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Movie> createMovie(@RequestBody MovieDto movieDto) {
        final Movie saveMovie = movieService.create(movieDto);
        return ResponseEntity.ok(saveMovie);
    }

    @Override
    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<MovieDto> findMovieById(Long id) {
        final MovieDto movieDto = movieService.findById(id);
        if(movieDto != null){
            return ResponseEntity.ok(movieDto);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    @GetMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studios.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<List<MovieDto>> findAllMovie() {
        final List<MovieDto> movieList = movieService.findAllMovie();
        return ResponseEntity.ok(movieList);
    }

    @Override
    @PatchMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Movie> updateMovie(@RequestBody MovieDto movieDto) {
        final Movie saveMovie = movieService.updateMovie(movieDto);
        return ResponseEntity.ok(saveMovie);
    }

    @Override
    @DeleteMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "405", description = "Method not allowed."),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Void> deleteMovie(@RequestParam("id") Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }

    //Advanced Queries:
    @Override
    @GetMapping("/specific-genre")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<MovieDto>> findByGenresName(@RequestParam("name") String name) {
        return ResponseEntity.ok(movieService.findByGenresName(name));
    }

    @Override
    public ResponseEntity<List<MovieDto>> findByTitleContainingIgnoreCase(@RequestParam String name) {
        return ResponseEntity.ok(movieService.findByTitleContainingIgnoreCase(name));
    }

    //Optimistic and Pessimistic Locking
    @Override
    @PatchMapping("update-title")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity updateMovieTitle(@RequestBody UpdateMovieTitleDto updateMovieTitleDto) {
        movieService.updateMovieTitle(updateMovieTitleDto.getId(),updateMovieTitleDto.getNewTitle());
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<MovieDto>> searchMovie(@RequestParam String keyword) {
        try {
            List<MovieDto> movies = movieService.searchMovies(keyword);
            System.out.println(movies);
            return ResponseEntity.ok(movies);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @GetMapping("/search-native")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<MovieDto>> searchMovieNAtive(String keyword) {
        try {
            List<MovieDto> movies = movieService.searchMoviesNative(keyword);

            return ResponseEntity.ok(movies);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @GetMapping("/movie-actors")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<MovieDto> findMovieWithActors(@RequestParam("id") Long id) {
        MovieDto movieDto = movieService.findMovieWithActors(id);
        return ResponseEntity.ok(movieDto);
    }

    @Override
    @GetMapping("/reviewed")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<MovieDto>> findMoviesWithSpecificReview(String movieName) {
        List<MovieDto> movieDtos = movieService.findMoviesWithSpecificReview(movieName);
        return ResponseEntity.ok(movieDtos);
    }

    @Override

    public ResponseEntity<List<MovieDto>> findMoviesWithSpecificGenre(String genre) {
        List<MovieDto> movieDtos = movieService.findMoviesWithSpecificGenre(genre);
        return ResponseEntity.ok(movieDtos);
    }

}
