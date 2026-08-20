package com.shopsphere.userauth.service;

import com.shopsphere.userauth.dto.UpdateProfileRequest;
import com.shopsphere.userauth.entity.User;
import com.shopsphere.userauth.exception.ApiException;
import com.shopsphere.userauth.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public User getProfile(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
    }

    public User updateProfile(Long id, UpdateProfileRequest request) {
        User user = getProfile(id);
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        return userRepository.save(user);
    }
}
