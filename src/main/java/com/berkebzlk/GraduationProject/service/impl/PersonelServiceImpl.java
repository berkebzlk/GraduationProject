package com.berkebzlk.GraduationProject.service.impl;

import com.berkebzlk.GraduationProject.entity.Hotel;
import com.berkebzlk.GraduationProject.entity.Personel;
import com.berkebzlk.GraduationProject.entity.Role;
import com.berkebzlk.GraduationProject.entity.User;
import com.berkebzlk.GraduationProject.exception.ResourceAlreadyExistsException;
import com.berkebzlk.GraduationProject.exception.ResourceNotFoundException;
import com.berkebzlk.GraduationProject.payload.PersonelDto;
import com.berkebzlk.GraduationProject.repository.HotelRepository;
import com.berkebzlk.GraduationProject.repository.PersonelRepository;
import com.berkebzlk.GraduationProject.repository.RoleRepository;
import com.berkebzlk.GraduationProject.repository.UserRepository;
import com.berkebzlk.GraduationProject.service.PersonelService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonelServiceImpl implements PersonelService {

    PersonelRepository personelRepository;
    UserRepository userRepository;
    HotelRepository hotelRepository;
    RoleRepository roleRepository;
    ModelMapper modelMapper;

    public PersonelServiceImpl(PersonelRepository personelRepository, UserRepository userRepository, HotelRepository hotelRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.personelRepository = personelRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    private User getUserFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameOrEmail = ((UserDetails) principal).getUsername();

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();

        return user;
    }

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

    @Override
    public PersonelDto addPersonel(long hotelId, long userId, long roleId) {

        checkIfUserIsHotelAdmin(getUserFromSecurityContext().getId(), hotelId);

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));

        if(personelRepository.existsByHotelIdAndUserIdAndRoleId(hotelId, userId, roleId)) {
            throw new ResourceAlreadyExistsException("Personel", "user id - hotel id", userId + "-" + hotelId);
        }

        Personel personel = new Personel();
        personel.setHotel(hotel);
        personel.setUser(user);
        personel.setRole(role);

        Personel savedPersonel = personelRepository.save(personel);

        return modelMapper.map(savedPersonel, PersonelDto.class);
    }

    @Override
    public String deletePersonel(long hotelId, long userId, long roleId) {

        checkIfUserIsHotelAdmin(getUserFromSecurityContext().getId(), hotelId);

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));

        personelRepository.deleteByHotelIdAndUserIdAndRoleId(hotelId, userId, roleId);

        return "Personel successfully deleted!";
    }
}
