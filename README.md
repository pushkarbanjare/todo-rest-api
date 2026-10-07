# Todo REST API

A RESTful Todo API built with Spring Boot 3.5, demonstrating layered architecture (Controller → Service → Repository), DTO-based request/response separation, centralized exception handling, and both unit and integration test coverage.

## Tech Stack
- Java 21 (LTS)
- Spring Boot 3.5 (Web, Data JPA, Validation)
- H2 (in-memory database)
- Lombok
- JUnit 5 + Mockito + MockMvc

## Architecture

Controller → Service (interface + impl) → Repository → H2 Database

Request/response DTOs are separated from the `Todo` entity to prevent clients from controlling system-managed fields (`id`, `createdAt`, `updatedAt`). A global `@RestControllerAdvice` handles `ResourceNotFoundException` (404) and validation failures (400) with consistent, field-level error responses.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|--------------|
| POST | /api/todos | Create a todo |
| GET | /api/todos | List all todos |
| GET | /api/todos/{id} | Get a todo by id |
| PUT | /api/todos/{id} | Update a todo |
| DELETE | /api/todos/{id} | Delete a todo |

## Running Locally
```bash
./mvnw spring-boot:run
```
App runs on `http://localhost:8080`. H2 console available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:tododb`, user: `sa`, no password).

## Running Tests
```bash
./mvnw test
```
10 tests: 5 service-layer unit tests (Mockito), 4 controller integration tests (MockMvc), 1 context load test.

## Example Request
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "Learn Spring Boot", "description": "Build a REST API", "completed": false}'
```
