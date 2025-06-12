package com.saumik.devtask_pro.mapper;

import com.saumik.devtask_pro.dto.request.TaskCreateRequest;
import com.saumik.devtask_pro.dto.response.TaskResponse;
import com.saumik.devtask_pro.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {


    public static Task toEntity(TaskCreateRequest req){
        Task task = Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .dueDate(req.getDueDate())
                .priority(req.getPriority())
                .status(req.getStatus())
                .build();
        return task;
    }

    public static Task toEntity(TaskResponse res){
        Task task = Task.builder()
                .id(res.getId())
                .title(res.getTitle())
                .description(res.getDescription())
                .dueDate(res.getDueDate())
                .priority(res.getPriority())
                .status(res.getStatus())
                .build();
        return task;
    }

    public static TaskResponse toResponse(TaskCreateRequest req){
        TaskResponse res = TaskResponse.builder()
                .id(-1L) // dummy, cause req not contain id
                .title(req.getTitle())
                .description(req.getDescription())
                .dueDate(req.getDueDate())
                .status(req.getStatus())
                .priority(req.getPriority())
                .build();
        return res;
    }

    public static TaskResponse toResponse(Task task){
        TaskResponse res = TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .priority(task.getPriority())
                .build();
        return res;
    }

    public static TaskCreateRequest toRequest(TaskResponse res){
        TaskCreateRequest req = TaskCreateRequest.builder()
                .title(res.getTitle())
                .description(res.getDescription())
                .dueDate(res.getDueDate())
                .status(res.getStatus())
                .priority(res.getPriority())
                .build();
        return req;
    }

    public static TaskCreateRequest toRequest(Task task){
        TaskCreateRequest req = TaskCreateRequest.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .priority(task.getPriority())
                .build();
        return req;
    }

}
