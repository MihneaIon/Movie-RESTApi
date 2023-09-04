package com.example.demo.mihnea.modelDto;

import com.example.demo.mihnea.model.GenreEnum;
import com.example.demo.mihnea.model.Movie;

import java.util.List;

public class GenreDto {

    private Long id;

    private String name;

    private List<MovieDto> movie;

    public GenreDto(Long id, String name, List<MovieDto> movieDto) {
        this.id = id;
        this.name = name;
        this.movie = movieDto;
    }

    public GenreDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieDto> getMovie() {
        return movie;
    }

    public void setMovie(List<MovieDto> movie) {
        this.movie = movie;
    }
}
