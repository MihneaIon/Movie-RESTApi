package com.example.demo.mihnea.resource;

import com.example.demo.mihnea.model.Studio;
import com.example.demo.mihnea.modelDto.StudioDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StudioResource {

    public ResponseEntity<Studio> createStudio(StudioDto studioDto);
//
    public ResponseEntity<StudioDto> findStudioById(@PathVariable Integer id);

    public ResponseEntity<List<StudioDto>> findAllStudies();

    public ResponseEntity<Studio> updateStudio(StudioDto studioDto);

    public ResponseEntity<Void> deleteStudio(@PathVariable Integer id);

    public List<StudioDto> getStudioWithMoreThenOneMovie();

    public ResponseEntity<StudioDto> getStudioWithEagerLoadedMovies(Integer id);

}
