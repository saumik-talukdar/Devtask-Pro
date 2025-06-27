package com.saumik.devtask_pro.task.dto.request;

import com.saumik.devtask_pro.enums.TaskPriority;
import com.saumik.devtask_pro.enums.TaskStatus;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import com.saumik.devtask_pro.validation.EnumValue;

@Data
@Builder
public class TaskUpdateRequest {

    private String title;
    private String description;

    private TaskPriority priority;

    private TaskStatus status;

    private LocalDate dueDate;

}

