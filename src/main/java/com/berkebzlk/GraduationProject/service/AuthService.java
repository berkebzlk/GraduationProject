package com.berkebzlk.GraduationProject.service;

import com.berkebzlk.GraduationProject.payload.LoginDto;
import com.berkebzlk.GraduationProject.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
