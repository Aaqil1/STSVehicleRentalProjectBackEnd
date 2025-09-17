# STS Vehicle Rental Project Backend

The repository contains three Spring Boot services that coordinate the backend of a vehicle rental platform:

| Module | Purpose | Base API Path |
| --- | --- | --- |
| `admin` | Administration of vehicles, vendors, users and feedback | `/api/admin/**` |
| `user` | End-user registration, authentication, bookings and feedback | `/api/users/**` |
| `vendor` | Vendor vehicle management and booking visibility | `/api/vendor/**` |

Each module exposes a consistent REST surface, enforces validation through DTOs and centralised exception handlers, and stores relational data via Spring Data JPA.

## Operational Profiles

All services use environment-aware configuration. Profiles are selected with `SPRING_PROFILES_ACTIVE` (default `dev`). Credentials and connection strings are externalised through environment variables, for example:

```bash
export ADMIN_DATASOURCE_URL=jdbc:mysql://localhost:3306/project
export ADMIN_DATASOURCE_USERNAME=root
export ADMIN_DATASOURCE_PASSWORD=change-me
```

For rapid testing every service ships with an in-memory H2 profile (`test`) that powers the automated test suite.

## Security

Basic authentication backed by the database is enabled in every module. Passwords are hashed with BCrypt and existing records are automatically upgraded on start-up. The public endpoints are limited to login/registration, while all management routes require authenticated requests.

## Domain Relationships

Bookings now maintain `@ManyToOne` relationships to users and vehicles so that the services can leverage JPA navigation instead of manual foreign-key integers. Vehicles and users also expose corresponding `@OneToMany` collections.

## Running Locally

```bash
cd admin && ./mvnw spring-boot:run
cd ../user && ./mvnw spring-boot:run
cd ../vendor && ./mvnw spring-boot:run
```

Set the desired profile via `SPRING_PROFILES_ACTIVE` or pass `--spring.profiles.active=dev` when launching.

## Automated Tests

All modules include focused service tests that run against the H2 profile:

```bash
cd admin && ./mvnw test
cd ../user && ./mvnw test
cd ../vendor && ./mvnw test
```

## API Highlights

- **Admin:** CRUD operations for vehicles and vendors, booking/user/feedback listings and credential validation at `/api/admin/login`.
- **User:** Registration, login, booking and payment verification endpoints along with feedback submission at `/api/users/feedback`.
- **Vendor:** Vehicle updates, deletions and booking visibility gated behind `/api/vendor/login`.

Refer to the controller classes in each module for the full list of endpoints and payload contracts.
