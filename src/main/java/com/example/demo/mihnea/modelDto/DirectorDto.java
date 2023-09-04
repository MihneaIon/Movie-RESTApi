package com.example.demo.mihnea.modelDto;

import java.util.List;

public class DirectorDto {



    private Long id;

    private String name;

    private List<MovieDto> movieDto;

    public DirectorDto() {
    }
    public DirectorDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public DirectorDto(Long id, String name, List<MovieDto> movie) {
        this.id = id;
        this.name = name;
        this.movieDto = movie;
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

    public List<MovieDto> getMovieDto() {
        return movieDto;
    }

    public void setMovieDto(List<MovieDto> movieDto) {
        this.movieDto = movieDto;
    }
}
