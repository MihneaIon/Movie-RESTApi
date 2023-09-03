package com.example.demo.mihnea.service;

import com.example.demo.mihnea.model.Genre;
import com.example.demo.mihnea.modelDto.GenreDto;

import java.util.List;

public interface GenreService {

    Genre create(GenreDto genreDto);

    Genre findById(Long id);

    List<Genre> findAllGenre();

    void deleteGenre(Long id);

    Genre updateGenre(GenreDto genreDto);

    public void batchInsertGenre(List<Genre> genres);

}
