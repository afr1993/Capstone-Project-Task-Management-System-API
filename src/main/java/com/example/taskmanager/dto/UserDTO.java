package com.example.taskmanager.dto;

import com.example.taskmanager.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private Set<Role> roles;
    private int taskCount;
}
