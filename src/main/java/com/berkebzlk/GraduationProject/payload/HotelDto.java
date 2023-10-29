package com.berkebzlk.GraduationProject.payload;

import com.berkebzlk.GraduationProject.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    @NotBlank
    @Size(min=3, message = "Hotel name should have at least 3 characters")
    private String name;

}
