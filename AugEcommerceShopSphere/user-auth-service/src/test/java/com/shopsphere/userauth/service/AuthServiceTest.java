package com.shopsphere.userauth.service;

import com.shopsphere.userauth.dto.LoginRequest;
import com.shopsphere.userauth.dto.SignupRequest;
import com.shopsphere.userauth.entity.User;
import com.shopsphere.userauth.entity.Role;
import com.shopsphere.userauth.exception.ApiException;
import com.shopsphere.userauth.repository.UserRepository;
import com.shopsphere.userauth.security.JwtTokenProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    AuthService authService;

    SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setEmail("test@gmail.com");
        signupRequest.setPassword("123456");
        signupRequest.setFullName("Test User");
    }

    @Test
    void signup_success() {

        when(userRepository.existsByEmail(anyString()))
                .thenReturn(false);

        when(passwordEncoder.encode(anyString()))
                .thenReturn("encoded");

        when(userRepository.save(any(User.class)))
                .thenAnswer(i -> {
                    User user = i.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        when(jwtTokenProvider.generateToken(
                anyLong(),
                anyString(),
                any(Role.class)))
                .thenReturn("token");

        var response = authService.signup(signupRequest);

        assertEquals("token", response.getToken());
        assertEquals("test@gmail.com", response.getEmail());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void signup_emailAlreadyExists() {

        when(userRepository.existsByEmail(anyString()))
                .thenReturn(true);

        assertThrows(
                ApiException.class,
                () -> authService.signup(signupRequest)
        );

        verify(userRepository, never())
                .save(any(User.class));
    }

    @Test
    void login_userNotFound() {

        LoginRequest request = new LoginRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("123456");

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(
                ApiException.class,
                () -> authService.login(request)
        );
    }
}