package com.berkebzlk.GraduationProject.controller;

import com.berkebzlk.GraduationProject.payload.HotelDto;
import com.berkebzlk.GraduationProject.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        HotelDto createdHotelDto = hotelService.createHotel(hotelDto);
        return new ResponseEntity<>(createdHotelDto, HttpStatus.CREATED);
    }
}
