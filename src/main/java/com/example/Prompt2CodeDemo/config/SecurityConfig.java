package com.example.Prompt2CodeDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http.csrf().disable();
        http.authorizeHttpRequests(auth -> auth
            // Public FAQ endpoints (knowledge base)
            .requestMatchers("/api/faqs", "/api/faqs/{id}", "/api/faqs/{id}/view", 
                           "/api/faqs/published", "/api/faqs/answered", "/api/faqs/category/**",
                           "/api/faqs/status/**", "/api/faqs/categories", "/api/faqs/labels",
                           "/api/faqs/by-labels", "/api/faqs/search").permitAll()
            // FAQ creation - EXTERNAL users only
            .requestMatchers("/api/faqs").hasRole("EXTERNAL")
            // FAQ management - INTERNAL users with specific roles
            .requestMatchers("/api/faqs/{id}/answer", "/api/faqs/pending", 
                           "/api/faqs/{id}", "DELETE /api/faqs/{id}").hasAnyRole("UKNF_EMPLOYEE", "UKNF_SYSTEM_ADMINISTRATOR")
            // All other requests
            .anyRequest().permitAll());
        return http.build();
    }
}
