package com.example.demo.mihnea.resource.impl;

import com.example.demo.mihnea.model.Genre;
import com.example.demo.mihnea.modelDto.GenreDto;
import com.example.demo.mihnea.resource.GenreResource;
import com.example.demo.mihnea.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GenreResourceImpl.PATH)
public class GenreResourceImpl implements GenreResource {

    public static final String PATH = "/genre";

    @Inject
    private GenreService genreService;
    @Override
    @PostMapping("/new-genre")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a genre.", responses = {
            @ApiResponse(responseCode = "201", description = "The genre was created.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreDto genreDto) {
        final GenreDto savedGenre = genreService.create(genreDto);
        return ResponseEntity.ok(savedGenre);
    }

    @Override
    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<GenreDto> findGenreById(Long id) {
        final GenreDto foundGenreDto = genreService.findById(id);
        return ResponseEntity.ok(foundGenreDto);
    }

    @Override
    public ResponseEntity<List<Genre>> findAllGenre() {
        return null;
    }

    @Override
    @PatchMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<GenreDto> updateGenre(@RequestBody GenreDto genreDto) {
        final GenreDto savedGenre = genreService.updateGenre(genreDto);
        return ResponseEntity.ok(savedGenre);
    }

    @Override
    @DeleteMapping("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "405", description = "Method not allowed."),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Void> deleteGenre(@RequestParam("id") Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/batch-insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "201", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "405", description = "Method not allowed."),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<List<Genre>> createGenreUsingBatchProcessing(@RequestBody List<Genre> movies) {
        genreService.batchInsertGenre(movies);
        return ResponseEntity.status(201).build();
    }
}
