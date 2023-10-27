package com.berkebzlk.GraduationProject.payload;

import com.berkebzlk.GraduationProject.entity.User;
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
    private String name;

    private Set<User> users;
}
