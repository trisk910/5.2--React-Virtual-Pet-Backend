# Virtual Pets API 🐾

This is the backend for the Virtual Pets application, inspired by the Medabots universe. Built with **Spring Boot** and **MySQL**, the API allows users to manage and interact with their own virtual creatures.

# Frontend repository:

https://github.com/trisk910/5.2--React-Virtual-Pet-Frontend.git

## 🔧 Tech Stack

- Java 23
- Spring Boot
- Spring Security + JWT
- MySQL
- JPA/Hibernate
- Swagger (API documentation)

## 📚 Project Context

This backend was developed as part of a learning project where an AI-generated frontend was integrated with a manually built Java backend. The goal is not only to build a working application but also to explore AI-assisted code generation, backend connectivity, and secure role-based access control.

## 🌟 Features

- **User Registration**: Create an account with username and password.
- **Login**: Authenticate with JWT token.
- **CRUD for Pets**:
  - Create: Choose from fantasy creatures (dragons, unicorns, aliens).
  - Read: View your pets in a vibrant virtual world.
  - Update: Feed, play, accessorize, and evolve your pets.
  - Delete: Retire pets you no longer want to care for.

## 🛡️ Role-Based Access Control

Two roles are supported:

- `ROLE_USER`: Can only view and manage their own pets.
- `ROLE_ADMIN`: Full access to all pets in the system.

### 🔐 Authorization Middleware

A custom JWT-based middleware ensures only authorized actions are allowed:

- Users can only access their own pets.
- Admins can manage all pets.

This enhances data security and maintains proper access control within the app.

## 📑 API Documentation

The API is documented using Swagger. Once the application is running, you can access the documentation at:

http://localhost:8080/swagger-ui.html

