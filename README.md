# Tutorial Backend Ludoteca Project

This project was generated with [Spring Boot](https://spring.io/projects/spring-boot)
using [Spring Initializr](https://start.spring.io/) and is
based on **Java**.

## Description

A web application built with to manage a ludoteca (board game library), where users can manage
games, categories, and authors.

#### Running the Application

- http://localhost:8080

## Git

https://github.com/anapi76/LudotecaBackend.git

## Requirements

- IntelliJ IDEA
- Spring Boot version 3.2.4
- Java 17.0.13 or higher.
- Maven
- Spring Web
- Spring Data JPA
- H2 Database

## Add Dependencies

To add a new dependency, search it in [Maven Repository](https://mvnrepository.com/) and add it to the `<dependencies>`
section of the pom.xml
file.

## Configuring Libraries

- Create a configuration package called `com.ccsw.tutorial.config`.
- Inside the configuration package, create a class called `ModelMapperConfig`. Once the **ModelMapper** is configured,
  can inject it into services or controllers to map between entities and DTOs.

## Configuring Database

- Modify the `application.properties` file adding the configuration to use H2 as an in-memory database
- http://localhost:8080/h2-console

## Functionalities

### Category

#### 1. **GET**

- **Route**: /category
- **Description**: Retrieves all categories.
- **HTTP Method**: `GET`

#### 2. **PUT **

- **Route**: /category
- **Route**: /category/{id}
- **Description**: Creates a new category or updates an existing category.
- **HTTP Method**: `PUT`

#### 2. **DELETE**

- **Route**: /category/{id}
- **Description**: Delete a category.
- **HTTP Method**: `DELETE`

## Documentation

This project uses **OpenAPI** to describe the API endpoints.

To view the full documentation:

- **OpenAPI Documentation (JSON)**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- **OpenAPI Documentation (YAML)**: [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

To view the API documentation through Swagger UI:

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
