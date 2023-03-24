package com.example.internmanagement.service;

import com.example.internmanagement.entity.User;
import com.example.internmanagement.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
