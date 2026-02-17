package com.application.auth_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.application.auth_service.dto.Userdto;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface Userclient {
    
    @GetMapping("/api/v1/users/email/{email}")
    Userdto getUserByEmail(@PathVariable("email") String email);
}