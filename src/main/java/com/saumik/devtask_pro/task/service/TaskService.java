package com.saumik.devtask_pro.task.service;

import com.saumik.devtask_pro.enums.TaskStatus;
import com.saumik.devtask_pro.enums.Visibility;
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
import java.util.stream.Collectors;

import static com.saumik.devtask_pro.task.mapper.TaskMapper.toEntity;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponse> getMyTasks() {
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
        if (assignee.getId().equals(owner.getId())) {
            task.setStatus(TaskStatus.TODO);
        } else {
            task.setStatus(TaskStatus.PENDING_ACCEPTANCE);
        }
        ensureNotCompleted(task);
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse updateTask(Long id, TaskUpdateRequest req) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();

        ensureNotCompleted(task);
        User assignee = getAssigneeFromRequest(req.getAssignee(), currentUser);
        if (assignee.getId().equals(currentUser.getId()) &&
                (req.getStatus().equals(TaskStatus.PENDING_ACCEPTANCE) ||
                        req.getStatus().equals(TaskStatus.SUBMITTED_FOR_REVIEW))) {
            throw new RuntimeException("Invalid status");
        } else if (task.getStatus().canTransitionTo(req.getStatus())) {
            task.setStatus(req.getStatus());
        } else {
            throw new RuntimeException("Invalid status");
        }
        task = TaskMapper.updateTaskFromRequest(req, task, assignee);


        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse updateTaskStatus(Long id, @Valid StatusUpdateRequest req) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();

        ensureNotCompleted(task);
        User assignee = task.getAssignedTo();
        if (assignee.getId().equals(currentUser.getId()) &&
                (req.getStatus().equals(TaskStatus.PENDING_ACCEPTANCE) ||
                        req.getStatus().equals(TaskStatus.SUBMITTED_FOR_REVIEW))) {
            throw new RuntimeException("Invalid status");
        } else if (task.getStatus().canTransitionTo(req.getStatus())) {
            task.setStatus(req.getStatus());
        } else {
            throw new RuntimeException("Invalid status");
        }

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();
        ensureNotCompleted(task);
        if (!task.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only owner can delete this task");
        }

        taskRepository.delete(task);
    }


    public List<TaskResponse> getPublicTasks() {
        return taskRepository.findByVisibility(Visibility.PUBLIC).stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    public TaskResponse acceptPublicTask(Long id) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();
        ensureNotCompleted(task);
        if (!task.getOwner().getId().equals(currentUser.getId())) {
            task.setAssignedTo(currentUser);
            task.setVisibility(Visibility.PRIVATE);
            task.setStatus(TaskStatus.IN_PROGRESS);
            return TaskMapper.toResponse(taskRepository.save(task));
        }else{
            throw new RuntimeException("You are not allowed to accept this task");
        }
    }

    public TaskResponse makeTaskPublic(Long id) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();
        ensureNotCompleted(task);
        if (!task.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only owner can make this task public");
        }
        if (!task.getAssignedTo().getId().equals(task.getOwner().getId())) {
            throw new RuntimeException("Only unaccepted tasks can be made public");
        }

        task.setVisibility(Visibility.PUBLIC);
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public List<TaskResponse> getRequestedTasks() {
        User currentUser = getCurrentUser();
        return taskRepository
                .findByAssignedToAndStatusAndVisibility(
                        currentUser,
                        TaskStatus.PENDING_ACCEPTANCE,
                        Visibility.PRIVATE
                )
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    public TaskResponse acceptRequestedTask(Long id) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();

        ensureNotCompleted(task);
        if (!task.getAssignedTo().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only assignee can accept the task");
        }

        if (task.getStatus() != TaskStatus.PENDING_ACCEPTANCE) {
            throw new RuntimeException("Task is not awaiting acceptance");
        }

        task.setStatus(TaskStatus.IN_PROGRESS); // or ACCEPTED if you kept that
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse rejectRequestedTask(Long id) {
        Task task = getTaskEntityById(id);
        ensureNotCompleted(task);
        task.setStatus(TaskStatus.TODO);
        task.setAssignedTo(task.getOwner());
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse makeTaskPrivate(Long id) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();
        ensureNotCompleted(task);
        if (!task.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only owner can make this task public");
        }
        task.setVisibility(Visibility.PRIVATE);
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse submitTaskForReview(Long id) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();
        ensureNotCompleted(task);
        if(!task.getAssignedTo().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized Request");
        }
        task.setStatus(TaskStatus.SUBMITTED_FOR_REVIEW);
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse acceptSubmittedTask(Long id) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();
        ensureNotCompleted(task);
        if(task.getAssignedTo().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not allowed to accept this task");
        }
        if(!task.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only owner can accept the task");
        }
        if(!task.getStatus().canTransitionTo(TaskStatus.COMPLETED)){
            throw new RuntimeException("Invalid task status");
        }
        task.setStatus(TaskStatus.COMPLETED);
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse rejectSubmittedTask(Long id) {
        Task task = getTaskEntityById(id);
        User currentUser = getCurrentUser();
        ensureNotCompleted(task);
        if(task.getAssignedTo().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not allowed to reject this task");
        }
        if(!task.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only owner can reject the task");
        }
        if(!task.getStatus().canTransitionTo(TaskStatus.REJECTED)){
            throw new RuntimeException("Invalid task status");
        }
        task.setStatus(TaskStatus.REJECTED);
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public List<TaskResponse> getRejectedTasks() {
        User currentUser = getCurrentUser();

        return taskRepository
                .findByAssignedToAndStatus(currentUser, TaskStatus.REJECTED)
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
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

    private void ensureNotCompleted(Task task) {
        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new RuntimeException("Cannot modify a completed task");
        }
    }



}