package com.example.demo.mihnea.modelDto;

import java.util.Set;

public class StudioDto {

    private Integer id;
    private String name;
    private Set<MovieDto> movieDto;

    private AddressDto addressDto;

    public StudioDto() {
    }

    public StudioDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public StudioDto(Integer id, String name, Set<MovieDto> movie) {
        this.id = id;
        this.name = name;
        this.movieDto = movie;
    }

    public StudioDto(Integer id, String name, Set<MovieDto> movieDto, AddressDto addressDto) {
        this.id = id;
        this.name = name;
        this.movieDto = movieDto;
        this.addressDto = addressDto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MovieDto> getMovieDto() {
        return movieDto;
    }

    public void setMovieDto(Set<MovieDto> movieDto) {
        this.movieDto = movieDto;
    }

    public AddressDto getAddress() {
        return addressDto;
    }

    public void setAddress(AddressDto address) {
        this.addressDto = address;
    }
}
