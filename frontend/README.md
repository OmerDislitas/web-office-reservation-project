# Office Rental System - Frontend

This is the React frontend for the Office Rental System application.

## Features Implemented

### Authentication
- Login page with form validation
- Registration page with password confirmation
- Logout functionality
- Session-based authentication using Spring Security
- Protected routes for authenticated users

### UI/UX Improvements
- Modern, clean design with consistent styling
- Responsive layout that works on mobile and desktop
- Consistent color scheme and typography
- Card-based components for better visual hierarchy
- Improved form controls and inputs
- Status indicators for reservations
- Better error handling and user feedback

### Pages
1. **Office List** - Browse and search offices with filters
2. **My Reservations** - View personal reservations
3. **Login** - Authenticate existing users
4. **Register** - Create new user accounts

### Components
- **Currency Widget** - Displays live currency exchange rates
- **Navigation Bar** - Context-aware navigation with auth state
- **Office Cards** - Clean presentation of office information
- **Reservation Forms** - Intuitive date selection with confirmation
- **Review System** - Add and view office reviews

## Technical Details

### Dependencies
- React with Vite
- React Router for navigation
- Axios for API calls
- Custom CSS for styling

### Architecture
- Component-based structure
- Context API for state management
- Reusable UI components
- Consistent styling through global CSS

### Authentication Flow
1. User visits login or register page
2. Credentials sent to Spring Security backend
3. Session cookie stored in browser
4. Auth context tracks login state
5. Protected routes check authentication status

## File Structure
```
src/
├── components/          # Reusable UI components
├── contexts/           # React context providers
├── pages/              # Page components
├── services/           # API service configuration
├── styles.css          # Global styles
├── index.css           # Base styles
├── App.jsx             # Main application component
└── main.jsx            # Application entry point
```

## Styling
- Custom CSS with modern color palette
- Responsive grid system
- Consistent spacing and typography
- Interactive elements with hover effects
- Status indicators with appropriate colors