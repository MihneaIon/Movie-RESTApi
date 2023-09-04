package com.example.demo.mihnea.service;

import com.example.demo.mihnea.model.Studio;
import com.example.demo.mihnea.modelDto.StudioDto;

import java.util.List;

public interface StudioService {

    Studio create(StudioDto studioDto);

    StudioDto findById(Integer id);

    List<StudioDto> findAllStudios();

    void deleteStudio(Integer id);

    List<StudioDto> getStudiosWithMoreThanXMovies(int numberOfMovies);

    Studio updateStudio(StudioDto studioDto);

    StudioDto getStudioWithEagerLoadedMovies(Integer id);
}
