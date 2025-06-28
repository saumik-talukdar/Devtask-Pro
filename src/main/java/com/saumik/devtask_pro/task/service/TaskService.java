package com.saumik.devtask_pro.task.service;

import com.saumik.devtask_pro.task.dto.request.StatusUpdateRequest;
import com.saumik.devtask_pro.task.dto.request.TaskCreateRequest;
import com.saumik.devtask_pro.task.dto.request.TaskUpdateRequest;
import com.saumik.devtask_pro.task.dto.response.TaskResponse;
import com.saumik.devtask_pro.task.entity.Task;
import com.saumik.devtask_pro.user.entity.User;
import com.saumik.devtask_pro.exception.ResourceNotFoundException;
import com.saumik.devtask_pro.task.mapper.TaskMapper;
import com.saumik.devtask_pro.task.repository.TaskRepository;
import com.saumik.devtask_pro.user.repository.UserRepository;
import com.saumik.devtask_pro.user.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.saumik.devtask_pro.task.mapper.TaskMapper.toEntity;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponse> getAllTasks(){
        User currentUser = getCurrentUser();
        return taskRepository.findByOwner(currentUser).stream()
                .map(TaskMapper::toResponse)
                .toList();
    }


    public List<TaskResponse> getAssignedTasks() {
        return taskRepository.findByAssignedTo(getCurrentUser()).stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {
        return TaskMapper.toResponse(getTaskEntityById(id));
    }

    public TaskResponse createTask(TaskCreateRequest req) {
        User owner = getCurrentUser();
        User assignee = getAssigneeFromRequest(req.getAssignee(), owner);

        Task task = TaskMapper.toEntity(req, owner, assignee);

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse updateTask(Long id, TaskUpdateRequest req) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();

          // no  need maybe (owner can do whatever)
//        if (task.getAssignedTo() == null || !task.getOwner().getId().equals(currentUser.getId())) {
//            throw new RuntimeException("Only owner can update full task");
//        }

        User assignee = getAssigneeFromRequest(req.getAssignee(), currentUser);
        task = TaskMapper.updateTaskFromRequest(req,task,assignee);


        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse updateTaskStatus(Long id, @Valid StatusUpdateRequest req) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();

        if (!task.getAssignedTo().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only assignee can update task status");
        }

        task.setStatus(req.getStatus());
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();

        if (!task.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only owner can delete this task");
        }

        taskRepository.delete(task);
    }

    // === Private Helper Methods ===

    private Task getTaskEntityById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found", id));
    }

    private User getCurrentUser() {
        return ((CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).getUserEntity();
    }

    private User getUserByUsernameOrEmail(String input) {
        return userRepository.findByUsernameOrEmail(input, input)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + input));
    }

    private User getAssigneeFromRequest(String assigneeInput, User defaultUser) {
        if (assigneeInput == null || assigneeInput.isBlank()) return defaultUser;
        return getUserByUsernameOrEmail(assigneeInput);
    }
}

