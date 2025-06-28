package com.saumik.devtask_pro.task.dto.request;

import com.saumik.devtask_pro.enums.TaskStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateRequest {

    @NotNull
    @Valid
    private TaskStatus status;
}
