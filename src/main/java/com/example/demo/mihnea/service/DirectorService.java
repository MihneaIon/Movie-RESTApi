package com.example.demo.mihnea.service;

import com.example.demo.mihnea.model.Director;
import com.example.demo.mihnea.modelDto.DirectorDto;

import java.util.List;

public interface DirectorService {

    Director create(DirectorDto directorDto);

    Director findById(Integer id);

    List<DirectorDto> findAllDirector();

    void deleteDirector(Integer id);

    List<DirectorDto> getDirectorWithMoreThanXMovies(int movieCount);

    Director updateDirector(DirectorDto directorDto);
}
