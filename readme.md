
# DevTaskPro

DevTaskPro is a personal task management API built with Spring Boot. This project is a step-by-step learning journey to build a robust backend application, starting with fundamental features.

---

## 🚀 Current Features

* **Task Management (CRUD):** Create, view, update, and delete tasks.
* **Task Details:** Each task has a title, description, and status.
* **REST API:** Provides a basic set of endpoints for task operations.
* **In-Memory Database:** Uses H2 Database for easy local development, pre-loaded with dummy data on startup.

---

## 🛠️ Technologies Used (Current)

* **Spring Boot:** The core framework for building the API.
* **Spring Data JPA:** For seamless database interaction.
* **H2 Database:** An embedded, in-memory database for development.
* **Lombok:** To reduce boilerplate code in entities.
* **Java 17+**

---

## ⚙️ Getting Started

To get DevTaskPro up and running on your local machine:

### Prerequisites

* Java Development Kit (JDK) 17 or higher
* Maven

### Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/saumik-talukdar/Devtask-Pro.git
    cd devtask-pro
    ```
    

2.  **Build the project:**
    ```bash
    mvn clean install
    ```

### Running the Application

* **Using Maven:**
    ```bash
    mvn spring-boot:run
    ```
* **From your IDE:** Run the `main` method in `DevtaskProApplication.java`.

The application will start on `http://localhost:8080`.

---

## 📊 H2 Console

Access the H2 Database web console to view your data:

* **URL:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:devdb`
* **Username:** `sa`
* **Password:** (leave blank)

---

## 🚀 API Endpoints

All task-related API endpoints are under `/api/tasks`:

* `GET /api/tasks`: Get all tasks.
* `GET /api/tasks/{id}`: Get a task by ID.
* `POST /api/tasks`: Create a new task (send `title`, `description`, `status` in request body).
* `PUT /api/tasks/{id}`: Update an existing task (send updated `title`, `description`, `status` in request body).
* `DELETE /api/tasks/{id}`: Delete a task by ID.

---

## 🧪 Dummy Data

On application startup, a `CommandLineRunner` automatically populates the H2 database with some sample tasks, so you have immediate data to work with. This data resets with each application restart.

---

## 🤝 Contributing

This project is a personal learning effort. Contributions are welcome as the project evolves!

---