package com.berkebzlk.GraduationProject.service;

import com.berkebzlk.GraduationProject.entity.Review;
import com.berkebzlk.GraduationProject.payload.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto addReview(ReviewDto reviewDto, long hotelId);

    List<ReviewDto> getReviewsByHotel(long hotelId);

    ReviewDto updateReview(ReviewDto reviewDto, long hotelId, long reviewId);

    String deleteReview(long hotelId, long reviewId);
}
