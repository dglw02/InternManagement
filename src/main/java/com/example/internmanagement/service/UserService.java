package com.example.internmanagement.service;

import com.example.internmanagement.entity.Project;
import com.example.internmanagement.entity.User;
import com.example.internmanagement.exception.ErrorException;
import com.example.internmanagement.repository.ProjectRepository;
import com.example.internmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.transaction.Transactional;
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
        Optional<User> checkIfUserWithIdExist = userRepository.findUserById(user.getId());
        user.setEnabled(true);
        if (checkIfUserWithIdExist != null) {
            throw new ErrorException("Account already exit.");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUser() {
        List<User> users =  userRepository.findAll();
        if (users.isEmpty()) {
            throw new ErrorException("User not found.");
        }
        return users;
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new ErrorException("user with id: " + id + " does not exist."));
    }

    @Transactional
    public User updateUserById(User user, Long id) {
        User updateUser = new User();

        Optional<User> userDB = userRepository.findUserById(id);

        if (userDB.isEmpty()) {
            throw new ErrorException("User with id: " + id + " does not exist.");
        }

        if (StringUtils.isEmpty(user.getFirstName()) && !Objects.equals(user.getFirstName(), userDB.get().getFirstName())) {
            updateUser.setFirstName(user.getFirstName());
        } else {
            updateUser.setFirstName(userDB.get().getFirstName());
        }

        if (StringUtils.isEmpty(user.getLastName()) && !Objects.equals(user.getLastName(), userDB.get().getLastName())) {
            updateUser.setLastName(user.getLastName());
        } else {
            updateUser.setLastName(userDB.get().getLastName());
        }

        if (StringUtils.isEmpty(user.getEmail()) && !Objects.equals(user.getEmail(), userDB.get().getEmail())) {
            updateUser.setEmail(user.getEmail());
        } else {
            updateUser.setEmail(userDB.get().getEmail());
        }

        updateUser.setId(id);
        return userRepository.save(updateUser);
    }


    public void deleteUserById(Long id) {
        if (id == 0) {
            throw new ErrorException("You need to provide ID of user to be deleted. ID can not be 0.");
        }
        Optional<User> checkIfStudentWithIdExist = userRepository.findById(id);
        if (checkIfStudentWithIdExist.isEmpty()) {
            throw new ErrorException(
                    "User can not be deleted because user with id: " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }


    public Project createProjectForUser(Long id, Project project) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new ErrorException("User with id: " + id + " does not exist.");
        }
        project.setUser(user.get());
        Project savedProject = projectRepository.save(project);
        return savedProject;
    }


    public List<Project> getProjectsByIdForUserById(Long id) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new ErrorException("User with id: " + id + " does not exist.");
        }
        return user.get().getProjects();
    }
}
