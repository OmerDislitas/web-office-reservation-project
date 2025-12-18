# Office Rental System

A full-stack office rental system.

- **Normal users** use the site via **React (Vercel) + REST API**
- **Admin** uses a **Thymeleaf panel (Render)** to approve office listings and manage reservations
- **Database** is **MySQL (Aiven)**
- **Authentication**: Cookie/Session (JSESSIONID)

---

## Live URLs

- **Frontend (Vercel):** https://web-office-reservation-project.vercel.app
- **Backend (Render):** https://web-office-reservation-project.onrender.com
- **Admin Login:** https://web-office-reservation-project.onrender.com/admin/login

---

## Features

### User (React)
- Register / Login (email + password)
- List approved offices
- Search offices by **name** (simple search bar)
- Reserve an office with **startDate / endDate**
- Reservation date-conflict protection (overlapping dates are blocked)
- Reviews:
  - Anyone can write reviews
  - View existing reviews

### Admin (Thymeleaf)
- Admin login (form-based)
- Approve / Reject office requests
- View all reservations
- Delete reservations (payments linked to reservation are handled)

### Pricing
- Office has a **dailyPrice**
- Reservation total price is calculated as:
  - `totalDays = daysBetween(startDate, endDate)`
  - `totalPrice = totalDays * dailyPrice`

### Payments (Simulated)
- No real card payment.
- A **payment record** is created as a simulated payment after reservation.

### Geocoding (OpenStreetMap / Nominatim)
- When admin approves an office, the system converts `address` → `latitude/longitude`
- Coordinates are stored in the `offices` table

---

## Demo Flow (Recommended)

1) Open the frontend:
   - https://web-office-reservation-project.vercel.app

2) Register and login (user)

3) Create an office request:
   - Click **Create office reservation**
   - Fill in details and submit (it will be **PENDING**)

4) Admin approval:
   - Go to https://web-office-reservation-project.onrender.com/admin/login
   - Login as admin
   - Approve from **Pending Offices**

5) Back to frontend:
   - Approved office appears in the list

6) Reserve:
   - Click **Reserve**
   - Pick start/end dates
   - Confirm “Emin misin?”
   - Reservation is created + simulated payment is recorded

7) Reviews:
   - Click **Reviews**
   - View and add a review

---

## Admin Credentials

- **Email:** admin@office.com
- **Password:** Admin123!

---

## Main Entities / Tables

- `users`
- `offices`
- `reservations`
- `payments`
- `reviews`

---

## Notes

- Backend uses a controller-service-repository architecture.
- Cookie/session authentication is used (JSESSIONID).
- In production, session cookies are configured to work cross-site with Vercel:
  - `SameSite=None` and `Secure=true`.

---
