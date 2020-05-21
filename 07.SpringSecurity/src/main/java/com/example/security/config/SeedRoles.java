package com.example.security.config;

import com.example.security.domain.entities.Role;
import com.example.security.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SeedRoles {
    private RoleRepository roleRepository;

    @Autowired
    public SeedRoles(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void seedRoles() {
        if (roleRepository.findAll().isEmpty()) {
            Role userRole = new Role();
            userRole.setAuthority("USER");
            roleRepository.save(userRole);
        }
    }
}
