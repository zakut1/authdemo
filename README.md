# Spring Boot Authentication Demo

A simple authentication demo built with Java, Spring Boot, MySQL, and a simple HTML/JavaScript frontend.

## Current Features

- User registration
- Password hashing with BCrypt
- Login with email and password
- MySQL database persistence
- Simple register, login, and dashboard pages
- Frontend sends HTTP requests to Spring Boot using fetch()

## Techs

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- HTML
- JavaScript
- Bootstrap

## Current Flow

Register:

HTML form → POST /api/auth/register → Spring Boot → MySQL

Login:

HTML form → POST /api/auth/login → Spring Boot → password check → dashboard redirect

## Project Status

Completed:
- Basic Spring Boot setup
- Register endpoint
- Login endpoint
- MySQL connection
- User entity and repository
- Password hashing
- Simple frontend pages

Not implemented yet:
- Real session-based authentication
- Logout
- Protected dashboard
- Docker
- Kafka

## Notes

The dashboard redirect currently only happens after successful login, but the dashboard page is not protected yet. Session-based authentication will be added later.

### Progress Log

#### Day 1
- Created Spring Boot project
- Added register endpoint
- Connected project to MySQL
- Created User entity, repository, and service layer
- Successfully saved users to database through Postman

#### Day 2
- Added BCrypt password hashing
- Added login endpoint
- Improved HTTP responses
- Created register, login, and dashboard HTML pages
- Connected frontend forms to backend endpoints using JavaScript fetch()
- Confirmed full browser → Spring Boot → MySQL flow works