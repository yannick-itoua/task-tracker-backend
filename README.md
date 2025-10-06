# Task Tracker Backend

Spring Boot REST API for the Task Tracker application.

## 🏗️ Architecture

```
┌─────────────────────────────────────────────┐
│              Spring Boot Application        │
├─────────────────────────────────────────────┤
│  Controller Layer                           │
│  ├── TaskController (REST endpoints)       │
│  └── GlobalExceptionHandler               │
├─────────────────────────────────────────────┤
│  Service Layer (Business Logic)            │
│  └── Implicit (handled by Spring Data)    │
├─────────────────────────────────────────────┤
│  Repository Layer                          │
│  └── TaskRepository (JPA operations)      │
├─────────────────────────────────────────────┤
│  Entity Layer                              │
│  └── Task (JPA Entity)                    │
└─────────────────────────────────────────────┘
                    │
                    ▼
        ┌─────────────────────┐
        │   PostgreSQL DB     │
        └─────────────────────┘
```

## 🚀 Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Build Tool**: Maven 3.9.5
- **Database**: PostgreSQL 15
- **ORM**: Spring Data JPA
- **Validation**: Spring Boot Validation
- **Monitoring**: Spring Boot Actuator

## 📋 Features

### Core Functionality
- ✅ CRUD operations for tasks
- 🔍 Search and filtering capabilities
- 📊 Task statistics endpoint
- 🔄 Toggle task completion status

### Technical Features
- 🛡️ Input validation with custom error responses
- 🌐 CORS configuration for frontend integration
- 📈 Health checks and monitoring with Actuator
- 🐳 Docker containerization
- 🔄 Automatic database schema updates

## 🌐 API Documentation

### Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/tasks` | Get all tasks | - |
| GET | `/api/tasks?done=true` | Get completed tasks | - |
| GET | `/api/tasks?search=keyword` | Search tasks | - |
| GET | `/api/tasks/{id}` | Get task by ID | - |
| POST | `/api/tasks` | Create new task | TaskRequest |
| PUT | `/api/tasks/{id}` | Update task | TaskRequest |
| PATCH | `/api/tasks/{id}/toggle` | Toggle completion | - |
| DELETE | `/api/tasks/{id}` | Delete task | - |
| GET | `/api/tasks/stats` | Get statistics | - |

### Request/Response Models

#### TaskRequest
```json
{
  "title": "string (required, max 255)",
  "description": "string (optional, max 1000)",
  "done": "boolean (default: false)"
}
```

#### TaskResponse
```json
{
  "id": "number",
  "title": "string",
  "description": "string",
  "done": "boolean",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

#### TaskStats
```json
{
  "total": "number",
  "completed": "number",
  "pending": "number"
}
```

### Error Responses

#### Validation Error (400)
```json
{
  "message": "Validation failed",
  "status": 400,
  "timestamp": "2024-01-01T10:00:00",
  "errors": {
    "title": "Title is required",
    "description": "Description must not exceed 1000 characters"
  }
}
```

#### Not Found (404)
```json
{
  "message": "Task not found",
  "status": 404,
  "timestamp": "2024-01-01T10:00:00"
}
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+ (or Docker)

### Local Development

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd task-tracker-backend
   ```

2. **Start PostgreSQL:**
   ```bash
   # Using Docker
   docker run --name postgres-dev -e POSTGRES_DB=tasktracker -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres:15-alpine
   
   # Or use the provided docker-compose
   docker-compose -f ../docker-compose.dev.yml up database
   ```

3. **Run the application:**
   ```bash
   # Using Maven wrapper
   ./mvnw spring-boot:run
   
   # Or on Windows
   .\mvnw.cmd spring-boot:run
   ```

4. **Access the API:**
   - API Base URL: `http://localhost:8080/api`
   - Health Check: `http://localhost:8080/actuator/health`

### Configuration

#### Environment Variables
```bash
# Database Configuration
DATABASE_URL=jdbc:postgresql://localhost:5432/tasktracker
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=password

# CORS Configuration
CORS_ORIGINS=http://localhost:3000,http://localhost:5173

# Optional: Spring Profile
SPRING_PROFILES_ACTIVE=dev
```

#### Application Properties
```properties
# Database
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/tasktracker}
spring.datasource.username=${DATABASE_USERNAME:postgres}
spring.datasource.password=${DATABASE_PASSWORD:password}

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true

# Server
server.port=8080

# CORS
spring.web.cors.allowed-origins=${CORS_ORIGINS:http://localhost:3000}
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*

# Actuator
management.endpoints.web.exposure.include=health,info
```

## 🐳 Docker

### Build Image
```bash
docker build -t task-tracker-backend .
```

### Run Container
```bash
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://host.docker.internal:5432/tasktracker \
  -e DATABASE_USERNAME=postgres \
  -e DATABASE_PASSWORD=password \
  task-tracker-backend
```

### Multi-stage Dockerfile
The Dockerfile uses multi-stage builds for optimization:
1. **Build stage**: Compiles the application with Maven
2. **Runtime stage**: Creates a slim production image with OpenJDK

## 🧪 Testing

### Run Tests
```bash
# All tests
./mvnw test

# Specific test class
./mvnw test -Dtest=TaskControllerTest

# Integration tests
./mvnw test -Dtest=*IntegrationTest
```

### Test Coverage
```bash
./mvnw jacoco:report
```

## 📊 Monitoring

### Health Checks
- **Endpoint**: `/actuator/health`
- **Response**: `{"status": "UP"}`

### Application Info
- **Endpoint**: `/actuator/info`

### Database Health
The application includes automatic database connectivity checks.

## 🔧 Development

### Project Structure
```
src/
├── main/
│   ├── java/com/tasktracker/
│   │   ├── TaskTrackerApplication.java     # Main application class
│   │   ├── controller/
│   │   │   └── TaskController.java         # REST controller
│   │   ├── entity/
│   │   │   └── Task.java                   # JPA entity
│   │   ├── repository/
│   │   │   └── TaskRepository.java         # Data repository
│   │   ├── dto/
│   │   │   ├── TaskRequest.java            # Request DTO
│   │   │   └── TaskResponse.java           # Response DTO
│   │   └── exception/
│   │       └── GlobalExceptionHandler.java # Error handling
│   └── resources/
│       └── application.properties          # Configuration
└── test/
    └── java/                               # Test classes
```

### Key Components

#### Task Entity
- JPA annotations for database mapping
- Automatic timestamp management
- Validation constraints

#### TaskRepository
- Extends `JpaRepository<Task, Long>`
- Custom query methods for filtering
- Built-in pagination support

#### TaskController
- RESTful endpoint design
- Request/Response DTOs
- Exception handling
- CORS configuration

### Building for Production

1. **Create executable JAR:**
   ```bash
   ./mvnw clean package -DskipTests
   ```

2. **Run JAR:**
   ```bash
   java -jar target/task-tracker-backend-0.0.1-SNAPSHOT.jar
   ```

## 🚀 Deployment

### Railway
```bash
# Connect to Railway
railway login

# Deploy
railway up

# Set environment variables in Railway dashboard:
# - DATABASE_URL (from Railway PostgreSQL plugin)
# - CORS_ORIGINS (frontend URL)
```

### Heroku
```bash
# Login to Heroku
heroku login

# Create app
heroku create task-tracker-backend

# Add PostgreSQL addon
heroku addons:create heroku-postgresql:hobby-dev

# Deploy
git push heroku main
```

### Environment-specific Configurations

#### Development
```properties
spring.profiles.active=dev
spring.jpa.show-sql=true
logging.level.com.tasktracker=DEBUG
```

#### Production
```properties
spring.profiles.active=prod
spring.jpa.show-sql=false
logging.level.root=WARN
```

## 🔍 Troubleshooting

### Common Issues

1. **Database Connection Failed:**
   ```bash
   # Check PostgreSQL is running
   docker ps | grep postgres
   
   # Verify connection string
   echo $DATABASE_URL
   ```

2. **Port Already in Use:**
   ```bash
   # Find process using port 8080
   lsof -i :8080
   
   # Kill process
   kill -9 <PID>
   ```

3. **CORS Errors:**
   - Check `CORS_ORIGINS` environment variable
   - Verify frontend URL is included

4. **Maven Build Issues:**
   ```bash
   # Clean and rebuild
   ./mvnw clean compile
   
   # Update dependencies
   ./mvnw dependency:resolve
   ```

### Logging

Enable debug logging for specific packages:
```properties
logging.level.com.tasktracker=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## 📞 API Testing

### Using cURL

```bash
# Get all tasks
curl http://localhost:8080/api/tasks

# Create task
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"New Task","description":"Task description"}'

# Update task
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Task","done":true}'

# Delete task
curl -X DELETE http://localhost:8080/api/tasks/1
```

### Using Postman
Import the provided Postman collection for comprehensive API testing.

---

For more information, see the main [project README](../README.md).