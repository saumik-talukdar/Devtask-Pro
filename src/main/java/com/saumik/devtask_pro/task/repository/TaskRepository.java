package com.saumik.devtask_pro.task.repository;

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
}
