package com.example.taskmanager.controller;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public List<UserDTO> getAllUsersWithTaskCount() {
        return userService.getAllUsersWithTaskCount();
    }
}
