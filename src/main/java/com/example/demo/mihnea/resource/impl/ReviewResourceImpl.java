package com.example.demo.mihnea.resource.impl;

import com.example.demo.mihnea.model.Review;
import com.example.demo.mihnea.modelDto.ReviewDto;
import com.example.demo.mihnea.resource.ReviewResource;
import com.example.demo.mihnea.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(ReviewResourceImpl.PATH)
public class ReviewResourceImpl implements ReviewResource {

    public static final String PATH = "/review";

    @Inject
    private ReviewService reviewService;


    @Override
    @PostMapping("/new-review")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a studio.", responses = {
            @ApiResponse(responseCode = "201", description = "The studio was created.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Review> createReview(@RequestBody ReviewDto reviewDto) {
        final Review savedReview = reviewService.create(reviewDto);
        return ResponseEntity.ok(savedReview);
    }

    @Override
    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Review> findReviewById(Long id) {
        final Review foundReview = reviewService.findById(id);
        return ResponseEntity.ok(foundReview);
    }

    @Override
    public ResponseEntity<List<Review>> findAllReview() {
        return null;
    }

    @Override
    @PatchMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Review> updateReview(@RequestBody ReviewDto reviewDto) {
        final Review savedReview = reviewService.updateReview(reviewDto);
        return ResponseEntity.ok(savedReview);
    }

    @Override
    @DeleteMapping("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "405", description = "Method not allowed."),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Void> deleteReview(@RequestParam("id") Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}
