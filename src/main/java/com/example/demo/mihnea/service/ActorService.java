package com.example.demo.mihnea.service;



import com.example.demo.mihnea.model.Actor;
import com.example.demo.mihnea.modelDto.ActorDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ActorService {

    Actor create(ActorDto actorDto);

    ActorDto findById(Long id);

    Set<ActorDto> findAllActor();

    Boolean deleteActor(Long id);

    List<ActorDto> getActorWithMoreThanXMovies(int numberOfMovies);

    Actor updateActor(ActorDto actorDto);

    List<ActorDto> findActorsByStudioAndDirector(String studioName, String directorName);
}
