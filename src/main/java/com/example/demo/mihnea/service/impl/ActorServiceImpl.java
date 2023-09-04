package com.example.demo.mihnea.service.impl;

import com.example.demo.mihnea.model.Actor;
import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.modelDto.ActorDto;
import com.example.demo.mihnea.modelDto.MovieDto;
import com.example.demo.mihnea.repository.ActorRepository;
import com.example.demo.mihnea.service.ActorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class ActorServiceImpl implements ActorService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public Actor create(ActorDto actorDto) {
        Actor actor;
        if (actorDto.getId() != null) {
            actor = entityManager.find(Actor.class, actorDto.getId());
        } else {
            actor = new Actor();
        }
        actor = convertDtoToEntity(actorDto);
        entityManager.persist(actor);
        entityManager.flush();
        return actor;
    }

    @Override
    public ActorDto findById(Long id) {
        Actor actor = entityManager.find(Actor.class, id);
        ActorDto actorDto = convertEntityToDto(actor);
        return actorDto;
    }

    @Override
    public Set<ActorDto> findAllActor() {
        String jpql = "SELECT a FROM Actor a";
        TypedQuery<Actor> query = entityManager.createQuery(jpql, Actor.class);
        List<Actor> actors = query.getResultList();
        Set<ActorDto> actorDtos = actors.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toSet());
        return actorDtos;
    }

    @Override
    public Boolean deleteActor(Long id) {
        try {
            Actor actor = entityManager.find(Actor.class, id);
            //remove the references to the movie when a actor is removed
            actor.getMovies().forEach(movie -> movie.getActors().remove(actor));
            entityManager.remove(actor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ActorDto> getActorWithMoreThanXMovies(int numberOfMovies) {
        TypedQuery<Actor> query = entityManager.createQuery("SELECT a FROM Actor a WHERE SIZE(a.movies) > :x", Actor.class);
        query.setParameter("x", numberOfMovies);
        List<Actor> actors = query.getResultList();
        return actors.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Actor updateActor(ActorDto actorDto) {

        Actor existingActor = actorRepository.findById(actorDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Actor not found"));
        existingActor.setName(actorDto.getName());
        return actorRepository.save(existingActor);

    }

    @Override
    public List<ActorDto> findActorsByStudioAndDirector(String studioName, String directorName) {
        List<Actor> actors = actorRepository.findActorsByStudioAndDirector(studioName, directorName);
        List<ActorDto> actorDtos = actors.stream()
                .map(this::convertEntityToDto)
                .toList();
        return actorDtos;
    }

    private ActorDto convertEntityToDto(Actor actor) {
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actor.getId());
        actorDto.setName(actor.getName());
        if (actor.getMovies() != null) {
            Set<MovieDto> movieDtos = actor.getMovies().stream()
                    .map(movie -> new MovieDto(movie.getId(), movie.getTitle()))
                    .collect(Collectors.toSet());
            actorDto.setMovie(movieDtos);
        }
        return actorDto;
    }

    private Actor convertDtoToEntity(ActorDto actorDto) {
        Actor actor = new Actor();
        if (actorDto.getId() != null) {
            actor.setId(actorDto.getId());
        }
        actor.setName(actorDto.getName());
        if (actorDto.getMovie() != null) {
            List<Movie> movies = actorDto.getMovie().stream()
                    .map(movieDto -> new Movie(movieDto.getId(), movieDto.getTitle()))
                    .collect(Collectors.toList());
            actor.setMovies(movies);
        }
        return actor;
    }
}
