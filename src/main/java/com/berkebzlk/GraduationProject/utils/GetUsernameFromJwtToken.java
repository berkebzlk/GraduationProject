package com.berkebzlk.GraduationProject.utils;

import com.berkebzlk.GraduationProject.GraduationProjectApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class GetUsernameFromJwtToken {

    private static AuthenticationManager authenticationManager;

    @Autowired
    public GetUsernameFromJwtToken(AuthenticationManager authenticationManager) {
        GetUsernameFromJwtToken.authenticationManager = authenticationManager;
    }

    public static void main(String[] args) {
        // Spring uygulama bağlamını başlat
        ConfigurableApplicationContext context = SpringApplication.run(GraduationProjectApplication.class, args);

        // Sınıfın bir örneğini al
        GetUsernameFromJwtToken instance = context.getBean(GetUsernameFromJwtToken.class);

        // Oturum açma işlemini gerçekleştir
        instance.login("berke", "berke");

        System.out.println("giriş yapıldı");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String usernameOrEmail = ((UserDetails) principal).getUsername();
        System.out.println(principal);
    }

    public void login(String username, String rawPassword) {

        // Oturum açma tokeni oluştur
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, rawPassword);

        // Oturumu doğrula
        Authentication authentication = authenticationManager.authenticate(token);

        // Kimlik doğrulama tokenini güvenlik bağlamına ayarla
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}