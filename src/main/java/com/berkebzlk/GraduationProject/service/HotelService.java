package com.berkebzlk.GraduationProject.service;

import com.berkebzlk.GraduationProject.payload.HotelDto;

import java.util.List;

public interface HotelService {
    HotelDto createHotel(HotelDto hotelDto);

    HotelDto getHotel(long hotelId);

    List<HotelDto> getAllHotels();

    HotelDto updateHotel(HotelDto hotelDto, long hotelId);

    String deleteHotel(long hotelId);

    void checkIfUserIsHotelAdmin(long userId, long hotelId);
}
