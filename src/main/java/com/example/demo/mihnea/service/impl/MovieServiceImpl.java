package com.example.demo.mihnea.service.impl;

import com.example.demo.mihnea.mapper.MovieMapper;
import com.example.demo.mihnea.model.*;
import com.example.demo.mihnea.modelDto.*;
import com.example.demo.mihnea.repository.*;
import com.example.demo.mihnea.service.ActorService;
import com.example.demo.mihnea.service.GenreService;
import com.example.demo.mihnea.service.MovieService;
import com.example.demo.mihnea.service.StudioService;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class MovieServiceImpl implements MovieService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    private DirectorServiceImpl directorServiceImpl;

    @Inject
    private StudioService studioService;

    @Inject
    private GenreService genreService;

    @Inject
    private ActorService actorService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private DirectorRepsoitory directorRepsoitory;

//    @Autowired
//    private MovieMapper movieMapper;

    @Override
    public Movie create(MovieDto movieDto) {
        Director director = directorRepsoitory.findByName(movieDto.getDirectorDto().getName());
        if (director == null) {
            director = directorRepsoitory.save(new Director(movieDto.getDirectorDto().getName()));
        }

        List<Genre> genres = new ArrayList<>();
        for (GenreDto genreDto : movieDto.getGenreDos()) {
            Genre existingGenre = genreRepository.findByName(genreDto.getName());
            if (existingGenre == null) {
                genres.add(new Genre(genreDto.getName()));
            } else {
                genres.add(existingGenre);
            }
        }
        genres = genreRepository.saveAll(genres);

        List<Actor> actors = new ArrayList<>();
        for (ActorDto actorDto : movieDto.getActorDto()) {
            Actor existingActor = actorRepository.findByName(actorDto.getName());
            if (existingActor == null) {
                actors.add(new Actor(actorDto.getName()));
            } else {
                actors.add(existingActor);
            }
        }
        actors = actorRepository.saveAll(actors);

        Studio studio = studioRepository.findByName(movieDto.getStudioDto().getName());
        if (studio == null) {
            studio = studioRepository.save(new Studio(movieDto.getStudioDto().getName()));
        }

        List<Review> reviews = reviewRepository.saveAll(
                movieDto.getReviewDtos().stream()
                        .map(reviewDto -> new Review(reviewDto.getComment()))
                        .collect(Collectors.toList())
        );

        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDirector(director);
        movie.setGenres(genres);
        movie.setActors(actors);
        movie.setStudio(studio);
        movie.setReviews(reviews);

        return movieRepository.save(movie);
    }

    @Override
    public MovieDto findById(Long id) {
        try{
            Movie movie = movieRepository.findById(id).orElse(null);
            return convertEntityToDto(movie);
        }catch (StackOverflowError e){
            System.out.println("!!!"+e);
        }
        return null;
    }

    @Override
    public List<MovieDto> findAllMovie() {
        return null;
    }

    @Override
    public void deleteMovie(Long id) {
        MovieDto movieDto = this.findById(id);
        //entityManager.remove(movie);
    }

    @Override
    public Movie updateMovie(MovieDto movieDto) {
        return null;
    }

    // find movies withj specific genre
    @Override
    public List<MovieDto> findByGenresName(String name) {
        List<Movie> movies = movieRepository.findByGenresName(name);
        List<MovieDto> movieDtos = movies.stream().map(this::convertEntityToDto)
                .toList();
        return movieDtos;
    }

    @Override
    public List<MovieDto> findByTitleContainingIgnoreCase(String name) {
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(name);
        List<MovieDto> movieDtos = movies.stream().map(this::convertEntityToDto)
                .toList();
        return movieDtos;
    }

    private Movie convertDtoToEntity(MovieDto movieDto, Movie movie){
        if (movieDto.getDirectorDto() != null) {
            movie.setDirector(directorServiceImpl.findById(movieDto.getDirectorDto().getId().intValue()));
        }
        if (movieDto.getStudioDto() != null) {
            Studio studio = studioRepository.findById(movieDto.getStudioDto().getId()).orElseThrow(() -> new EntityNotFoundException("Studio with this id doesn't exist"));
            movie.setStudio(studio);
        }
        if (!movieDto.getGenreDos().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(movieDto.getGenreDos().stream().map(GenreDto::getId).collect(Collectors.toSet()));
            movie.setGenres(genres);
        }
        if (!movieDto.getReviewDtos().isEmpty()) {
            List<Review> reviews = reviewRepository.findAllById(movieDto.getReviewDtos().stream().map(ReviewDto::getId).collect(Collectors.toSet()));
            movie.setReviews(reviews);
        }
        if (!movieDto.getActorDto().isEmpty()) {
            List<Actor> actors = actorRepository.findAllById(movieDto.getActorDto().stream().map(ActorDto::getId).collect(Collectors.toSet()));
            movie.setActors(actors);
        }
        return movie;
    }

    public List<Movie> getMoviesByGenre(Long genreId) {
        return movieRepository.findByGenre(genreId);
    }

//Optimistic and Pessimistic Locking:
    @Override
    @Transactional
    public void updateMovieTitle(Long id, String newTitle) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null) {
            movie.setTitle(newTitle);
        }
    }

    @Override
    public List<MovieDto> searchMovies(String keyword) {
        List<Movie> movies = movieRepository.searchMovies(keyword);
        List<MovieDto> movieDtos = movies.stream().map(this::convertEntityToDto)
                .toList();
        return movieDtos;
    }

    @Override
    public List<MovieDto> searchMoviesNative(String keyword) {
        List<Movie> movies = movieRepository.searchMovies(keyword);
        List<MovieDto> movieDtos = movies.stream().map(this::convertEntityToDto)
                .toList();
        return movieDtos;
    }

    public Movie findMovieWithActorsEntityGraph(Long movieId) {
        EntityGraph<Movie> graph = entityManager.createEntityGraph(Movie.class);
        graph.addAttributeNodes("actors"); // Use the name of the Entity Graph you defined

        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);

        return entityManager.find(Movie.class, movieId, hints);
    }

    // Entity Graph:
    @Override
    public MovieDto findMovieWithActors(Long movieId) {
        Movie movie = findMovieWithActorsEntityGraph(movieId);
        MovieDto movieDto = convertEntityToDto(movie);
        return movieDto;
    }

    @Override
    public List<MovieDto> findMoviesWithSpecificReview(String name) {
        List<Movie> movieList = moviesWithSpecificReview(name);
        List<MovieDto> movieDtos = movieList.stream().map(this::convertEntityToDto)
                .toList();
        return movieDtos;
    }
    @Override
    public List<MovieDto> findMoviesWithSpecificGenre(String genre){
        List<Movie> movieList = moviesWithSpecificGenre(genre);
        List<MovieDto> movieDtos = movieList.stream().map(this::convertEntityToDto)
                .toList();
        return movieDtos;
    }


    private MovieDto  convertEntityToDto(Movie movie){
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        Set<ActorDto> actorDtos = movie.getActors().stream()
                .map(actor -> new ActorDto(actor.getId(), actor.getName()))
                .collect(Collectors.toSet());
        movieDto.setActorDto(actorDtos);
        Set<GenreDto> genreDtos = movie.getGenres().stream()
                .map(genre -> new GenreDto(genre.getId(), genre.getName()))
                .collect(Collectors.toSet());
        movieDto.setGenreDos(genreDtos);
        Set<ReviewDto> reviewDtos = movie.getReviews().stream()
                .map(review -> new ReviewDto(review.getId(), review.getComment()))
                .collect(Collectors.toSet());
        movieDto.setReviewDtos(reviewDtos);
        movieDto.setDirectorDto(new DirectorDto(movie.getDirector().getId(), movie.getDirector().getName()));
        movieDto.setStudioDto(new StudioDto(movie.getStudio().getId(), movie.getStudio().getName()));
        return movieDto;
    }

    // get movies with actors having a specific name:
    List<Movie> moviesWithActor(String name){
        TypedQuery<Movie> query = entityManager.createQuery( "SELECT m FROM Movie m WHERE EXISTS " +
                        "(SELECT a FROM Actor a WHERE a.name = :name AND a MEMBER OF m.actors)",
                Movie.class);
        List<Movie> movieWithActors = query.getResultList();
        return movieWithActors;
    }

    // get movies with specific genre
    private List<Movie> moviesWithSpecificGenre(String name) {
//        TypedQuery<Movie> query = entityManager.createQuery(
//                "SELECT DISTINCT m FROM Movie m JOIN m.genres g WHERE g.name = :genreName",
//                Movie.class);
//
//        query.setParameter("genreName", name);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movieRoot = cq.from(Movie.class);
        Join<Movie, Genre> genreJoin = movieRoot.join("genres");

        cq.select(movieRoot).distinct(true);
        cq.where(cb.equal(genreJoin.get("name"), name));

        TypedQuery<Movie> query = entityManager.createQuery(cq);
        List<Movie> moviesWithGenre = query.getResultList();


        List<Movie> actionMovies = query.getResultList();
        return actionMovies;
    }

    //get Movies with a Certain Number of Reviews
    private List<Movie> moviesWithSpecificReview(String name) {
        TypedQuery<Movie> query = entityManager.createQuery(
                "SELECT m FROM Movie m WHERE " +
                        "(SELECT COUNT(r) FROM Review r WHERE r.movie = m) > 5",
                Movie.class);

        List<Movie> moviesWithReviews = query.getResultList();
        return moviesWithReviews;
    }

}
