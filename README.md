# Office Rental System

A full-stack web application for renting office spaces with user and admin functionalities.

## Tech Stack

- **Backend**: Java + Spring Boot 3.x
- **Database**: MySQL with JPA/Hibernate
- **Frontend**: React with Vite
- **Server-side Rendering**: Thymeleaf for admin pages
- **Security**: Spring Security with role-based access control

## Features

### User Features
- Browse and search office listings with filters (city, price range, size)
- View office details
- Reserve offices with date selection (confirmation required)
- View personal reservations
- Add and view reviews for offices

### Admin Features
- Dashboard with statistics (total offices, pending reservations)
- Manage offices (create, view, delete)
- Manage reservations (approve/reject pending reservations)
- Delete inappropriate reviews

### External API Integration
- Currency exchange rates widget displaying USD conversion rates

## Database Schema

The application uses 5 tables:
1. `users` - User accounts with roles (USER, ADMIN)
2. `offices` - Office listings with details
3. `reservations` - Office bookings with status tracking
4. `payments` - Payment records (table exists but no UI/logic implemented)
5. `reviews` - User reviews for offices

## Setup Instructions

### Prerequisites
- Java 17+
- Maven
- Node.js 16+
- MySQL database

### Backend Setup

1. Configure database connection in `backend/src/main/resources/application.properties`
2. Navigate to the backend directory:
   ```
   cd backend
   ```
3. Build and run the application:
   ```
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```
   mvnw.cmd spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory:
   ```
   cd frontend
   ```
2. Install dependencies:
   ```
   npm install
   ```
3. Start the development server:
   ```
   npm run dev
   ```

## Usage

1. Access the frontend at `http://localhost:5173`
2. Access the admin panel at `http://localhost:8080/admin` (login required)
3. Register a new account or use default credentials if configured

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user

### Offices
- `GET /api/offices` - List all offices (with optional filters)
- `GET /api/offices/{id}` - Get office by ID
- `POST /api/offices` - Create new office (ADMIN only)
- `PUT /api/offices/{id}` - Update office (ADMIN only)
- `DELETE /api/offices/{id}` - Delete office (ADMIN only)

### Reservations
- `POST /api/reservations` - Create new reservation
- `GET /api/reservations/my` - Get current user's reservations
- `GET /api/reservations/pending` - Get pending reservations (ADMIN only)
- `POST /api/reservations/{id}/approve` - Approve reservation (ADMIN only)
- `POST /api/reservations/{id}/reject` - Reject reservation (ADMIN only)

### Reviews
- `POST /api/reviews` - Create new review
- `GET /api/reviews/office/{officeId}` - Get reviews for an office
- `DELETE /api/reviews/{id}` - Delete review (ADMIN only)

### Admin
- `GET /api/admin/stats` - Get dashboard statistics (ADMIN only)

## Security

- Role-based access control with USER and ADMIN roles
- Form-based login with Spring Security
- CORS configured for frontend-backend communication
- Password encryption with BCrypt

## License

This project is for educational purposes only.