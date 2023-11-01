package com.berkebzlk.GraduationProject.service.impl;

import com.berkebzlk.GraduationProject.entity.Hotel;
import com.berkebzlk.GraduationProject.entity.Role;
import com.berkebzlk.GraduationProject.exception.ResourceAlreadyExistsException;
import com.berkebzlk.GraduationProject.exception.ResourceNotFoundException;
import com.berkebzlk.GraduationProject.repository.HotelRepository;
import com.berkebzlk.GraduationProject.repository.RoleRepository;
import com.berkebzlk.GraduationProject.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    HotelRepository hotelRepository;

    public RoleServiceImpl(RoleRepository roleRepository, HotelRepository hotelRepository) {
        this.roleRepository = roleRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Role addRole(Role role) {

        boolean isRoleExists = roleRepository.existsByName(role.getName());

        if (isRoleExists) {
            throw new ResourceAlreadyExistsException("Role", "name", role.getName());
        }

        Role savedRole = roleRepository.save(role);

        return savedRole;
    }

    @Override
    public Set<Role> getUsersRolesByHotelId(long hotelId, long userId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));



        return null;
    }
}
