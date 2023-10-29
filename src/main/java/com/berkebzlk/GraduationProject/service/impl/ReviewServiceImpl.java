package com.berkebzlk.GraduationProject.service.impl;

import com.berkebzlk.GraduationProject.entity.Hotel;
import com.berkebzlk.GraduationProject.entity.Review;
import com.berkebzlk.GraduationProject.entity.User;
import com.berkebzlk.GraduationProject.exception.ResourceNotFoundException;
import com.berkebzlk.GraduationProject.payload.ReviewDto;
import com.berkebzlk.GraduationProject.payload.UserDto;
import com.berkebzlk.GraduationProject.repository.HotelRepository;
import com.berkebzlk.GraduationProject.repository.ReviewRepository;
import com.berkebzlk.GraduationProject.repository.UserRepository;
import com.berkebzlk.GraduationProject.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;
    UserRepository userRepository;
    HotelRepository hotelRepository;
    ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    private User getUserFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameOrEmail = ((UserDetails) principal).getUsername();

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();

        return user;
    }

    @Override
    public ReviewDto addReview(ReviewDto reviewDto, long hotelId) {

        User user = getUserFromSecurityContext();
        UserDto userDto = modelMapper.map(user, UserDto.class);

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));

        Review review = modelMapper.map(reviewDto, Review.class);
        review.setUser(user);
        review.setHotel(hotel);

        Review savedReview = reviewRepository.save(review);

        ReviewDto savedReviewDto = modelMapper.map(savedReview, ReviewDto.class);
        savedReviewDto.setUser(userDto);

        return savedReviewDto;
    }

    @Override
    public List<ReviewDto> getReviewsByHotel(long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));

        List<Review> reviews = reviewRepository.findReviewsByHotelId(hotelId);

        return reviews
                .stream()
                .map(review -> {
                    // Review entity'sini ReviewDTO'ya dönüştür
                    ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);

                    // User entity'sini UserDTO'ya dönüştür
                    UserDto userDto = modelMapper.map(review.getUser(), UserDto.class);
                    reviewDto.setUser(userDto);

                    return reviewDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto updateReview(ReviewDto reviewDto, long hotelId, long reviewId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        review.setContent(reviewDto.getContent());

        Review updatedReview = reviewRepository.save(review);

        UserDto userDto = modelMapper.map(updatedReview.getUser(), UserDto.class);

        ReviewDto updatedReviewDto = modelMapper.map(updatedReview, ReviewDto.class);
        updatedReviewDto.setUser(userDto);

        return updatedReviewDto;
    }

    @Override
    public String deleteReview(long hotelId, long reviewId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        reviewRepository.delete(review);

        return "Review successfully deleted!";
    }
}
