package com.saumik.devtask_pro.task.controller;

import com.saumik.devtask_pro.task.dto.request.StatusUpdateRequest;
import com.saumik.devtask_pro.task.dto.request.TaskCreateRequest;
import com.saumik.devtask_pro.task.dto.request.TaskUpdateRequest;
import com.saumik.devtask_pro.task.dto.response.TaskResponse;
import com.saumik.devtask_pro.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PutMapping("/{id}/submit-review")
    public TaskResponse submitTaskForReview(@PathVariable Long id) {
        return taskService.submitTaskForReview(id);
    }

    @PutMapping("/{id}/review/accept")
    public TaskResponse acceptSubmittedTask(@PathVariable Long id) {
        return taskService.acceptSubmittedTask(id);
    }

    @PutMapping("/{id}/review/reject")
    public TaskResponse rejectSubmittedTask(@PathVariable Long id) {
        return taskService.rejectSubmittedTask(id);
    }

    @GetMapping("/rejected")
    public List<TaskResponse> getRejectedTasks() {
        return taskService.getRejectedTasks();
    }




    @GetMapping("/requested")
    public List<TaskResponse> getRequestedTasks() {
        return taskService.getRequestedTasks();
    }

    @PutMapping("/{id}/accept-requested")
    public TaskResponse acceptRequestedTask(@PathVariable Long id) {
        return taskService.acceptRequestedTask(id);
    }

    @PutMapping("/{id}/reject")
    public TaskResponse rejectRequestedTask(@PathVariable Long id) {
        return taskService.rejectRequestedTask(id);
    }

    @PutMapping("/{id}/make-private")
    public TaskResponse makeTaskPrivate(@PathVariable Long id) {
        return taskService.makeTaskPrivate(id);
    }

    @PutMapping("/{id}/make-public")
    public TaskResponse makeTaskPublic(@PathVariable Long id) {
        return taskService.makeTaskPublic(id);
    }


    @GetMapping("/public")
    public List<TaskResponse> getPublicTasks() {
        return taskService.getPublicTasks();
    }

    @PutMapping("/public/{id}/accept")
    public TaskResponse acceptPublicTask(@PathVariable Long id) {
        return taskService.acceptPublicTask(id);
    }

    @GetMapping
    public List<TaskResponse> getMyTasks(){
        return taskService.getMyTasks();
    }

    @GetMapping("/assigned")
    public List<TaskResponse> getAssignedTasks(){
        return taskService.getAssignedTasks();
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

    @PutMapping("/assigned/{id}")
    public TaskResponse updateTaskStatus(@PathVariable Long id,@RequestBody @Valid StatusUpdateRequest task){
        return taskService.updateTaskStatus(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }


}