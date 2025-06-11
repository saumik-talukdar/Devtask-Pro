package com.saumik.devtask_pro.service;

import com.saumik.devtask_pro.dto.request.TaskRequest;
import com.saumik.devtask_pro.dto.response.TaskResponse;
import com.saumik.devtask_pro.entity.Task;
import com.saumik.devtask_pro.exception.ResourceNotFoundException;
import com.saumik.devtask_pro.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> getAllTasks(){
        return taskRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id){
        Task task = getTaskEntityById(id);
        return mapToResponse(task);
    }

    public TaskResponse createTask(TaskRequest req){
        Task task = Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .status(req.getStatus())
                .dueDate(req.getDueDate())
                .priority(req.getPriority())
                .build();
        return mapToResponse(taskRepository.save(task));
    }

    public TaskResponse updateTask(Long id,TaskRequest updatedTask){
        Task existingTask = getTaskEntityById(id);
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setDueDate(updatedTask.getDueDate());
        return mapToResponse(taskRepository.save(existingTask));
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

    public Task getTaskEntityById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task not found",id));
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .build();
    }
}
