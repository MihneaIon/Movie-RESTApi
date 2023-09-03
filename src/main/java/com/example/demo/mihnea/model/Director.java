package com.example.demo.mihnea.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Director extends BaseClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "director")
    private List<Movie> directedMovies = new ArrayList<>();

    // Constructors, getters, setters

    public Director(Long id, String name, List<Movie> directedMovies) {
        this.id = id;
        this.name = name;
        this.directedMovies = directedMovies;
    }

    public Director(){

    }
    public Director(String name) {
        this.name = name;
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

    public List<Movie> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(List<Movie> directedMovies) {
        this.directedMovies = directedMovies;
    }
}