# CRM-JTool v1.0 [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)[![Netlify Status](https://api.netlify.com/api/v1/badges/633082cd-8c9e-4c2e-b2fc-b3063e66d8b2/deploy-status)](https://app.netlify.com/sites/portfolio-abelprieto-fullstack/deploys)

![11AF1495-A4CF-42B6-852F-2AFD3C44E337_1_201_a](https://github.com/user-attachments/assets/db4ee1f8-494a-4e20-a339-f15ac84b9e23)

The CRM is a system designed to help companies manage, analyze, and optimize their interactions with customers. It centralizes customer-related data into a database, enabling various departments (sales, marketing, customer service, etc.) to efficiently access and manage this information.

**Deploy**: PENDING

## Table of Contents

- [Description](#description)
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [MVP](#mvp)
- [UML](#uml)
- [User Stories](#user-stories)
- [API](#api)
- [Testing](#testing)
- [License](#license)

## Description

This CRM system allows businesses to manage their customers, products, and orders efficiently through an interactive admin panel. Users can perform CRUD operations (Create, Read, Update, Delete) on orders, with secure authentication and role-based access.

The main goal is to enhance customer relationship management while streamlining internal workflows and data handling.

## Features

- **Login and Authentication**: Secure login system using JWT and Spring Security to protect user data.
- **Active Users System**: Allows administrators to control which users are activated, granting or revoking their access to the application.
- **Profile Panel**: Enables users to edit their personal information, manage roles, and securely change their password.
- **Password Management**: Passwords are securely hashed and stored using bcrypt, ensuring robust encryption.
- **Data validation**: All input fields are validated using validator to prevent invalid or malicious data submissions.
- **Routing Security**: URL access is secured based on user roles, restricting actions and data visibility according to permissions.
- **Orders, Products & Customers**: Comprehensive management of orders, products, and customer data, forming the core functionalities to operate the application efficiently.

## Technologies

### Frontend

- **Vite**: Fast development environment.
- **React**: JavaScript framework for building user interfaces.
- **react-router-dom**: Navigation between secure and consistent routes.
- **sweetalert2**: Interactive alerts and notifications.

### Backend

- **Java**: Application logic and API development.
- **Spring Boot**: Backend framework.
- **Spring Security**: Secure authentication and role management..
- **Validate**: User input validation.
- **JWT**: Authentication via JSON Web Tokens.
- **bcrypt**: Password encryption.

### Database

- **MySQL**: Relational database for data storage.

### Tools

- **Postman**: API testing and debugging.
- **TablePlus**: SQL database management.

## Installation

### Requirements

- Node.js >= 14.x
- MySQL installed locally or available on a cloud server.
- Java 17 or above.

### Steps

1. Clone the repository:

```bash
git clone https://github.com/abelpriem/CRM-JTool.git

```

2. Install backend dependencies:

```bash
cd backend
maven install
```

3. Install frontend dependencies:

```bash
cd ../app
npm install
```

4. Run the application in development mode:

```bash
cd backend
mvn spring-boot:run
```

5. Run frontend in a separate terminal:

```bash
cd ../app
npm run dev
```

## MVP

### Config & Security

- CORS
- JWT

### Users

- User: Model
- DTOs: UserDto - RegisterRequest - LoginRequest - EditClientRequest - NewClientRequest - ChangePasswordRequest
- User Controller
- User Service
- User Repository
- Test

- [x] Create users & authenticate with credentials - JWT
- [x] Active users only by Admin
- [x] Add new clients on BD
- [x] List of all clients
- [x] Edit data users: change current password
- [x] Edit clients: all data info
- [x] Delete clients

### Products

- Product: Model
- DTOs: EditProductRequest
- Product Controller
- Product Service
- Product Repository
- Test

- [x] List of all products
- [x] Edit data products (only by Postman)
- [x] Delete products

### Pending:

- Order (TODO)
- OrderDetail (TODO)

### Next versión (?)

- [x] Create all kind of orders between users and products
- [x] Add new product (no datasql)
- [x] Active users without database management
- [x] Add payment methods
- [x] Implement more users (guest, moderator...)

## UML

![994897D1-0DD7-4218-9DFB-EABE0F3F3B40_1_201_a](https://github.com/user-attachments/assets/52fb90e0-624b-488c-94d9-15441fb4c1f4)

## User Stories

### Admin User

1. As an admin, I want to log in securely, so I can manage the system.
2. As an admin, I want to active user accounts.
3. As an admin, I want to view a list of customers, so I can easily manage their details.
4. As an admin, I want to add, edit, and delete costumers, so I can keep the costumers list up-to-date.
5. As an admin, I want to view a list of products, so I can easily manage their details.
6. As an admin, I want to add, edit, and delete products, so I can keep the product catalog up-to-date.

### User

1. As a user, I want my details to be securely stored, so my interactions with the company are efficient.
2. As a user, I want to view a list of customers, so I can place orders easily.
3. As a user, I want to view available products, so I can place orders easily.
4. As a user, I want to check my profile with my data info.
5. As a user, I want to change my current password.

## API

The API provides several endpoints for managing authentication, job postings, and users. Some example endpoints are:

### Users

- `POST api/users/auth`: Log in.
- `POST api/users/register`: Register a new user.
- `GET api/users`: Get the list of all users.
- `PATCH api/users/change-password`: Edit the current user password (ADMIN only).
- `POST api/users/clients/new-client`: Create new client (ADMIN only).
- `GET api/users/clients`: Get the list of all clients.
- `GET api/users/clients/{clientId}`: Get only the client selected (ADMIN only).
- `PATCH api/users/clients/edit/${clientId}`: Edit ALL the selected client (ADMIN only).
- `DELETE api/users/clients/delete/${clientId}`: Delete selected client (ADMIN only).

### Products

- `GET api/products`: Get the list of all products.
- `PATCH api/products/edit/{productId}`: Edit selected product (ADMIN only).
- `DELETE api/products/delete/{productId}`: Delete selected product (ADMIN only).

## Testing

Unit tests have been implemented to ensure system reliability. We use the following libraries for testing:

- **JUnit**: Framework for running tests.
- **Mockito**: Library for assertions.

To run the tests:

```bash
cd api
npm run test
```

## License

This project is licensed under the MIT License.
