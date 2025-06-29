package com.saumik.devtask_pro.config;

import com.saumik.devtask_pro.enums.TaskPriority;
import com.saumik.devtask_pro.enums.TaskStatus;
import com.saumik.devtask_pro.enums.Visibility;
import com.saumik.devtask_pro.task.entity.Task;
import com.saumik.devtask_pro.task.repository.TaskRepository;
import com.saumik.devtask_pro.user.entity.User;
import com.saumik.devtask_pro.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    public CommandLineRunner initDatabase(TaskRepository taskRepository, UserRepository userRepository) {
        return args -> {

            // Ensure default user exists
            User defaultOwner = userRepository.findByEmail("admin@example.com")
                    .orElseGet(() -> userRepository.save(User.builder()
                            .username("Admin User")
                            .email("admin@example.com")
                            .password("securepassword") // ideally should be encoded
                            .build()));

            // Preload sample tasks
            taskRepository.save(Task.builder()
                    .title("Implement User Login Page")
                    .description("Design and develop the frontend UI for user authentication.")
                    .status(TaskStatus.IN_PROGRESS)
                    .dueDate(LocalDate.of(2025, 6, 25))
                    .priority(TaskPriority.HIGH)
                    .owner(defaultOwner)
                    .build());

            taskRepository.save(Task.builder()
                    .title("Refactor Task Service Logic")
                    .description("Improve readability and efficiency of task CRUD operations.")
                    .status(TaskStatus.IN_PROGRESS)
                    .dueDate(LocalDate.of(2025, 6, 18))
                    .priority(TaskPriority.MEDIUM)
                    .owner(defaultOwner)
                    .build());

            taskRepository.save(Task.builder()
                    .title("Set Up H2 Database Configuration")
                    .description("Verify connection and console access for development environment.")
                    .status(TaskStatus.IN_PROGRESS)
                    .dueDate(LocalDate.of(2025, 6, 8))
                    .priority(TaskPriority.LOW)
                    .owner(defaultOwner)
                    .build());

            taskRepository.save(Task.builder()
                    .title("Research JWT Implementation for Spring Security")
                    .description("Explore best practices and libraries for token-based authentication.")
                    .status(TaskStatus.IN_PROGRESS)
                    .dueDate(LocalDate.of(2025, 7, 5))
                    .priority(TaskPriority.CRITICAL)
                    .owner(defaultOwner)
                    .build());

            taskRepository.save(Task.builder()
                    .title("Write Unit Tests for TaskService")
                    .description("Develop comprehensive tests for task creation, retrieval, update, and deletion.")
                    .status(TaskStatus.IN_PROGRESS)
                    .dueDate(LocalDate.of(2025, 7, 1))
                    .priority(TaskPriority.HIGH)
                    .owner(defaultOwner)
                    .build());

            log.info("Preloaded sample tasks with owner: " + defaultOwner.getEmail());
        };
    }
}
