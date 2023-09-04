package com.example.demo.mihnea.modelDto;

import com.example.demo.mihnea.model.MovieStatus;

import java.util.Set;

public class MovieDto {

    private Long id;

    private String title;

    private MovieStatus movieStatus;

    private DirectorDto directorDto;

    private Set<GenreDto> genreDos;

    private StudioDto studioDto;

    private Set<ActorDto> actorDto;

    private Set<ReviewDto> reviewDtos;

    public MovieDto() {
    }
    public MovieDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public MovieDto(Long id, String title, MovieStatus movieStatus, DirectorDto directorDto, Set<GenreDto> genreDos, StudioDto studioDto, Set<ActorDto> actorDto, Set<ReviewDto> reviewDtos) {
        this.id = id;
        this.title = title;
        this.movieStatus = movieStatus;
        this.directorDto = directorDto;
        this.genreDos = genreDos;
        this.studioDto = studioDto;
        this.actorDto = actorDto;
        this.reviewDtos = reviewDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public MovieStatus getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(MovieStatus movieStatus) {
        this.movieStatus = movieStatus;
    }

    public DirectorDto getDirectorDto() {
        return directorDto;
    }

    public void setDirectorDto(DirectorDto directorDto) {
        this.directorDto = directorDto;
    }

    public Set<GenreDto> getGenreDos() {
        return genreDos;
    }

    public void setGenreDos(Set<GenreDto> genreDos) {
        this.genreDos = genreDos;
    }

    public StudioDto getStudioDto() {
        return studioDto;
    }

    public void setStudioDto(StudioDto studioDto) {
        this.studioDto = studioDto;
    }

    public Set<ReviewDto> getReviewDtos() {
        return reviewDtos;
    }

    public void setReviewDtos(Set<ReviewDto> reviewDtos) {
        this.reviewDtos = reviewDtos;
    }

    public Set<ActorDto> getActorDto() {
        return actorDto;
    }

    public void setActorDto(Set<ActorDto> actorDto) {
        this.actorDto = actorDto;
    }
}
