package com.example.internmanagement.service;

import com.example.internmanagement.entity.Project;
import com.example.internmanagement.entity.User;
import com.example.internmanagement.exception.ErrorException;
import com.example.internmanagement.repository.ProjectRepository;
import com.example.internmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

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

    public Project createProjectForUser(Long id, Project project) {
        Optional<User> student = userRepository.findUserById(id);
        if (student.isEmpty()) {
            throw new ErrorException("Student with id: " + id + " does not exist.");
        }
        project.setUser(student.get());
        Project savedProject = projectRepository.save(project);
        return savedProject;
    }

    public List<Project> getProjectsByIdForUserById(Long id) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new ErrorException("Student with id: " + id + " does not exist.");
        }
        return user.get().getProjects();
    }
}
