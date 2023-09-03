package com.example.demo.mihnea.modelDto;

import com.example.demo.mihnea.model.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewDto {

    private Long id;

    @JsonProperty("comment")
    private String comment;

    private Movie movie;

    public ReviewDto() {
    }
    public ReviewDto(Long id, String comment) {
        this.id = id;
        this.comment = comment;
    }
    public ReviewDto(Long id, String comment, Movie movie) {
        this.id = id;
        this.comment = comment;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
