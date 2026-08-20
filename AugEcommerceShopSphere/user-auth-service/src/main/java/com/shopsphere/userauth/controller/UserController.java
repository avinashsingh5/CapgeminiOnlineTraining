package com.shopsphere.userauth.controller;

import com.shopsphere.userauth.dto.UpdateProfileRequest;
import com.shopsphere.userauth.entity.User;
import com.shopsphere.userauth.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public User getProfile(@PathVariable Long id) {
        return userService.getProfile(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public User updateProfile(@PathVariable Long id, @RequestBody UpdateProfileRequest request) {
        return userService.updateProfile(id, request);
    }
}














