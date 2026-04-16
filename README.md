# MiniJira – Dockerized Project Management REST API

MiniJira is a backend system inspired by Jira, built using Spring Boot.  
It provides REST APIs for managing projects, issues, comments, attachments, and users with secure JWT-based authentication.

The application is fully containerized using Docker and uses PostgreSQL as the database with Flyway for version-controlled schema migrations.

---

## Features

- User Authentication using JWT
- Project Management APIs
- Issue Tracking System
- Comments on Issues
- File Attachments support
- Notification support
- Flyway database migrations
- Dockerized environment
- Swagger OpenAPI documentation
- Spring Security integration
- JPA / Hibernate ORM

---

## Tech Stack

Backend:
- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- Flyway

Database:
- PostgreSQL

DevOps:
- Docker
- Docker Compose

Documentation:
- Swagger OpenAPI

---

## Architecture

Client (Swagger UI / Postman)
        |
        ↓
Spring Boot REST API (Docker Container)
        |
        ↓
PostgreSQL Database (Docker Container)
        |
        ↓
Flyway Migration Scripts

---

## Prerequisites

Install the following:

- Docker Desktop

No need to install Java, Maven, or PostgreSQL manually.

---

## Running the Application using Docker

Clone repository:

git clone https://github.com/srujan9166/MiniJira.git

Navigate to project folder:

cd minijira

Build and start containers:

docker compose up --build

---

## Access Swagger UI

After the application starts:

http://localhost:8080/swagger-ui/index.html

Swagger provides interactive API documentation to test endpoints.

---

## Stopping the Application

docker compose down

---

## Reset Database

If you want to recreate the database:

docker compose down -v
docker compose up --build

---

## Project Structure

src/main/java/com/example/minijira

- controller → REST controllers
- service → business logic
- repository → database access layer
- model → JPA entities
- dto → request/response objects
- configuration → security & app configuration
- filter → JWT filters
- util → helper classes

src/main/resources

- application.properties → configuration
- db/migration → Flyway migration scripts

Docker

- Dockerfile → builds Spring Boot container
- docker-compose.yml → multi-container setup

---

## Example API Endpoints

Authentication:

POST /api/auth/signup
POST /api/auth/signin

Project:

GET /api/projects
POST /api/projects

Issues:

GET /api/issues
POST /api/issues

Comments:

POST /api/comments

Attachments:

POST /api/attachments

---

## Environment Variables

Configured inside docker-compose.yml:

POSTGRES_DB=minijira
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres

---

## Learning Outcomes

This project demonstrates:

- REST API design using Spring Boot
- Database schema versioning using Flyway
- Secure authentication using JWT
- Docker containerization
- Multi-container orchestration using Docker Compose
- Integration of PostgreSQL with Spring Boot
- API documentation using Swagger

---

## Author

Developed by Srujan

GitHub:
https://github.com/yourusername

LinkedIn:
Add your LinkedIn profile link here

---

## Future Improvements

- Role-based authorization
- Pagination & filtering
- Unit & integration testing
- CI/CD pipeline integration
- Frontend integration