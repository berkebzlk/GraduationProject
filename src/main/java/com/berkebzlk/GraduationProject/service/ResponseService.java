package com.berkebzlk.GraduationProject.service;

import com.berkebzlk.GraduationProject.payload.ResponseDto;

import java.util.List;

public interface ResponseService {
    ResponseDto addResponse(ResponseDto responseDto, long reviewId);

    List<ResponseDto> getResponsesByReview(long reviewId);

    ResponseDto getResponse(long responseId);

    ResponseDto updateResponse(ResponseDto responseDto, long responseId);

    String deleteResponse(long responseId);

    List<ResponseDto> getResponsesByUserId(long userId);
}
