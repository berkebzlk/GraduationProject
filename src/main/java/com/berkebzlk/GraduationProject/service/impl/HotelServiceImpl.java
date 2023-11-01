package com.berkebzlk.GraduationProject.service.impl;

import com.berkebzlk.GraduationProject.entity.Hotel;
import com.berkebzlk.GraduationProject.entity.Personel;
import com.berkebzlk.GraduationProject.entity.Role;
import com.berkebzlk.GraduationProject.entity.User;
import com.berkebzlk.GraduationProject.exception.ResourceNotFoundException;
import com.berkebzlk.GraduationProject.payload.HotelDto;
import com.berkebzlk.GraduationProject.repository.HotelRepository;
import com.berkebzlk.GraduationProject.repository.PersonelRepository;
import com.berkebzlk.GraduationProject.repository.RoleRepository;
import com.berkebzlk.GraduationProject.repository.UserRepository;
import com.berkebzlk.GraduationProject.security.JwtTokenProvider;
import com.berkebzlk.GraduationProject.service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    ModelMapper modelMapper;
    HotelRepository hotelRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PersonelRepository personelRepository;

    public HotelServiceImpl(ModelMapper modelMapper, HotelRepository hotelRepository, UserRepository userRepository, RoleRepository roleRepository, PersonelRepository personelRepository) {
        this.modelMapper = modelMapper;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.personelRepository = personelRepository;
    }

    private User getUserFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameOrEmail = ((UserDetails) principal).getUsername();

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();

        return user;
    }

    @Override
    public HotelDto createHotel(HotelDto hotelDto) {

        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);

        User user = getUserFromSecurityContext();
        Role hotelAdminRole = roleRepository.findByName("ROLE_HOTEL_ADMIN").get();
        Hotel savedHotel = hotelRepository.save(hotel);

        Personel personel = new Personel();
        personel.setUser(user);
        personel.setRole(hotelAdminRole);
        personel.setHotel(savedHotel);
        personelRepository.save(personel);

        return modelMapper.map(savedHotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotel(long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));

        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public List<HotelDto> getAllHotels() {

        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().map(hotel -> modelMapper.map(hotel, HotelDto.class)).collect(Collectors.toList());
    }

    @Override
    public HotelDto updateHotel(HotelDto hotelDto, long hotelId) {

        User user = getUserFromSecurityContext();
        checkIfUserIsHotelAdmin(user.getId(), hotelId);

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));

        hotel.setName(hotelDto.getName());

        Hotel savedHotel = hotelRepository.save(hotel);

        return modelMapper.map(savedHotel, HotelDto.class);
    }

    @Override
    public String deleteHotel(long hotelId) {

        User user = getUserFromSecurityContext();
        checkIfUserIsHotelAdmin(user.getId(), hotelId);

        hotelRepository.deleteById(hotelId);

        return "Hotel successfully deleted!";
    }

    @Override
    public void checkIfUserIsHotelAdmin(long userId, long hotelId) {

        Role hotelAdminRole = roleRepository.findByName("ROLE_HOTEL_ADMIN").get();

        Set<Personel> personels = personelRepository.findByHotelId(hotelId);

        // find hotel admins
        Set<Personel> hotelAdmins = personels
                .stream()
                .filter(personel -> personel.getRole().equals(hotelAdminRole))
                .collect(Collectors.toSet());

        boolean isUserHotelAdmin = hotelAdmins
                .stream()
                .anyMatch(personel -> personel.getUser().getId() == userId);

        if (!isUserHotelAdmin) {
            throw new AccessDeniedException("You are not authorized to perform this action.");
        }
    }
}
