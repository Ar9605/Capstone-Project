package com.application.auth_service.service;

import org.springframework.stereotype.Service;

import com.application.auth_service.client.Userclient;
import com.application.auth_service.dto.AuthRequest;
import com.application.auth_service.dto.Userdto;
import com.application.auth_service.util.JwtUtil;

import feign.FeignException;

@Service
public class AuthService {

    private final Userclient userClient;
    private final JwtUtil jwtUtil;

    public AuthService(Userclient userClient, JwtUtil jwtUtil) {
        this.userClient = userClient;
        this.jwtUtil = jwtUtil;
    }

    public String login(AuthRequest request) {
        try {
     
            Userdto user = userClient.getUserByEmail(request.getEmail());
            
            if (user != null && request.getPassword().equals(user.getPassword())){
                String roleName="USER";
                if(user.getRoles() != null && !user.getRoles().isEmpty()) {
                    roleName = user.getRoles().iterator().next().getName(); 
                }
                return jwtUtil.generateToken(user.getEmail(), roleName);
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("User not found with that email");
        } catch (FeignException e) {
            throw new RuntimeException("Error communicating with User Service"+e.getMessage());
        }
    }
}