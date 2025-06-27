package com.saumik.devtask_pro.task.dto.response;

import com.saumik.devtask_pro.enums.TaskPriority;
import com.saumik.devtask_pro.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskResponse {
    @NotNull(message = "id is required")
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "DueDate is required")
    private LocalDate dueDate;
    @NotNull(message = "Status is required")
    private TaskStatus status;
    @NotNull(message = "Priority is required")
    private TaskPriority priority;
}
