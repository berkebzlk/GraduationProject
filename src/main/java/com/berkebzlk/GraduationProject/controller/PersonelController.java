package com.berkebzlk.GraduationProject.controller;

import com.berkebzlk.GraduationProject.payload.PersonelRequest;
import com.berkebzlk.GraduationProject.payload.PersonelDto;
import com.berkebzlk.GraduationProject.service.PersonelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class PersonelController {
    PersonelService personelService;

    public PersonelController(PersonelService personelService) {
        this.personelService = personelService;
    }

    @PostMapping("/{hotelId}/personel")
    public ResponseEntity<PersonelDto> addPersonel(@RequestBody PersonelRequest addPersonelRequest,
                                                @PathVariable(name = "hotelId") long hotelId) {
        PersonelDto savedPersonel = personelService
                .addPersonel(hotelId, addPersonelRequest.getUserId(), addPersonelRequest.getRoleId());

        return new ResponseEntity<>(savedPersonel, HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}/personel")
    public ResponseEntity<String> deletePersonel(@RequestBody PersonelRequest personelRequest,
                                                 @PathVariable long hotelId) {
        String responseString = personelService
                .deletePersonel(hotelId, personelRequest.getUserId(), personelRequest.getRoleId());

        return new ResponseEntity<>(responseString, HttpStatus.OK);
    }
}
