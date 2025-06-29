package com.saumik.devtask_pro.enums;
import java.util.EnumSet;
import java.util.Set;

public enum TaskStatus {
    PENDING_ACCEPTANCE,   // For tasks awaiting assignee approval
    TODO,                 // For self-assigned tasks
    IN_PROGRESS,
    PAUSED,
    SUBMITTED_FOR_REVIEW,
    REJECTED,
    COMPLETED;

    private Set<TaskStatus> allowedNextStatuses;

    static {
        PENDING_ACCEPTANCE.allowedNextStatuses = EnumSet.of(IN_PROGRESS); // Only valid when accepted
        TODO.allowedNextStatuses = EnumSet.of(IN_PROGRESS, PAUSED,PENDING_ACCEPTANCE);       // For owner/self-assigned
        IN_PROGRESS.allowedNextStatuses = EnumSet.of(PAUSED, SUBMITTED_FOR_REVIEW, COMPLETED);
        PAUSED.allowedNextStatuses = EnumSet.of(IN_PROGRESS);
        SUBMITTED_FOR_REVIEW.allowedNextStatuses = EnumSet.of(REJECTED, COMPLETED, IN_PROGRESS);
        REJECTED.allowedNextStatuses = EnumSet.of(IN_PROGRESS);
        COMPLETED.allowedNextStatuses = EnumSet.noneOf(TaskStatus.class); // No transitions allowed
    }

    public boolean canTransitionTo(TaskStatus next) {
        return allowedNextStatuses.contains(next);
    }
}



/*
If owner is doing by himself then there only 3 valid state for him,
TODO, IN_PROGRESS, COMPLETED
All the other status is not valid for him.
He don't need to review for himself,no point(in future).
 */

