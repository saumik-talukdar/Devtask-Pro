package com.saumik.devtask_pro.controller;

import com.saumik.devtask_pro.dto.request.TaskCreateRequest;
import com.saumik.devtask_pro.dto.request.TaskUpdateRequest;
import com.saumik.devtask_pro.dto.response.TaskResponse;
import com.saumik.devtask_pro.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PostMapping
    public TaskResponse createTask(@RequestBody @Valid TaskCreateRequest task){
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,@RequestBody @Valid TaskUpdateRequest task){
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }


}