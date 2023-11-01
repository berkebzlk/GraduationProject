package com.berkebzlk.GraduationProject.controller;

import com.berkebzlk.GraduationProject.entity.Role;
import com.berkebzlk.GraduationProject.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/api/role")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {

        Role addedRole = roleService.addRole(role);

        return new ResponseEntity<>(addedRole, HttpStatus.CREATED);
    }
}
