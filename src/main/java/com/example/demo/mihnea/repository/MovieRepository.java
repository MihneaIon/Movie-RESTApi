package com.example.demo.mihnea.repository;

import com.example.demo.mihnea.model.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenre(@Param("genreId") Long genreId);

    List<Movie> findByTitleContainingIgnoreCase(String keyword);

    @EntityGraph(attributePaths = "genres")
    List<Movie> findByGenresName(String genreName);

    // JPQL Query
    @Query("SELECT m FROM Movie m WHERE m.title LIKE %:keyword%")
    List<Movie> searchMovies(@Param("keyword") String keyword);

    //Native Sql Query
    @Query(value = "SELECT * FROM movies WHERE title LIKE %:keyword%", nativeQuery = true)
    List<Movie> searchMoviesNative(@Param("keyword") String keyword);
}
