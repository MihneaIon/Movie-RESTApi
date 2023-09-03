package com.example.demo.mihnea.modelDto;

import com.example.demo.mihnea.model.Movie;

import java.util.List;
import java.util.Set;

public class ActorDto {

    private Long id;
    private String name;
    private Set<MovieDto> movie;

    public ActorDto(Long id, String name, Set<MovieDto> movie) {
        this.id = id;
        this.name = name;
        this.movie = movie;
    }

    public ActorDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ActorDto() {
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

    public Set<MovieDto> getMovie() {
        return movie;
    }

    public void setMovie(Set<MovieDto> movie) {
        this.movie = movie;
    }
}
