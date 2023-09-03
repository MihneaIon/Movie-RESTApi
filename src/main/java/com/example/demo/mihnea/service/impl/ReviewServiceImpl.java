package com.example.demo.mihnea.service.impl;

import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.model.Review;
import com.example.demo.mihnea.modelDto.ReviewDto;
import com.example.demo.mihnea.service.ReviewService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Review create(ReviewDto reviewDto) {
        Review review;
        if (reviewDto.getId() != null) {
            review = findById(reviewDto.getId());
        } else {
            review = new Review();
        }
        review.setComment(reviewDto.getComment());
        if (reviewDto.getMovie() != null && reviewDto.getMovie() != null) {
            Movie movie = reviewDto.getMovie();
            reviewDto.setMovie(movie);
        }
        entityManager.persist(review);
        return review;
    }

    @Override
    public Review findById(Long id) {
        return entityManager.find(Review.class, id);
    }

    @Override
    public List<Review> findAllGenre() {
        String jpql = "SELECT r FROM Review r";
        TypedQuery<Review> query = entityManager.createQuery(jpql, Review.class);
        return query.getResultList();
    }

    @Override
    public void deleteReview(Long id) {
        Review review = this.findById(id);
        entityManager.remove(review);
    }

    @Override
    public List<Review> getGenreWithMoreThanXMovies(long numberOfMovies) {
        return null;
    }

    @Override
    public Review updateReview(ReviewDto reviewDto) {
        Review review = this.findById(reviewDto.getId());
        if (reviewDto.getId() != null) {
            review.setComment(reviewDto.getComment());
            if (reviewDto.getMovie() != null) {
                review.setMovie(reviewDto.getMovie());
            }
            entityManager.merge(review);
        }
        return review;
    }
}
