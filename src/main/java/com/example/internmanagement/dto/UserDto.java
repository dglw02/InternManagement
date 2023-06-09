package com.example.internmanagement.dto;


import com.example.internmanagement.entity.Role;
import lombok.Data;

@Data
public class UserDto {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private Role role;
}
