package com.example.demo.mihnea.service;



import com.example.demo.mihnea.model.Actor;
import com.example.demo.mihnea.modelDto.ActorDto;

import java.util.List;
import java.util.Set;

public interface ActorService {

    Actor create(ActorDto actorDto);

    ActorDto findById(Long id);

    Set<ActorDto> findAllActor();

    void deleteActor(Long id);

    List<Actor> getActorWithMoreThanXMovies(int numberOfMovies);

    Actor updateActor(ActorDto actorDto);
}
