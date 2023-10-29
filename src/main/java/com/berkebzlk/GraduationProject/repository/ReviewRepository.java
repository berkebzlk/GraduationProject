package com.berkebzlk.GraduationProject.repository;

import com.berkebzlk.GraduationProject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByHotelId(long hotelId);
}
