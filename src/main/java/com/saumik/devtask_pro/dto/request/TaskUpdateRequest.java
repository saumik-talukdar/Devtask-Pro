package com.saumik.devtask_pro.dto.request;

import com.saumik.devtask_pro.enums.TaskPriority;
import com.saumik.devtask_pro.enums.TaskStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

