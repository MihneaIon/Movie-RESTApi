package com.example.demo.mihnea.resource;

import com.example.demo.mihnea.model.Actor;
import com.example.demo.mihnea.modelDto.ActorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

public interface ActorResource {

    public ResponseEntity<Actor> createActor(ActorDto actorDto);

    public ResponseEntity<ActorDto> findActorById(@PathVariable Long id);

    public ResponseEntity<Set<ActorDto>> findAllActor();

    public ResponseEntity<Actor> updateActor(ActorDto actorDto);

    public ResponseEntity<Boolean> deleteActor(@PathVariable Long id);

    public ResponseEntity<List<ActorDto>> findActorsByStudioAndDirector(String studioName, String directorName);

    public ResponseEntity<List<ActorDto>> getActorWithMoreThanXMovies(int numberOfMovies);

}
