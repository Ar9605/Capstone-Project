package com.application.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.application.api_gateway.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(csrf -> csrf.disable()) 
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/api/v1/auth/**").permitAll()
                .pathMatchers("/api/v1/users/register").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .pathMatchers("/api/v1/users/**").hasAnyRole("USER", "ADMIN")
                .pathMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/products/**").hasAnyRole("USER", "ADMIN")
                .pathMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/api/v1/inventory/**").hasAnyRole("USER", "ADMIN")
                .pathMatchers(HttpMethod.POST, "/api/v1/inventory/**").hasRole("ADMIN")
           .pathMatchers(
            "/swagger-ui.html", 
                            "/swagger-ui/**", 
                            "/v3/api-docs/**", 
                            "/webjars/**",
                            "/docs/**"
            ).permitAll()
                .anyExchange().authenticated()
            )
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
    }
}
