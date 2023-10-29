package com.berkebzlk.GraduationProject.controller;

import com.berkebzlk.GraduationProject.payload.ReviewDto;
import com.berkebzlk.GraduationProject.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{hotelId}/review")
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewDto reviewDto, @PathVariable(name = "hotelId") long hotelId) {
        ReviewDto savedReview = reviewService.addReview(reviewDto, hotelId);

        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}/review")
    public ResponseEntity<List<ReviewDto>> getReviewsByHotelId(@PathVariable(name = "hotelId") long hotelId) {
        List<ReviewDto> reviews = reviewService.getReviewsByHotel(hotelId);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/{hotelId}/review/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@Valid @RequestBody ReviewDto reviewDto,
                                                  @PathVariable(name = "hotelId") long hotelId,
                                                  @PathVariable(name = "reviewId") long reviewId) {
        ReviewDto updatedReview = reviewService.updateReview(reviewDto, hotelId, reviewId);

        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}/review/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable(name = "hotelId") long hotelId,
                                               @PathVariable(name = "reviewId") long reviewId) {
        String response = reviewService.deleteReview(hotelId, reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
