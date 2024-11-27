# devJobs [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)[![Netlify Status](https://api.netlify.com/api/v1/badges/633082cd-8c9e-4c2e-b2fc-b3063e66d8b2/deploy-status)](https://app.netlify.com/sites/portfolio-abelprieto-fullstack/deploys)

![11AF1495-A4CF-42B6-852F-2AFD3C44E337_1_201_a](https://github.com/user-attachments/assets/db4ee1f8-494a-4e20-a339-f15ac84b9e23)

The CRM is a system designed to help companies manage, analyze, and optimize their interactions with customers. It centralizes customer-related data into a database, enabling various departments (sales, marketing, customer service, etc.) to efficiently access and manage this information.

**Deploy**: PENDING

## Table of Contents

- [Description](#description)
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [API](#api)
- [UML Diagram](#uml)
- [User Stories](#stories)
- [Testing](#testing)
- [License](#license)

## Description

devJobs is a job portal focused on developers and recruiters. Recruiters can post, edit, and delete job offers, as well as manage candidates and review their resumes. Candidates can search for job offers, apply, and send their information directly to recruiters.

The system also features authentication functionalities to ensure that only authorized users can access certain admin panels.

## Features

- **Login and Authentication**: Secure login with JWT and Spring Security.
- **Admin panel**: Allows recruiters to manage their job postings, edit their personal data, and review candidate resumes.
- **Job search**: On the main page, candidates can filter job offers by keywords.
- **Authentication and authorization**: Users can register, log in, and access features according to their role.
- **Password management**: Implemented with bcrypt for secure encryption.
- **File upload**: Candidates can upload their resumes using multer.
- **Data validation**: Input field validation with validator.

## Technologies

### Frontend

- **Vite**: Fast development environment.
- **React**: JavaScript framework for building user interfaces.
- **react-router-dom**: Navigation between secure and consistent routes.
- **sweetalert2**: Interactive alerts and notifications.

### Backend

- **NodeJS**: Server-side JavaScript runtime environment.
- **Express**: Minimalist framework for building APIs.
- **JWT**: Authentication via JSON Web Tokens.
- **bcrypt**: Password encryption.
- **multer**: File uploads on the local server.
- **validator**: User input validation.
- **mocha/chai**: Libraries for testing and unit tests.

### Database

- **MongoDB**: Non-relational database to store user information, job vacancies, and applications.

## Installation

### Requirements

- Node.js >= 14.x
- MongoDB installed locally or access to a cloud instance.

### Steps

1. Clone the repository:

```bash
git clone https://github.com/tuusuario/devJobs.git

```

2. Install backend dependencies:

```bash
cd api
npm install
```

3. Install frontend dependencies:

```bash
cd ../app
npm install
```

4. Create a .env file in the backend directory with the following environment variables:

```bash
MONGO_URI=tu_mongo_uri
JWT_SECRET=tu_jwt_secret
```

5. Run the application in development mode:

```bash
cd api
node .
```

6. Run frontend in a separate terminal:

```bash
cd ../app
npm run dev
```

### Usage

1. Recruiters can register and create job postings from the admin panel.
2. Candidates can search and filter job offers on the main page, as well as apply by submitting their data and resume.
3. Recruiters can manage their job postings and view the list of candidates from their admin panel.

### API

The API provides several endpoints for managing authentication, job postings, and users. Some example endpoints are:

- `POST /users/auth`: Log in.
- `POST /users/create`: Register a new user.
- `GET /offers`: Get the list of job postings.
- `POST /offers/create`: Create a new job posting (recruiters only).
- `PUT /offers/edit/:offerUrl`: Edit a job posting (recruiters only).
- `DELETE /offer/:offerUrl`: Delete a job posting (recruiters only).

### Testing

Unit tests have been implemented to ensure system reliability. We use the following libraries for testing:

- **Mocha**: Framework for running tests.
- **Chai**: Library for assertions.

To run the tests:

```bash
cd api
npm run test

```
