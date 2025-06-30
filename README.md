
<p align="center">
  <img src="https://d9hhrg4mnvzow.cloudfront.net/lp.3035tech.com/96c1669d-logo-teach-horiz-branco_1000000000000000000028.png" alt="3035tech Logo" width="200"/>
</p>

# ControleCursos — Course Management System

This project was developed as part of the full-stack training program at **3035Teach** bootcamp.

It is a backend system built with **Java**, **JPA**, and **PostgreSQL** for managing courses, students, and enrollments. The system replaces manual administrative tasks with a clean, maintainable API following layered architecture and best practices.

# Simple Spring Boot Product API (Exercise)

Started as a simple Spring Boot exercise to build a REST API managing a product catalog without a database, using in-memory storage.

Features included:

- CRUD operations for products (`id`, `name`, `price`)
- Endpoints:  
  - `GET /products`  
  - `GET /products/{id}`  
  - `POST /products`  
  - `PATCH /products/{id}`  
  - `DELETE /products/{id}`
- Layered architecture with Controller, Service, Repository
- Use of DTOs for data transfer

# Evolution

The project evolved into a professional backend applying:

- DTOs for request/response separation
- Exception handling with custom exceptions and `@ControllerAdvice`
- Mapper classes for entity-DTO conversion
- Preparation for database integration using Spring Data JPA
- Proper git workflow with branches and semantic commits

This progression reflects the learning journey at 3035Teach and the application of clean architecture principles.
