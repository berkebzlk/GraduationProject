package com.berkebzlk.GraduationProject.service;

import com.berkebzlk.GraduationProject.payload.PersonelDto;

import java.util.List;

public interface PersonelService {
    PersonelDto addPersonel(long hotelId, long userId, long roleId);

    String deletePersonel(long hotelId, long userId, long roleId);
}
