package com.berkebzlk.GraduationProject.service.impl;

import com.berkebzlk.GraduationProject.entity.Response;
import com.berkebzlk.GraduationProject.entity.Review;
import com.berkebzlk.GraduationProject.entity.User;
import com.berkebzlk.GraduationProject.exception.ResourceNotFoundException;
import com.berkebzlk.GraduationProject.payload.ResponseDto;
import com.berkebzlk.GraduationProject.repository.ResponseRepository;
import com.berkebzlk.GraduationProject.repository.ReviewRepository;
import com.berkebzlk.GraduationProject.repository.UserRepository;
import com.berkebzlk.GraduationProject.service.ResponseService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseServiceImpl implements ResponseService {
    ResponseRepository responseRepository;
    ReviewRepository reviewRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    public ResponseServiceImpl(ResponseRepository responseRepository, ReviewRepository reviewRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.responseRepository = responseRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    private User getUserFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameOrEmail = ((UserDetails) principal).getUsername();

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();

        return user;
    }

    @Override
    public ResponseDto addResponse(ResponseDto responseDto, long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        Response response = modelMapper.map(responseDto, Response.class);
        response.setReview(review);
        response.setUser(getUserFromSecurityContext());

        Response savedResponse = responseRepository.save(response);

        return modelMapper.map(savedResponse, ResponseDto.class);
    }

    @Override
    public List<ResponseDto> getResponsesByReview(long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        List<Response> responses = responseRepository.findResponsesByReviewId(reviewId);

        return responses
                .stream()
                .map(response -> modelMapper.map(response, ResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDto getResponse(long responseId) {

        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("Response", "id", responseId));

        return modelMapper.map(response, ResponseDto.class);
    }

    @Override
    public ResponseDto updateResponse(ResponseDto responseDto, long responseId) {

        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("Response", "id", responseId));

        checkIfRequestUserOwnsResponse(response, "You have no access to change this response!");

        response.setContent(responseDto.getContent());

        Response savedResponse = responseRepository.save(response);

        return modelMapper.map(savedResponse, ResponseDto.class);
    }

    @Override
    public String deleteResponse(long responseId) {

        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("Response", "id", responseId));

        checkIfRequestUserOwnsResponse(response, "You have no access to delete this response!");

        responseRepository.delete(response);

        return "Response successfully deleted!";
    }

    @Override
    public List<ResponseDto> getResponsesByUserId(long userId) {

        List<Response> responsesByUser = responseRepository.findResponsesByUserId(userId);

        return responsesByUser
                .stream()
                .map(response -> modelMapper.map(response, ResponseDto.class))
                .collect(Collectors.toList());
    }

    private void checkIfRequestUserOwnsResponse(Response response, String message) {
        User user = getUserFromSecurityContext();
        if (user.getId() != response.getUser().getId()) {
            throw new AccessDeniedException(message);
        }
    }
}
