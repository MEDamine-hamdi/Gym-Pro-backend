package com.gympro.gymprobackend.config;

import com.gympro.gymprobackend.entity.ERole;
import com.gympro.gymprobackend.entity.Role;
import com.gympro.gymprobackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeRoles(@Autowired RoleRepository roleRepository) {
        return args -> {
            // Check if roles already exist
            if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
                Role userRole = new Role();
                userRole.setName(ERole.ROLE_USER);
                roleRepository.save(userRole);
                System.out.println("✓ ROLE_USER initialized");
            }

            if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
                Role adminRole = new Role();
                adminRole.setName(ERole.ROLE_ADMIN);
                roleRepository.save(adminRole);
                System.out.println("✓ ROLE_ADMIN initialized");
            }

            if (roleRepository.findByName(ERole.ROLE_RECEPTIONIST).isEmpty()) {
                Role receptionistRole = new Role();
                receptionistRole.setName(ERole.ROLE_RECEPTIONIST);
                roleRepository.save(receptionistRole);
                System.out.println("✓ ROLE_RECEPTIONIST initialized");
            }
        };
    }
}
