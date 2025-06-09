package com.saumik.devtask_pro.controller;

import com.saumik.devtask_pro.entity.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @GetMapping("/dummy")
    public Task getDummyTask() {
        return Task.builder()
                .id(1L)
                .title("Dummy Task")
                .description("This is just a dummy task")
                .status("IN_PROGRESS")
                .build();
    }
}