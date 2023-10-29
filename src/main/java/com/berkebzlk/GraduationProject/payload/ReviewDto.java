package com.berkebzlk.GraduationProject.payload;

import com.berkebzlk.GraduationProject.entity.Hotel;
import com.berkebzlk.GraduationProject.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private long id;
    @NotBlank(message = "Your message cannot be blank")
    @Size(min = 2, message = "Message must be minimum 2 characters")
    private String content;
    private UserDto user;
    private Hotel hotel;
}
