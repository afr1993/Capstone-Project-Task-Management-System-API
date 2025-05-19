# Task Manager API

## Overview

This is a RESTful Task Manager application built with Spring Boot. It supports user registration, login with JWT authentication, role-based authorization, and task management. The application uses MySQL as the database and provides API documentation with Swagger / OpenAPI.

---

## Features

- User registration and authentication with JWT
- Role-based access control (e.g., USER, ADMIN)
- CRUD operations for tasks (create, read, update, delete)
- Each task is associated with a user
- Password encryption with BCrypt
- API documentation via Swagger UI
- Spring Security integration with stateless JWT filters
- Global exception handling
- MySQL database persistence

---

## Technologies

- Java 17+
- Spring Boot 3.x
- Spring Security
- JWT (JSON Web Tokens)
- Spring Data JPA (Hibernate)
- MySQL 8+
- Lombok
- Swagger / OpenAPI (springdoc-openapi)
- Maven (build tool)

---

## Getting Started

### Prerequisites

- Java 17 or newer installed
- Maven installed
- MySQL server running and accessible
- Postman or similar API client (for testing)

### Setup

1. **Clone the repository**

git clone https://github.com/yourusername/taskmanager.git
cd taskmanager

2.Configure MySQL

Create a database named taskmanager and configure the credentials in src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update

3.Build and Run

mvn clean install
mvn spring-boot:run
The application will start on http://localhost:8080
------------------------------------------------------------------------------
API Usage
Authentication endpoints
POST /auth/register — Register a new user

POST /auth/login — Login and receive JWT token

Secured endpoints
Task management endpoints (CRUD) require JWT in Authorization: Bearer <token> header.

Admin endpoints require users with role ROLE_ADMIN.

Swagger UI
Access API documentation and test endpoints at: http://localhost:8080/swagger-ui/index.html

Testing with Postman
Register a user:
POST http://localhost:8080/auth/register
Body: {
  "username": "testuser",
  "password": "password123"
}

Login to get JWT token:

POST http://localhost:8080/auth/login
Body: {
  "username": "testuser",
  "password": "password123"
}
Copy the token from the response.

Use the token to access secured endpoints:

In Postman, set the header:
Authorization: Bearer <your_jwt_token>
For example, to create a task:

POST http://localhost:8080/tasks
Body: {
  "title": "Sample Task",
  "description": "This is a test task",
  "completed": false
}

-----Project Structure----
src/main/java/com/example/taskmanager
│
├── config          # Swagger and other configurations
├── controller      # REST controllers (AuthController, TaskController, AdminController)
├── dto             # Data Transfer Objects (UserDTO, TaskDTO, AuthRequest, AuthResponse)
├── entity          # JPA Entities (User, Task, Role)
├── exception       # Custom exceptions and global handlers
├── repository      # Spring Data JPA repositories
├── security        # Spring Security, JWT filters, utilities
├── service         # Business logic services (UserService, TaskService, UserDetailsServiceImpl)
└── TaskManagerApplication.java  # Main Spring Boot application class
Troubleshooting
Make sure MySQL is running and the credentials are correct

If Swagger UI shows 403 or 401 errors, check Spring Security config to allow /v3/api-docs/** and /swagger-ui/**

Check application logs for JWT parsing errors or missing beans

Use Postman or Swagger UI to test API endpoints step by step

Future Improvements
Add task priorities and deadlines

Implement pagination and filtering for task lists

Enable email notifications for task deadlines

Add refresh token functionality for JWT

Add integration and unit tests

Author
Adrian Flores 
Email: afr.1993@gmail.com
GitHub: https://github.com/afr1993






