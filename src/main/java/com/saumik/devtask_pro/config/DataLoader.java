package com.saumik.devtask_pro.config; // You can put it in 'config' or 'util' package

import com.saumik.devtask_pro.entity.Task;
import com.saumik.devtask_pro.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration // Mark this class as a Spring configuration class
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Bean // This method creates and registers a bean
    public CommandLineRunner initDatabase(TaskRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(Task.builder()
                    .title("Develop User Authentication")
                    .description("Implement user registration, login, and password reset functionalities.")
                    .status("IN_PROGRESS")
                    .build()));

            log.info("Preloading " + repository.save(Task.builder()
                    .title("Design Database Schema")
                    .description("Define tables and relationships for tasks, users, and projects.")
                    .status("TO_DO")
                    .build()));

            log.info("Preloading " + repository.save(Task.builder()
                    .title("Set up CI/CD Pipeline")
                    .description("Configure Jenkins/GitLab CI for automated builds and deployments.")
                    .status("DONE")
                    .build()));

            log.info("Preloading " + repository.save(Task.builder()
                    .title("Write API Documentation")
                    .description("Document all REST endpoints using OpenAPI/Swagger.")
                    .status("TO_DO")
                    .build()));
        };
    }
}