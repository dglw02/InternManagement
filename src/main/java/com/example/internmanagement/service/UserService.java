package com.example.internmanagement.service;

import com.example.internmanagement.entity.User;
import com.example.internmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUser() {
        List<User> users =  userRepository.findAll();
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }
        return users;
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user with id: " + id + " does not exist."));
    }

    public User updateUserById(User user, Long id) {
        Optional<User> userData = userRepository.findUserById(id);
        if (userData.isPresent()){
            User users = userData.get();
            users.getId();
            users.setFirstName(user.getFirstName());
            users.setLastName(users.getLastName());
            users.setEmail(user.getEmail());
            users.setEnabled(user.isEnabled());
            users.setRole(user.getRole());
            users.getPassword();
            return userRepository.save(users);
        }
        else {
            return null;
        }
    }


    public void deleteUserById(Long id) {
        if (id == 0) {
            throw new UsernameNotFoundException("You need to provide ID of user to be deleted. ID can not be 0.");
        }
        Optional<User> checkIfStudentWithIdExist = userRepository.findById(id);
        if (checkIfStudentWithIdExist.isEmpty()) {
            throw new UsernameNotFoundException(
                    "User can not be deleted because user with id: " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }
}
