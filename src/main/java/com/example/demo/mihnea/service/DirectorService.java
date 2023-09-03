package com.example.demo.mihnea.service;

import com.example.demo.mihnea.model.Director;
import com.example.demo.mihnea.modelDto.DirectorDto;

import java.util.List;

public interface DirectorService {

    Director create(DirectorDto directorDto);

    Director findById(Integer id);

    List<Director> findAllDirector();

    void deleteDirector(Integer id);

    List<Director> getDirectorWithMoreThanXMovies(int numberOfMovies);

    Director updateDirector(DirectorDto directorDto);
}
