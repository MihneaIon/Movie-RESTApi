package com.example.demo.mihnea.modelDto;

import com.example.demo.mihnea.model.Movie;

import java.util.List;

public class DirectorDto {

    public DirectorDto() {
    }
    public DirectorDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public DirectorDto(Long id, String name, List<Movie> movie) {
        this.id = id;
        this.name = name;
        this.movie = movie;
    }

    private Long id;

    private String name;

    private List<Movie> movie;

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

    public List<Movie> getMovie() {
        return movie;
    }

    public void setMovie(List<Movie> movie) {
        this.movie = movie;
    }
}
