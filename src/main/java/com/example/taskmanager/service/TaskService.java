package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskDTO> getTasksForUser(User user) {
        return taskRepository.findByUser(user).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO createTaskForUser(User user, TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());
        task.setUser(user);

        return toDTO(taskRepository.save(task));
    }

    public TaskDTO updateTask(User user, Long taskId, TaskDTO taskDTO, boolean isAdmin) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));

        if (!isAdmin && !task.getUser().getId().equals(user.getId())) {
            throw new SecurityException("No autorizado para modificar esta tarea");
        }

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());

        return toDTO(taskRepository.save(task));
    }

    public void deleteTask(User user, Long taskId, boolean isAdmin) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));

        if (!isAdmin && !task.getUser().getId().equals(user.getId())) {
            throw new SecurityException("No autorizado para eliminar esta tarea");
        }

        taskRepository.delete(task);
    }

    private TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted()
        );
    }
}
