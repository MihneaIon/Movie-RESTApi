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
import springfox.documentation.annotations.Cacheable;

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
    public MovieDto create(MovieDto movieDto) {
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

        List<Review> reviews = new ArrayList<>();
        for(ReviewDto reviewDto : movieDto.getReviewDtos()){
            Review existingReview = reviewRepository.findById(reviewDto.getId()).get();
            if(existingReview == null){
                reviews.add(new Review(reviewDto.getComment()));
            } else {
                reviews.add(existingReview);
            }
        }

        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setMovieStatus(movieDto.getMovieStatus());
        movie.setDirector(director);
        movie.setGenres(genres);
        movie.setActors(actors);
        movie.setStudio(studio);
        movie.setReviews(reviews);

        movieRepository.save(movie);

        return convertEntityToDto(movie);
    }

    @Override
    @Cacheable(value = "movie")
    public MovieDto findById(Long id) {
            Movie movie = movieRepository.findById(id).orElse(null);
            return convertEntityToDto(movie);
    }

    @Override
    public List<MovieDto> findAllMovie() {
        return null;
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        if(movieRepository.existsById(id)){
            movieRepository.deleteById(id);
        }
    }

    @Override
    public MovieDto updateMovie(MovieDto movieDto) {
        try {
            Movie movie = movieRepository.findById(movieDto.getId()).get();
            Movie savedMovie = updateAttributes(movieDto, movie);
            movieRepository.saveAndFlush(movie);
            return convertEntityToDto(movie);
        }catch (Exception e){
            e.printStackTrace();
            //return new EntityNotFoundException("Movie with ID " + movieDto.getId() + " not found");
            return null;
        }
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

    private Movie convertDtoToEntity(MovieDto movieDto){
        Movie movie = new Movie();
        movie.setId(movieDto.getId());
        movie.setTitle(movieDto.getTitle());
        movie.setMovieStatus(movieDto.getMovieStatus());
        if (movieDto.getDirectorDto() != null) {
            movie.setDirector(directorServiceImpl.findById(movieDto.getDirectorDto().getId().intValue()));
        }
        if (movieDto.getStudioDto() != null) {
            Studio studio = studioRepository.findById(movieDto.getStudioDto().getId()).orElseThrow(() -> new EntityNotFoundException("Studio with this id doesn't exist"));
            movie.setStudio(studio);
        }
        if (movieDto.getGenreDos() != null && !movieDto.getGenreDos().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(movieDto.getGenreDos().stream().map(GenreDto::getId).collect(Collectors.toSet()));
            movie.setGenres(genres);
        }
        if (movieDto.getReviewDtos() != null && !movieDto.getReviewDtos().isEmpty()) {
            List<Review> reviews = reviewRepository.findAllById(movieDto.getReviewDtos().stream().map(ReviewDto::getId).collect(Collectors.toSet()));
            movie.setReviews(reviews);
        }
        if (movieDto.getActorDto() != null && !movieDto.getActorDto().isEmpty()) {
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
    public void updateMovieTitle(Long id, String newTitle, MovieStatus movieStatus) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null) {
            movie.setTitle(newTitle);
            movie.setMovieStatus(movieStatus);
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
        graph.addAttributeNodes("movie-with-actors"); // the name of the Entity Graph

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
        movieDto.setMovieStatus(movie.getMovieStatus());
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

    private Movie updateAttributes(MovieDto movieDto, Movie movie){
        if(movieDto.getId()!= null){
            movie.setId(movieDto.getId());
        }
        if(movieDto.getTitle()!= null){
            movie.setTitle(movieDto.getTitle());
        }
        if(movieDto.getMovieStatus()!= null){
            movie.setMovieStatus(movieDto.getMovieStatus());
        }
        if(movieDto.getId()!= null){
            movie.setId(movieDto.getId());
        }
        if (movieDto.getDirectorDto() != null) {
            movie.setDirector(directorServiceImpl.findById(movieDto.getDirectorDto().getId().intValue()));
        }
        if (movieDto.getStudioDto() != null) {
            Studio studio = studioRepository.findById(movieDto.getStudioDto().getId()).orElseThrow(() -> new EntityNotFoundException("Studio with this id doesn't exist"));
            movie.setStudio(studio);
        }
        if (movieDto.getGenreDos() != null && !movieDto.getGenreDos().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(movieDto.getGenreDos().stream().map(GenreDto::getId).collect(Collectors.toSet()));
            movie.setGenres(genres);
        }
        if (movieDto.getReviewDtos() != null && !movieDto.getReviewDtos().isEmpty()) {
            List<Review> reviews = reviewRepository.findAllById(movieDto.getReviewDtos().stream().map(ReviewDto::getId).collect(Collectors.toSet()));
            movie.setReviews(reviews);
        }
        if (movieDto.getActorDto() != null && !movieDto.getActorDto().isEmpty()) {
            List<Actor> actors = actorRepository.findAllById(movieDto.getActorDto().stream().map(ActorDto::getId).collect(Collectors.toSet()));
            movie.setActors(actors);
        }
        return movie;
    }

    @Transactional
    public MovieDto updateOrAddMovie(MovieDto movieDto) {
        Movie existingMovie = null;
        if (movieDto.getId() != null) {
            existingMovie = movieRepository.findById(movieDto.getId()).orElse(null);
        }

        Movie movie = (existingMovie != null) ? existingMovie : new Movie();

        movie.setTitle(movieDto.getTitle());
        movie.setMovieStatus(movieDto.getMovieStatus());

        Director director = directorRepsoitory.findById(movieDto.getDirectorDto().getId())
                .orElseGet(() -> new Director(movieDto.getDirectorDto().getName()));
        movie.setDirector(director);

        List<Genre> genres = new ArrayList<>();
        for (GenreDto genreDto : movieDto.getGenreDos()) {
            Genre genre = genreRepository.findById(genreDto.getId())
                    .orElseGet(() -> new Genre(genreDto.getName()));
            genres.add(genre);
        }
        movie.setGenres(genres);

        List<Actor> actors = new ArrayList<>();
        for (ActorDto actorDto : movieDto.getActorDto()) {
            Actor actor = actorRepository.findById(actorDto.getId())
                    .orElseGet(() -> new Actor(actorDto.getName()));
            actors.add(actor);
        }
        movie.setActors(actors);

        List<Review> reviews = new ArrayList<>();
        for (ReviewDto reviewDto : movieDto.getReviewDtos()) {
            Review review = reviewRepository.findById(reviewDto.getId())
                    .orElseGet(() -> new Review(reviewDto.getComment()));
            reviews.add(review);
        }
        movie.getReviews().clear();
        movie.getReviews().addAll(reviews);

        movie = movieRepository.save(movie);

        return convertEntityToDto(movie);
    }

}
