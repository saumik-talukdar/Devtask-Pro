package com.saumik.devtask_pro.service;

import com.saumik.devtask_pro.dto.request.TaskCreateRequest;
import com.saumik.devtask_pro.dto.request.TaskUpdateRequest;
import com.saumik.devtask_pro.dto.response.TaskResponse;
import com.saumik.devtask_pro.entity.Task;
import com.saumik.devtask_pro.exception.ResourceNotFoundException;
import com.saumik.devtask_pro.mapper.TaskMapper;
import com.saumik.devtask_pro.repository.TaskRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> getAllTasks(){
        return taskRepository.findAll().stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id){
        Task task = getTaskEntityById(id);
        return TaskMapper.toResponse(task);
    }

    public TaskResponse createTask(TaskCreateRequest req){

        try {
            Task task = Task.builder()
                    .title(req.getTitle())
                    .description(req.getDescription())
                    .status(req.getStatus())
                    .dueDate(req.getDueDate())
                    .priority(req.getPriority())
                    .build();
            return TaskMapper.toResponse(taskRepository.save(task));
        } catch (DataAccessException ex) {
            throw new RuntimeException("Failed to save task to database", ex);
        }


    }

    public TaskResponse updateTask(Long id, TaskUpdateRequest updatedTask){
        Task existingTask = getTaskEntityById(id);
        if(updatedTask.getDescription()!=null)existingTask.setDescription(updatedTask.getDescription());
        if(updatedTask.getTitle()!=null)existingTask.setTitle(updatedTask.getTitle());
        if(updatedTask.getStatus()!=null)existingTask.setStatus(updatedTask.getStatus());
        if(updatedTask.getPriority()!=null)existingTask.setPriority(updatedTask.getPriority());
        if(updatedTask.getDueDate()!=null)existingTask.setDueDate(updatedTask.getDueDate());
        return TaskMapper.toResponse(taskRepository.save(existingTask));
    }

    public void deleteTask(Long id) {
        Task task = getTaskEntityById(id); // throws exception if not found
        taskRepository.delete(task);
    }


    private Task getTaskEntityById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task not found",id));
    }


}
