package com.example.demo.mihnea.resource;

import com.example.demo.mihnea.model.Review;
import com.example.demo.mihnea.modelDto.ReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ReviewResource {
    public ResponseEntity<Review> createReview(ReviewDto reviewDto);
    //
    public ResponseEntity<Review> findReviewById(@PathVariable Long id);

    public ResponseEntity<List<Review>> findAllReview();

    public ResponseEntity<Review> updateReview(ReviewDto reviewDto);

    public ResponseEntity<Void> deleteReview(@PathVariable Long id);

}
