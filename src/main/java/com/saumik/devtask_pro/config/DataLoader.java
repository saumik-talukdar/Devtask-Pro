package com.saumik.devtask_pro.config; // You can put it in 'config' or 'util' package

import com.saumik.devtask_pro.entity.Task;
import com.saumik.devtask_pro.enums.TaskPriority;
import com.saumik.devtask_pro.enums.TaskStatus;
import com.saumik.devtask_pro.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Configuration // Mark this class as a Spring configuration class
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Bean // This method creates and registers a bean
    public CommandLineRunner initDatabase(TaskRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(Task.builder()
                    .title("Implement User Login Page")
                    .description("Design and develop the frontend UI for user authentication.")
                    .status(TaskStatus.TO_DO)
                    .dueDate(LocalDate.of(2025, 6, 25)) // June 25, 2025
                    .priority(TaskPriority.HIGH)
                    .build()));

            log.info("Preloading " + repository.save(Task.builder()
                    .title("Refactor Task Service Logic")
                    .description("Improve readability and efficiency of task CRUD operations.")
                    .status(TaskStatus.IN_PROGRESS)
                    .dueDate(LocalDate.of(2025, 6, 18)) // June 18, 2025
                    .priority(TaskPriority.MEDIUM)
                    .build()));

            log.info("Preloading " + repository.save(Task.builder()
                    .title("Set Up H2 Database Configuration")
                    .description("Verify connection and console access for development environment.")
                    .status(TaskStatus.DONE)
                    .dueDate(LocalDate.of(2025, 6, 8)) // June 8, 2025
                    .priority(TaskPriority.LOW)
                    .build()));

            log.info("Preloading " + repository.save(Task.builder()
                    .title("Research JWT Implementation for Spring Security")
                    .description("Explore best practices and libraries for token-based authentication.")
                    .status(TaskStatus.BLOCKED) // This task is blocked for now
                    .dueDate(LocalDate.of(2025, 7, 5)) // July 5, 2025
                    .priority(TaskPriority.CRITICAL)
                    .build()));

            log.info("Preloading " + repository.save(Task.builder()
                    .title("Write Unit Tests for TaskService")
                    .description("Develop comprehensive tests for task creation, retrieval, update, and deletion.")
                    .status(TaskStatus.TO_DO)
                    .dueDate(LocalDate.of(2025, 7, 1)) // July 1, 2025
                    .priority(TaskPriority.HIGH)
                    .build()));
        };
    }
}