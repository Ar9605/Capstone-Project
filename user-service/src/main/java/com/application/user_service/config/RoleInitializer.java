package com.application.user_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.application.user_service.model.Role;
import com.application.user_service.model.RoleEnum;
import com.application.user_service.repository.RoleRepository;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName(RoleEnum.USER).isEmpty()) {
                Role userRole = new Role();
                userRole.setName(RoleEnum.USER);
                roleRepository.save(userRole);
            }

            if (roleRepository.findByName(RoleEnum.ADMIN).isEmpty()) {
                Role adminRole = new Role();
                adminRole.setName(RoleEnum.ADMIN);
                roleRepository.save(adminRole);
            }
        };
    }
}
