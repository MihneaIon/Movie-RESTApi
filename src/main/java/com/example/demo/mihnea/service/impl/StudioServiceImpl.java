package com.example.demo.mihnea.service.impl;


import com.example.demo.mihnea.model.Address;
import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.model.Studio;
import com.example.demo.mihnea.modelDto.MovieDto;
import com.example.demo.mihnea.modelDto.StudioDto;
import com.example.demo.mihnea.repository.StudioRepository;
import com.example.demo.mihnea.service.StudioService;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class StudioServiceImpl implements StudioService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private StudioRepository studioRepository;

    @Override
    public Studio create(StudioDto studioDto) {
        Studio studio;
        if (studioDto.getId() != null) {
            studio = entityManager.find(Studio.class, studioDto.getId());
        } else {
            studio = new Studio();
        }
        studio = convertDtoToEntity(studioDto);

        entityManager.persist(studio);
        entityManager.flush();
        return studio;
    }

    @Override
    public StudioDto findById(Integer id) {
        Studio studio = entityManager.find(Studio.class, id);
        if (studio != null) {
            StudioDto studioDto = convertEntityToDto(studio);
            return studioDto;
        } else {
            return null;
        }

    }

    @Override
    public List<StudioDto> findAllStudios() {
        List<Studio> studioList = studioRepository.findAll();
        return studioList.stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public void deleteStudio(Integer id) {
        Studio studio = entityManager.find(Studio.class, id);
        studio.getProducedMovies().forEach(movie -> movie.setStudio(null));
        entityManager.remove(studio);
    }

    @Override
    public List<StudioDto> getStudiosWithMoreThanXMovies(int numberOfMovies) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Studio> cq = cb.createQuery(Studio.class);

        Root<Studio> studio = cq.from(Studio.class);
        Join<Studio, Movie> movieJoin = studio.join("producedMovies", JoinType.INNER);

        cq.groupBy(studio);
        cq.having(cb.gt(cb.count(movieJoin), 1));

        TypedQuery<Studio> query = entityManager.createQuery(cq);
        System.out.println("!!!" + query.getResultList());
        List<Studio> foundStudio = query.getResultList();
        Set<StudioDto> studioDtos = foundStudio.stream()
                .map(studio1 -> new StudioDto(studio1.getId(), studio1.getName(), transformMoviesDtoToMovie(studio1.getProducedMovies())))
                .collect(Collectors.toSet());
        return new ArrayList<>(studioDtos);
    }

    public Set<MovieDto> transformMoviesDtoToMovie(Set<Movie> movieList) {
        Set<MovieDto> movies = movieList.stream()
                .map(movieDto -> new MovieDto(movieDto.getId(), movieDto.getTitle()))
                .collect(Collectors.toSet());
        return movies;
    }

    @Override
    public Studio updateStudio(StudioDto studioDto) {
        Studio existingStudio = studioRepository.findById(studioDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Studio was not found!"));
        existingStudio.setName(studioDto.getName());
        return existingStudio;
    }

    // Lazy vs. Eager Loading
    public Studio getStudioWithMovies(Long id) {
        return entityManager.find(Studio.class, id);
    }

    public StudioDto getStudioWithEagerLoadedMovies(Integer id) {
        if (id != null) {
            EntityGraph<Studio> graph = entityManager.createEntityGraph(Studio.class);
            graph.addAttributeNodes("producedMovies");

            Map<String, Object> hints = new HashMap<>();
            hints.put("javax.persistence.loadgraph", graph);

            Studio studio = entityManager.find(Studio.class, id, hints); // Eager loading of movies
            StudioDto studioDto = convertEntityToDto(studio);
            return studioDto;
        } else {
            return null;
        }
    }

    private StudioDto convertEntityToDto(Studio studio) {
        StudioDto studioDto = new StudioDto();
        studioDto.setId(studio.getId());
        studioDto.setName(studio.getName());
        if (studio.getProducedMovies() != null) {
            Set<MovieDto> movieDtos = studio.getProducedMovies().stream()
                    .map(movie -> new MovieDto(movie.getId(), movie.getTitle()))
                    .collect(Collectors.toSet());
            studioDto.setMovieDto(movieDtos);
        }
        return studioDto;
    }

    private Studio convertDtoToEntity(StudioDto studioDto) {
        Studio studio = new Studio();
        if (studioDto.getId() != null) {
            studio.setId(studioDto.getId());
        }
        studio.setName(studioDto.getName());
        if (studioDto.getMovieDto() != null) {
            Set<Movie> movies = studioDto.getMovieDto().stream()
                    .map(movieDto -> new Movie(movieDto.getId(), movieDto.getTitle()))
                    .collect(Collectors.toSet());
            studio.setProducedMovies(movies);
        }
        Address address = new Address();
        address.setCity(studioDto.getAddress().getCity());
        address.setStreet(studioDto.getAddress().getStreet());
        address.setZipCode(studioDto.getAddress().getZipCode());
        studio.setAddress(address);
        return studio;
    }
}
