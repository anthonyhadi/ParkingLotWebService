# ParkingLotWebService

## Overview

This is a simple HTTP Restful Web service built using Spring Boot with JWT authentication. The content of the app itself is about a simple parking lot management system.

## Java Version
`21`

## Technologies

- Spring Boot Web Restful
- Spring Security with JWT
- PostgreSQL Database
- Spring Data JPA
- Project Lombok mainly for auto-generating getter & setter

## Authentication

The application uses JWT (JSON Web Token) authentication with role-based access control:

### Default Users
- **Admin**: `admin` / `admin123` (ADMIN role)
- **User**: `user` / `user123` (USER role)

### API Endpoints

#### Authentication Endpoints (No authentication required)
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

#### Parking Lot Endpoints (Authentication required)
- `GET /api/create` - Create parking lot (ADMIN only)
- `GET /api/park` - Park a car (USER/ADMIN)
- `GET /api/remove` - Remove a car (USER/ADMIN)
- `GET /api/status` - Get parking status (USER/ADMIN)

### Using the API

1. **Login to get a JWT token:**
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"admin123"}'
   ```

2. **Use the token in subsequent requests:**
   ```bash
   curl -X GET "http://localhost:8080/api/create?noOfLots=5" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"
   ```

## How to run test locally
```
$ ./gradlew test  # macOS/Linux
$ gradlew.bat test  # Windows
```

## How to run via Docker
```
$ cd docker
$ docker compose up -d
```

## Api Docs
http://localhost:8080/swagger-ui/index.html

The Swagger UI now includes authentication support. Click the "Authorize" button and enter your JWT token to test protected endpoints.