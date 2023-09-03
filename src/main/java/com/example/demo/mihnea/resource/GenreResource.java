package com.example.demo.mihnea.resource;


import com.example.demo.mihnea.model.Genre;
import com.example.demo.mihnea.modelDto.GenreDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface GenreResource {

    public ResponseEntity<Genre> createGenre(GenreDto genreDto);
    //
    public ResponseEntity<Genre> findGenreById(@PathVariable Long id);

    public ResponseEntity<List<Genre>> findAllGenre();

    public ResponseEntity<Genre> updateGenre(GenreDto genreDto);

    public ResponseEntity<Void> deleteGenre(@PathVariable Long id);

    public ResponseEntity<List<Genre>> createGenreUsingBatchProcessing(List<Genre> genres);
}
