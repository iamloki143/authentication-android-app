# Android Authentication App with ASP.NET Core Backend

A complete full-stack authentication system built using **Android (Kotlin + Jetpack Compose)** and **ASP.NET Core Web API**. The project demonstrates modern authentication and authorization practices including JWT authentication, refresh tokens, email verification, password reset functionality, role-based access control, and secure token storage.

---

## Features

###  Authentication
- User Registration
- User Login
- JWT Access Token Authentication
- Refresh Token Authentication
- Persistent Login Session
- Secure Logout

###  Authorization
- Role-Based Authorization
- User Role Support
- Admin Role Support
- Protected Endpoints
- Admin-Only Screens

###  Email Features
- Email Verification
- Resend Verification Email
- Forgot Password
- Password Reset Using Email Token

###  Android Features
- Jetpack Compose UI
- MVVM Architecture
- StateFlow
- Navigation Compose
- Hilt Dependency Injection
- Retrofit Networking
- OkHttp Interceptors
- DataStore Token Storage
- Automatic Token Refresh
- Material 3 Design

###  Backend Features
- ASP.NET Core Web API
- Entity Framework Core
- PostgreSQL Database
- JWT Authentication
- Refresh Tokens
- BCrypt Password Hashing
- Email Service Integration
- Secure API Design

---

##  Architecture

```text
Android App
│
├── UI (Jetpack Compose)
├── ViewModel
├── Repository
├── Retrofit
├── DataStore
└── Hilt DI
        │
        ▼
ASP.NET Core Web API
│
├── Controllers
├── Services
├── JWT Authentication
├── Email Service
├── Entity Framework Core
└── PostgreSQL
```

---

##  Tech Stack

### Android
- Kotlin
- Jetpack Compose
- MVVM
- StateFlow
- Navigation Compose
- Hilt
- Retrofit
- OkHttp
- DataStore

### Backend
- ASP.NET Core Web API
- C#
- Entity Framework Core
- PostgreSQL
- JWT
- BCrypt

### Tools
- Android Studio
- Visual Studio
- Postman
- pgAdmin

---

##  Screens

### Authentication
- Login Screen
- Register Screen
- Email Verification Screen
- Forgot Password Screen
- Reset Password Screen

### Application
- Home Screen
- Dashboard Screen
- Profile Screen
- Admin Panel

---

##  API Endpoints

### Authentication

```http
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh
POST /api/auth/logout
```

### Email Verification

```http
POST /api/auth/verify-email
POST /api/auth/resend-verification
```

### Password Reset

```http
POST /api/auth/forgot-password
POST /api/auth/reset-password
```

### Protected Endpoints

```http
GET /api/auth/profile
GET /api/auth/admin
```

---

##  Project Structure

### Android

```text
app
├── data
│   ├── remote
│   ├── repository
│   └── local
├── presentation
│   ├── screen
│   └── components
├── navigation
├── di
└── utils
```

### Backend

```text
Backend
├── Controllers
├── Data
├── Models
├── DTOs
├── Services
├── Migrations
└── Configuration
```

---

##  Security Features

- JWT Access Tokens
- Refresh Tokens
- Password Hashing with BCrypt
- Protected API Endpoints
- Role-Based Authorization
- Email Verification
- Secure Token Storage using DataStore
- Automatic Token Refresh

---

##  Learning Outcomes

This project helped me gain practical experience in:

- Android Development
- Jetpack Compose
- MVVM Architecture
- REST API Integration
- Dependency Injection with Hilt
- ASP.NET Core Web API
- PostgreSQL Database Design
- JWT Authentication
- Refresh Token Implementation
- Email Verification Systems
- Password Recovery Systems
- Role-Based Authorization

---

##  Future Improvements

- Redis Caching
- Push Notifications
- Docker Deployment
- CI/CD Pipeline
- Unit Testing
- Integration Testing
- Cloud Deployment
- User Management Dashboard
- Audit Logs

---

##  Author

**Lokesh Kumar**

Android Developer | Backend Developer

GitHub: https://github.com/iamloki143
