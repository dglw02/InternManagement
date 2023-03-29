package com.example.internmanagement.service;

import com.example.internmanagement.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService{

    List<User> getAllUsers();

    User saveUser(User user);

    User getUserById(Long id);

    User updateUser(User user);

    void deleteUserById(Long id);
}
