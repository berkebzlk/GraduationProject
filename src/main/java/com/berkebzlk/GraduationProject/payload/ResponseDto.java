package com.berkebzlk.GraduationProject.payload;

import com.berkebzlk.GraduationProject.entity.Review;
import com.berkebzlk.GraduationProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private long id;
    private String content;
    private UserDto user;
    private ReviewDto review;
}
