package com.berkebzlk.GraduationProject.service.impl;

import com.berkebzlk.GraduationProject.entity.Hotel;
import com.berkebzlk.GraduationProject.entity.Personel;
import com.berkebzlk.GraduationProject.entity.Role;
import com.berkebzlk.GraduationProject.entity.User;
import com.berkebzlk.GraduationProject.exception.HotelAPIException;
import com.berkebzlk.GraduationProject.payload.LoginDto;
import com.berkebzlk.GraduationProject.payload.RegisterDto;
import com.berkebzlk.GraduationProject.repository.PersonelRepository;
import com.berkebzlk.GraduationProject.repository.RoleRepository;
import com.berkebzlk.GraduationProject.repository.UserRepository;
import com.berkebzlk.GraduationProject.security.JwtTokenProvider;
import com.berkebzlk.GraduationProject.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PersonelRepository personelRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PersonelRepository personelRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.personelRepository = personelRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new HotelAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new HotelAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Personel personel = new Personel();

        User savedUser = userRepository.save(user);
        Role role = roleRepository.findByName("ROLE_USER").get();
        Hotel hotel = null;

        personel.setUser(savedUser);
        personel.setRole(role);
        personel.setHotel(hotel);

        personelRepository.save(personel);

        return "User registered successfully!";
    }
}
