package com.berkebzlk.GraduationProject.service.impl;

import com.berkebzlk.GraduationProject.entity.Hotel;
import com.berkebzlk.GraduationProject.entity.Role;
import com.berkebzlk.GraduationProject.entity.User;
import com.berkebzlk.GraduationProject.exception.ResourceNotFoundException;
import com.berkebzlk.GraduationProject.payload.HotelDto;
import com.berkebzlk.GraduationProject.repository.HotelRepository;
import com.berkebzlk.GraduationProject.repository.RoleRepository;
import com.berkebzlk.GraduationProject.repository.UserRepository;
import com.berkebzlk.GraduationProject.security.JwtTokenProvider;
import com.berkebzlk.GraduationProject.service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;

@Service
public class HotelServiceImpl implements HotelService {

    JwtTokenProvider jwtTokenProvider;
    ModelMapper modelMapper;
    HotelRepository hotelRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;

    public HotelServiceImpl(JwtTokenProvider jwtTokenProvider, ModelMapper modelMapper,
                            HotelRepository hotelRepository,
                            UserRepository userRepository,
                            RoleRepository roleRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.modelMapper = modelMapper;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public HotelDto createHotel(HotelDto hotelDto) {
        // get username from request
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameOrEmail = ((UserDetails) principal).getUsername();

        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);

        // for hotels_users
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", usernameOrEmail, 0));

        Set<User> users = new HashSet<>();
        Role hotelAdminRole = roleRepository.findByName("ROLE_HOTEL_ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "ROLE_HOTEL_ADMIN", 0));

        if (!user.getRoles().contains(hotelAdminRole)) {
            Set<Role> roles = new HashSet<>(user.getRoles());
            roles.add(hotelAdminRole);
            user.setRoles(roles);
            userRepository.save(user);
        }
        users.add(user);
        hotel.setUsers(users);

        Hotel savedHotel = hotelRepository.save(hotel);

        return modelMapper.map(savedHotel, HotelDto.class);
    }

}
