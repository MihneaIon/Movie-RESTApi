package com.example.demo.mihnea.modelDto;

public class UpdateMovieTitleDto {
    private Long id;
    private String newTitle;

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
}
