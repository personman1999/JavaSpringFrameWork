package com.cybersoft.demospringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/demo", "demo/hello").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/product").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/product").hasAnyRole("ADMIN");
                    request.requestMatchers(HttpMethod.DELETE, "/product").permitAll();
                    request.anyRequest().authenticated();
                }).build();
    }
}
