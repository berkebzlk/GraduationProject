package com.berkebzlk.GraduationProject.controller;

import com.berkebzlk.GraduationProject.payload.ResponseDto;
import com.berkebzlk.GraduationProject.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ResponseController {
    ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping("/review/{reviewId}/response")
    public ResponseEntity<ResponseDto> addResponse(@RequestBody ResponseDto responseDto,
                                                   @PathVariable(name = "reviewId") long reviewId) {
        ResponseDto savedResponseDto = responseService.addResponse(responseDto, reviewId);

        return new ResponseEntity<>(savedResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/review/{reviewId}/response")
    public ResponseEntity<List<ResponseDto>> getResponsesByReviewId(@PathVariable(name = "reviewId") long reviewId) {
        List<ResponseDto> responses = responseService.getResponsesByReview(reviewId);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}/response/{responseId}")
    public ResponseEntity<ResponseDto> getResponse(@PathVariable(name = "responseId") long responseId) {
        ResponseDto responseDto = responseService.getResponse(responseId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/review/{reviewId}/response/{responseId}")
    public ResponseEntity<ResponseDto> updateResponse(@RequestBody ResponseDto responseDto,
                                                      @PathVariable long responseId) {
        ResponseDto savedResponseDto = responseService.updateResponse(responseDto, responseId);

        return new ResponseEntity<>(savedResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/review/{reviewId}/response/{responseId}")
    public ResponseEntity<String> deleteResponse(@PathVariable(name = "responseId") long responseId) {
        String responseString = responseService.deleteResponse(responseId);

        return new ResponseEntity<>(responseString, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/response")
    public ResponseEntity<List<ResponseDto>> getResponsesByUserId(@PathVariable(name = "userId") long userId) {
        List<ResponseDto> responsesByUserId = responseService.getResponsesByUserId(userId);

        return new ResponseEntity<>(responsesByUserId, HttpStatus.OK);
    }
}
