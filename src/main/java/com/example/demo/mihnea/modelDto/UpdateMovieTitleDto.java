package com.example.demo.mihnea.modelDto;

import com.example.demo.mihnea.model.MovieStatus;

public class UpdateMovieTitleDto {
    private Long id;
    private String newTitle;

    private MovieStatus movieStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public MovieStatus getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(MovieStatus movieStatus) {
        this.movieStatus = movieStatus;
    }
}
