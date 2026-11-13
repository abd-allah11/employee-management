# Employee Management System API

A RESTful Backend API built with **Spring Boot**, **Spring Security (JWT)**, and **PostgreSQL** for managing company departments, employees, user accounts, and leave requests, featuring token-based email onboarding and password recovery.

---

## Features

- **JWT Authentication & Role-Based Access Control (RBAC)**:
  - Stateless authentication using JSON Web Tokens (`JJWT 0.13.0`) valid for 14 days.
  - Role-based authorization (`ROLE_ADMIN` and `ROLE_USER`) securing endpoints via Spring Security.
  - Password encryption using `BCryptPasswordEncoder`.
- **Token-Based Employee Onboarding**:
  - Creating an employee profile automatically generates a unique `accountCreationToken` and sends an onboarding invitation link via email (`JavaMailSender`).
  - Employees complete registration (`POST /auth/signup?token=...`) to activate their `UserAccount` and verify their profile (`isVerified = true`).
- **Password Recovery Flow**:
  - Generates a 15-minute expiring `PasswordResetToken` emailed to the employee's registered address (`POST /auth/forgot-password/{username}`).
  - Allows secure password reset (`POST /auth/reset-password`) with automatic token invalidation.
- **Employee & Department Management**:
  - Full CRUD operations for employees and departments.
  - Custom 1-indexed pagination (`PaginatedResponse`) on `GET /employee` with `nextPageUrl`, `previousPageUrl`, `totalPages`, and `totalItems`.
- **Leave Request Management**:
  - Employees can submit leave requests (`POST /employee/{employeeId}/leave-request`) with date validation (`endDate >= startDate`) and default `PENDING` status, and view their leave history (`GET /employee/{employeeId}/leave-requests`).
- **Unified API Response & Exception Handling**:
  - All responses are wrapped in a standardized `GlobalResponse<T>` envelope (`status`, `data`, `errors`).
  - Centralized error handling via `@ControllerAdvice` (`GlobalExceptionResponse`) for custom business exceptions (`CustomResponseException`), Jakarta Bean Validation errors, and 404 resource errors.

---

## Tech Stack

| Category | Technology | Version |
| :--- | :--- | :--- |
| **Language** | Java | 27 |
| **Framework** | Spring Boot (`webmvc`, `data-jpa`, `security`, `validation`, `mail`) | 4.1.2 / 4.2.0-RC |
| **Database** | PostgreSQL | 42.7.14 Driver |
| **Security & Auth** | Spring Security + JJWT (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`) | 0.13.0 |
| **Utilities** | Project Lombok, Maven Wrapper (`mvnw`) | Latest |

---

## Project Architecture

```text
src/main/java/com/wd_aljak/employee_management/
├── abstracts/          # Service interfaces (DepartmentService, EmployeeService, LeaveRequestService)
├── config/             # SecurityConfig, JwtAuthFilter, and JwtHelper
├── controller/         # REST controllers (AuthController, DepartmentController, EmployeeController)
├── dtos/               # Java Records with Jakarta Validation constraints & PaginatedResponse
├── model/              # JPA Entities (Department, Employee, UserAccount, LeaveRequest, PasswordResetToken)
├── repo/               # Spring Data JPA Repositories
├── services/           # Business logic implementations & EmailService
├── shared/             # GlobalResponse wrapper, CustomResponseException, GlobalExceptionResponse
├── utils/              # SecurityUtils (ownership verification helper)
└── EmployeeManagementApplication.java
```

---

## Database Entities & Relationships

- **`Department`** (`department`):
  - `id` (`UUID`, PK), `name` (`String`, unique, max 50 chars).
- **`Employee`** (`employee`):
  - `id` (`UUID`, PK), `firstName`, `lastName`, `email` (unique), `phoneNumber` (10 digits), `hireDate` (`LocalDate`), `position`, `isVerified` (`boolean`), `accountCreationToken` (`String`).
  - **Relation**: `Many-to-One` with `Department` (`department_id`).
- **`UserAccount`** (`user_account`):
  - `id` (`UUID`, PK), `username` (unique), `password` (BCrypt hashed), `role` (default `"USER"`). Implements Spring Security `UserDetails`.
  - **Relation**: `One-to-One` with `Employee` (`employee_id`).
- **`LeaveRequest`** (`leave_request`):
  - `id` (`UUID`, PK), `startDate` (`LocalDate`), `endDate` (`LocalDate`), `reason` (`TEXT`), `state` (default `"PENDING"`).
  - **Relation**: `Many-to-One` with `Employee` (`employee_id`).
- **`PasswordResetToken`** (`password_rest_token`):
  - `id` (`UUID`, PK), `token` (unique), `expiryDate` (`LocalDateTime` - 15 mins).
  - **Relation**: `One-to-One` with `UserAccount` (`user_id`).

---

## API Endpoints

### 1. Authentication (`/auth`)

| Method | Endpoint | Access | Description | Request Body / Params |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/auth/signup` | Public | Complete employee account signup using email token | Query: `?token=<UUID>`<br>Body: `SignupRequest` (`username`, `password`) |
| `POST` | `/auth/login` | Public | Authenticate user and receive JWT token | Body: `LoginRequest` (`username`, `password`) |
| `POST` | `/auth/forgot-password/{username}` | Public | Send 15-min password reset token to user's email | Path: `username` |
| `POST` | `/auth/reset-password` | Public | Reset account password using token | Body: `ResetPasswordRequest` (`token`, `newPassword`) |

### 2. Employees & Leave Requests (`/employee`)

| Method | Endpoint | Access | Description | Request Body / Params |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/employee` | `ADMIN` | List employees with 1-based pagination | Query: `?page=1&size=3` |
| `GET` | `/employee/{employeeId}` | `ADMIN`, `USER` | Get employee details by ID | Path: `employeeId` (`UUID`) |
| `POST` | `/employee` | `ADMIN` | Create employee & send onboarding email | Body: `EmployeeCreateDTO` (`firstName`, `lastName`, `email`, `phoneNumber`, `hireDate`, `position`, `departmentId`) |
| `PUT` | `/employee/{employeeId}` | `ADMIN`, `USER` | Update employee profile | Path: `employeeId`<br>Body: `EmployeeUpdateDTO` (`firstName`, `lastName`, `phoneNumber`, `position`) |
| `DELETE` | `/employee/{employeeId}` | `ADMIN` | Delete employee by ID | Path: `employeeId` (`UUID`) |
| `POST` | `/employee/{employeeId}/leave-request` | `ADMIN`, `USER` | Submit a new leave request for an employee | Path: `employeeId`<br>Body: `LeaveRequestCreateDTO` (`reason`, `startDate`, `endDate`) |
| `GET` | `/employee/{employeeId}/leave-requests` | `ADMIN`, `USER` | Get all leave requests for a specific employee | Path: `employeeId` (`UUID`) |

### 3. Departments (`/department`)

| Method | Endpoint | Access | Description | Request Body / Params |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/department` | Authenticated | List all departments | None |
| `GET` | `/department/{departmentId}` | Authenticated | Get department by ID | Path: `departmentId` (`UUID`) |
| `POST` | `/department` | Authenticated | Create a new department | Body: `DepartmentDTO` (`name`) |
| `DELETE` | `/department/{departmentId}` | Authenticated | Delete a department by ID | Path: `departmentId` (`UUID`) |

---

## Standard API Response Format

### Success Response
```json
{
  "status": "success",
  "data": {
    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "name": "Engineering"
  },
  "errors": null
}
```

### Error Response
```json
{
  "status": "error",
  "data": null,
  "errors": [
    {
      "message": "Employee with id -> ... Not found"
    }
  ]
}
```

---

## Configuration & Setup

### Prerequisites
- **JDK 27** (or compatible Java version configured in `pom.xml`)
- **PostgreSQL** running locally on port `5432`
- **SMTP Mail Account** (e.g., Gmail App Password) for onboarding and password reset emails

### 1. Database Setup
Create a PostgreSQL database named `employee-sys`:
```sql
CREATE DATABASE "employee-sys";
```
> **Note:** `spring.jpa.hibernate.ddl-auto` is set to `none` in `application.properties`. Set it to `update` or `create` on first run so Hibernate generates the database schema automatically.

### 2. Environment & Application Properties
Update `src/main/resources/application.properties` using environment variables or your local credentials:

```properties
spring.application.name=employee-management

# PostgreSQL Database
spring.datasource.url=jdbc:postgresql://localhost:5432/employee-sys
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:your_db_password}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration (Base64-encoded 256-bit key)
jwt.secret=${JWT_SECRET:your_base64_encoded_secret_key}

# Mail (SMTP) Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME:your_email@gmail.com}
spring.mail.password=${MAIL_PASSWORD:your_gmail_app_password}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Backend Origin & CORS
backend.origin=http://localhost:8080
```
*(CORS is pre-configured in `SecurityConfig` to allow requests from `http://localhost:5173`).*

### 3. Build & Run
Using the included Maven Wrapper:

```bash
# Linux / macOS
./mvnw clean spring-boot:run

# Windows
mvnw.cmd clean spring-boot:run
```
The server will start at `http://localhost:8080`.
