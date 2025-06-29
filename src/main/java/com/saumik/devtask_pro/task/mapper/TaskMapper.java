package com.saumik.devtask_pro.task.mapper;

import com.saumik.devtask_pro.task.dto.request.TaskCreateRequest;
import com.saumik.devtask_pro.task.dto.request.TaskUpdateRequest;
import com.saumik.devtask_pro.task.dto.response.TaskResponse;
import com.saumik.devtask_pro.task.entity.Task;
import com.saumik.devtask_pro.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {


    public static Task toEntity(TaskCreateRequest req, User owner,User assignee){
        Task task = Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .dueDate(req.getDueDate())
                .priority(req.getPriority())

                .assignedTo(assignee)
                .owner(owner)
                .build();
        return task;
    }

    public static Task updateTaskFromRequest(TaskUpdateRequest req, Task task,User assignee){
        if (req.getTitle() != null) task.setTitle(req.getTitle());
        if (req.getDescription() != null) task.setDescription(req.getDescription());

        if (req.getPriority() != null) task.setPriority(req.getPriority());
        if (req.getDueDate() != null) task.setDueDate(req.getDueDate());
        task.setAssignedTo(assignee);
        return task;
    }


    public static TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .priority(task.getPriority())
                .owner(task.getOwner() != null ? task.getOwner().getUsername() : null)
                .assignee(task.getAssignedTo() != null ? task.getAssignedTo().getUsername() : null)
                .build();
    }

}
