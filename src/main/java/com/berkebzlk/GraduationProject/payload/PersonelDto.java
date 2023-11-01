package com.berkebzlk.GraduationProject.payload;

import com.berkebzlk.GraduationProject.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonelDto {
    private long id;
    private UserDto user;
    private HotelDto hotel;
    private Role role;
}
