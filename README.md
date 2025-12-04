# School Website & Notification Microservice

This project consists of two Spring Boot applications:
1.  **school-main**: The main school website (Thymeleaf + Spring Boot).
2.  **school-notification**: A REST microservice for handling notifications.

## Prerequisites
- Java 17
- Maven

## How to Run

### 1. Start the Notification Service
```bash
cd school-notification
mvn spring-boot:run
```
Runs on port **8081**.

### 2. Start the Main Application
```bash
cd school-main
mvn spring-boot:run
```
Runs on port **8080**.

## Accessing the Application
- **Public Website**: [http://localhost:8080](http://localhost:8080)
- **Admin Panel**: [http://localhost:8080/admin/dashboard](http://localhost:8080/admin/dashboard)
- **Login**: [http://localhost:8080/login](http://localhost:8080/login)

### Default Credentials
- **Admin**: `admin` / `admin123`
- **User**: `user` / `user123`

## Features
- **Public Pages**: Home, News, Events, Gallery, About, Contact, Teachers, Classes, Announcements.
- **Admin Panel**: Manage News, Events, Gallery, Announcements.
- **Microservice Integration**: Creating/Deleting items triggers notifications via Feign Client.
- **Security**: Role-based access control (USER, ADMIN).
- **Caching**: Homepage news list is cached.
- **Scheduling**: Auto-post holiday message (Dec 25th).

## Database
By default, both apps use **H2 In-Memory Database**.
- Main App Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:mem:schooldb`)
- Notification App Console: [http://localhost:8081/h2-console](http://localhost:8081/h2-console) (JDBC URL: `jdbc:h2:mem:notificationdb`)
- Username: `sa`, Password: `password`

## Testing
Run tests with:
```bash
mvn test
```
