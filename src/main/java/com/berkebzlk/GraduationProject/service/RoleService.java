package com.berkebzlk.GraduationProject.service;

import com.berkebzlk.GraduationProject.entity.Role;

import java.util.Set;

public interface RoleService {
    Role addRole(Role role);

    Set<Role> getUsersRolesByHotelId(long hotelId, long userId);
}
