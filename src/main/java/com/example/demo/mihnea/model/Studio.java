package com.example.demo.mihnea.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Studio extends BaseClass{

    public static final String BY_ID = "Studio.BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Movie> producedMovies = new HashSet<>();

    @Embedded
    private Address address;

    public Studio(Integer id, String name, Set<Movie> producedMovies, Address address) {
        this.id = id;
        this.name = name;
        this.producedMovies = producedMovies;
        this.address = address;
    }

    public Studio(String name) {
        this.name = name;
    }

    public Studio(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Studio(){
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

    public Set<Movie> getProducedMovies() {
        return producedMovies;
    }

    public void setProducedMovies(Set<Movie> producedMovies) {
        this.producedMovies = producedMovies;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
