package com.saumik.devtask_pro.task.repository;

import com.saumik.devtask_pro.enums.TaskStatus;
import com.saumik.devtask_pro.enums.Visibility;
import com.saumik.devtask_pro.user.entity.User;
import com.saumik.devtask_pro.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedTo(User user);

    List<Task> findByOwner(User user);

    List<Task> findByVisibility(Visibility visibility);

    List<Task> findByAssignedToAndStatusAndVisibility(User assignedTo, TaskStatus status, Visibility visibility);
    List<Task> findByAssignedToAndStatus(User assignee, TaskStatus status);

}
