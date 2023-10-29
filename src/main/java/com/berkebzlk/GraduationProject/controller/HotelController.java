package com.berkebzlk.GraduationProject.controller;

import com.berkebzlk.GraduationProject.entity.Hotel;
import com.berkebzlk.GraduationProject.payload.HotelDto;
import com.berkebzlk.GraduationProject.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@Valid @RequestBody HotelDto hotelDto) {
        HotelDto createdHotelDto = hotelService.createHotel(hotelDto);
        return new ResponseEntity<>(createdHotelDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotel(@PathVariable(name = "id") long hotelId) {
        HotelDto hotelDto = hotelService.getHotel(hotelId);

        return new ResponseEntity<>(hotelDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        List<HotelDto> hotels = hotelService.getAllHotels();

        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOTEL_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotel(@Valid @RequestBody HotelDto hotelDto,
                                                @PathVariable(name = "id") long hotelId) {
        HotelDto updatedHotel = hotelService.updateHotel(hotelDto, hotelId);

        return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOTEL_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable("id") long hotelId) {
        String response = hotelService.deleteHotel(hotelId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
