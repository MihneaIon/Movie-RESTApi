package com.example.demo.mihnea.service;

import com.example.demo.mihnea.model.Review;
import com.example.demo.mihnea.modelDto.ReviewDto;

import java.util.List;

public interface ReviewService {

    Review create(ReviewDto reviewDto);

    Review findById(Long id);

    List<Review> findAllGenre();

    void deleteReview(Long id);

    List<Review> getGenreWithMoreThanXMovies(long numberOfMovies);

    Review updateReview(ReviewDto reviewDto);
}
