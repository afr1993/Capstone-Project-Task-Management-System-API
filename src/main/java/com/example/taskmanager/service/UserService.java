package com.example.taskmanager.service;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.entity.Role;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.RoleRepository;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String roleName) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(role));

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserDTO> getAllUsersWithTaskCount() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            long taskCount = taskRepository.countByUser(user);
            return new UserDTO(user.getId(), user.getUsername(), user.getRoles(), (int) taskCount);
        }).collect(Collectors.toList());
    }

}
