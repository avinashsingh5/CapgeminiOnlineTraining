package com.shopsphere.catalog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//trust the gateway headers
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserHeaderAuthFilter userHeaderAuthFilter;

    public SecurityConfig(UserHeaderAuthFilter userHeaderAuthFilter) {
        this.userHeaderAuthFilter = userHeaderAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/catalog/products/reduce-stock").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/catalog/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(userHeaderAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
