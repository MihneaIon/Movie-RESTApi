//package com.example.demo.mihnea.movie;
//
//import com.example.demo.mihnea.model.Movie;
//import com.example.demo.mihnea.repository.MovieRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DataJpaTest
//@ActiveProfiles("test")
//public class MovieRepositoryTest {
//    @Autowired
//    private MovieRepository movieRepository;
//
//    @BeforeEach
//    public void setUp() {
//        // You can initialize test data here before each test if needed
//    }
//
//    @Test
//    public void testSaveMovie() {
//        // Create a Movie entity
//        Movie movie = new Movie();
//        movie.setTitle("Test Movie");
//
//        // Save the Movie entity to the repository
//        Movie savedMovie = movieRepository.save(movie);
//
//        // Verify that the saved Movie entity has an ID
//        assertNotNull(savedMovie.getId());
//    }
//
//    @Test
//    public void testFindMovieById() {
//        // Create a Movie entity
//        Movie movie = new Movie();
//        movie.setTitle("Test Movie");
//        Movie savedMovie = movieRepository.save(movie);
//
//        // Find the Movie by ID
//        Movie foundMovie = movieRepository.findById(savedMovie.getId()).orElse(null);
//
//        // Verify that the found Movie matches the saved Movie
//        assertNotNull(foundMovie);
//        assertEquals(savedMovie.getId(), foundMovie.getId());
//        assertEquals(savedMovie.getTitle(), foundMovie.getTitle());
//    }
//
//    @Test
//    public void testFindAllMovies() {
//        // Create and save multiple Movie entities
//        Movie movie1 = new Movie();
//        movie1.setTitle("Movie 1");
//        Movie movie2 = new Movie();
//        movie2.setTitle("Movie 2");
//        movieRepository.saveAll(List.of(movie1, movie2));
//
//        // Find all Movie entities in the repository
//        List<Movie> movies = movieRepository.findAll();
//
//        // Verify that the list is not empty and contains the expected movies
//        assertNotNull(movies);
//        assertEquals(2, movies.size());
//    }
//}
