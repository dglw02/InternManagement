package com.example.internmanagement.controller;


import com.example.internmanagement.entity.User;
import com.example.internmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> user = userService.getAllUser();
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        User newUser = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/find/id/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable("userId") Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable("userId") Long id) {
        User updated = userService.updateUserById(user, id);
        return ResponseEntity.accepted().body(updated);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}