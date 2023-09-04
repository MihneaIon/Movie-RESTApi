package com.example.demo.mihnea.resource;

import com.example.demo.mihnea.model.Director;
import com.example.demo.mihnea.modelDto.DirectorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface DirectorResource {

    public ResponseEntity<Director> createDirector(DirectorDto directorDto);
    //
    public ResponseEntity<Director> findDirectorById(@PathVariable Long id);

    public ResponseEntity<List<DirectorDto>> findAllDirector();

    public ResponseEntity<Director> updateDirector(DirectorDto directorDto);

    public ResponseEntity<Void> deleteDirector(@PathVariable Long id);

    public ResponseEntity<List<DirectorDto>> getDirectorWithMoreThanXMovies(int movieCount);
}
