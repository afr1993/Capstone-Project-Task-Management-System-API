package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    private User getCurrentUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    private boolean isAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(@AuthenticationPrincipal UserDetails userDetails) {
        if (isAdmin(userDetails)) {
            return ResponseEntity.ok(taskService.getAllTasks());
        } else {
            User user = getCurrentUser(userDetails);
            return ResponseEntity.ok(taskService.getTasksForUser(user));
        }
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody TaskDTO taskDTO) {
        User user = getCurrentUser(userDetails);
        TaskDTO created = taskService.createTaskForUser(user, taskDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long id,
                                              @RequestBody TaskDTO taskDTO) {
        User user = getCurrentUser(userDetails);
        boolean admin = isAdmin(userDetails);
        TaskDTO updated = taskService.updateTask(user, id, taskDTO, admin);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable Long id) {
        User user = getCurrentUser(userDetails);
        boolean admin = isAdmin(userDetails);
        taskService.deleteTask(user, id, admin);
        return ResponseEntity.ok("Tarea eliminada");
    }
}
