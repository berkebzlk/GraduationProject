package com.berkebzlk.GraduationProject.repository;

import com.berkebzlk.GraduationProject.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findResponsesByReviewId(long reviewId);

    List<Response> findResponsesByUserId(long userId);
}
