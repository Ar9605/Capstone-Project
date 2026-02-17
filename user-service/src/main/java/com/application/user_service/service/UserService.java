package com.application.user_service.service;
import org.springframework.stereotype.Service;

import com.application.user_service.dto.UserRegistrationRequest;
import com.application.user_service.model.Role;
import com.application.user_service.model.RoleEnum;
import com.application.user_service.model.User;
import com.application.user_service.repository.RoleRepository;
import com.application.user_service.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User registerUser(UserRegistrationRequest request) {
        User user = new User(
            request.getUsername(), 
            request.getEmail(), 
            request.getPassword()
        );
        Role userRole = roleRepository.findByName(RoleEnum.USER)
            .orElseThrow(() -> new RuntimeException("Error: Default role not found."));
        
        user.getRoles().add(userRole);
        
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));  //exception handling required
}
}