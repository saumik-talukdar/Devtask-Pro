package com.saumik.devtask_pro.dto.response;

import com.saumik.devtask_pro.enums.TaskPriority;
import com.saumik.devtask_pro.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private TaskPriority priority;
}
