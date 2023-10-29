package com.berkebzlk.GraduationProject.security;

import com.berkebzlk.GraduationProject.entity.Personel;
import com.berkebzlk.GraduationProject.entity.Role;
import com.berkebzlk.GraduationProject.entity.User;
import com.berkebzlk.GraduationProject.repository.PersonelRepository;
import com.berkebzlk.GraduationProject.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PersonelRepository personelRepository;

    public CustomUserDetailsService(UserRepository userRepository, PersonelRepository personelRepository) {
        this.userRepository = userRepository;
        this.personelRepository = personelRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        Set<Personel> personels = personelRepository.findByUserId(user.getId());

        Set<Role> roles = personels.stream()
                .map(Personel::getRole)
                .collect(Collectors.toSet());

        Set <GrantedAuthority> authorities = roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

//        Set<GrantedAuthority> authorities = user
//                .getRoles()
//                .stream()
//                .map((role) -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
