package com.shopsphere.userauth.config;


import com.shopsphere.userauth.entity.User;
import com.shopsphere.userauth.enums.Role;
import com.shopsphere.userauth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder {

    @Bean
    CommandLineRunner seedAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (!userRepository.existsByEmail("admin@shopsphere.com")) {

                User admin = new User();
                admin.setFullName("Admin");
                admin.setEmail("admin@shopsphere.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);
            }
        };
    }
}