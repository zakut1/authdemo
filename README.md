# Spring Boot Authentication Demo

A simple authentication demo built with Java, Spring Boot, MySQL, simple HTML/JavaScript frontend, Kafka, and Docker.

## Running the Project with Docker

### Requirements

You only need **Docker Desktop** installed.

Java, Maven, MySQL, and Kafka do not need to be installed separately on your machine because the required services are handled through Docker.

### 1. Clone the repository

```text
git clone <repository-url>
```

Then enter the project directory:

```text
cd authdemo
```

### 2. Create the `.env` file

The repository contains an `.env.example` file with the required environment-variable structure.

Create a copy of:

```text
.env.example
```

and rename the copy to:

```text
.env
```
Or run on PowerShell:
```text
Copy-Item .env.example .env
```

### 3. Start the application

Run:

```text
docker compose up --build -d
```

Docker Compose will start the whole application

```text
auth-app      → Spring Boot application
auth-db       → MySQL database
auth-kafka    → Apache Kafka broker
```

The application automatically connects to MySQL and Kafka through the Docker Compose network.

### 4. Open the application

Once all containers are running, open the following pages in your browser:

**Registration page**

```text
http://localhost:8080/register.html
```

**Login page**

```text
http://localhost:8080/login.html
```

**Dashboard**

```text
http://localhost:8080/dashboard.html
```

### 5. Verify the containers

You can verify that all three containers are running through Docker Desktop.

The expected containers are:

```text
auth-app
auth-db
auth-kafka
```


## Features

- User registration
- BCrypt password hashing
- User login
- HttpSession-based authentication
- Logout
- Simple Bootstrap frontend
- MySQL persistence
- Dockerized Spring Boot application
- Dockerized MySQL database
- Apache Kafka integration
- User registration event publishing and consuming
- Login event publishing and consuming
- Successful and failed login event tracking
- Login history persistence in MySQL

## Techs

### Backend

- Java 17
- Spring Boot 4.1.0
- Spring MVC
- Spring Data JPA
- Spring Security Crypto
- Spring Kafka

### Database

- MySQL 8.4

### Messaging

- Apache Kafka

### Frontend

- HTML
- JavaScript
- Bootstrap

### Infrastructure

- Docker
- Docker Compose

---

## Project Status

Completed:
- Basic Spring Boot setup
- Register endpoint
- Login endpoint
- MySQL connection
- User entity and repository
- Password hashing
- Simple frontend pages
- Session-based authentication
- Logout
- Docker integration
- Basic Kafka integration
