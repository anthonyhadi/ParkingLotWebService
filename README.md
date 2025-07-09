# ParkingLotWebService

## Overview

This is a simple HTTP Restful Web service built using spring boot. The content of the app itself is about a simple parking lot management system.

## Java Version
`21`

## Technologies

- Spring Boot Web Restful
- PostgreSQL Database
- Spring Data JPA
- Project Lombok mainly for auto-generating getter & setter

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