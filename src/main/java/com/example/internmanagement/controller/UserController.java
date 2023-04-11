package com.example.internmanagement.controller;


import com.example.internmanagement.dto.CreateUserDto;
import com.example.internmanagement.dto.UserDto;
import com.example.internmanagement.entity.Project;
import com.example.internmanagement.entity.User;
import com.example.internmanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> user = userService.getAllUser().stream().map(user1 -> modelMapper.map(user1, UserDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(user);
    }
    //saveUser ko dùng đến,dùng registerRequest
    @PostMapping()
    public ResponseEntity<CreateUserDto> saveUser(@RequestBody CreateUserDto createUserDto) {
        User userRequest = modelMapper.map(createUserDto, User.class);
        User user = userService.saveUser(userRequest);
        CreateUserDto userResponse = modelMapper.map(user, CreateUserDto.class);
        return new ResponseEntity(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/find/id/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("userId") Long id) {
        User user = userService.findUserById(id);
        UserDto useResponse = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok().body(useResponse);
    }

//Có lỗi trong updateUser
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUserById(@RequestBody UserDto userDto, @PathVariable("userId") Long id) {

        User userRequest = modelMapper.map(userDto, User.class);
        User updated = userService.updateUserById(userRequest, id);
        UserDto userResponse = modelMapper.map(updated, UserDto.class);
        return ResponseEntity.accepted().body(userResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{userId}/projects")
    public ResponseEntity<List<Project>> createProjectForUser(@PathVariable("studentId") Long id,
                                                                 @Valid @RequestBody Project project) {
        Project savedProject = userService.createProjectForUser(id, project);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProject.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{userId}/projects")
    public ResponseEntity<List<Project>> getProjectsByIdForUserById(@PathVariable("userId") Long id) {
        List<Project> projects = userService.getProjectsByIdForUserById(id);
        return ResponseEntity.ok(projects);
    }
}