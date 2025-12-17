import { useState } from "react";
import { Routes, Route, Link, useNavigate } from "react-router-dom";
import { useAuth } from "./contexts/AuthContext";
import OfficeList from "./pages/OfficeList";
import MyReservations from "./pages/MyReservations";
import CurrencyWidget from "./pages/CurrencyWidget";
import Login from "./pages/Login";
import Register from "./pages/Register";

export default function App() {
  const { currentUser, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await logout();
      navigate("/");
    } catch (error) {
      console.error("Logout failed", error);
    }
  };

  return (
    <div>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/" element={
          <>
            {/* Navigation */}
            <nav className="navbar">
              <div className="container navbar-content">
                <Link to="/" className="navbar-brand">Office Rental System</Link>
                <div className="nav-links">
                  <Link to="/" className="nav-link">Offices</Link>
                  {currentUser?.authenticated && (
                    <Link to="/reservations" className="nav-link">My Reservations</Link>
                  )}
                  {currentUser?.authenticated ? (
                    <button onClick={handleLogout} className="btn btn-outline" style={{ marginLeft: '10px' }}>
                      Logout
                    </button>
                  ) : (
                    <>
                      <Link to="/login" className="nav-link">Login</Link>
                      <Link to="/register" className="nav-link">Register</Link>
                    </>
                  )}
                </div>
              </div>
            </nav>

            {/* Main Content */}
            <div className="container" style={{ padding: '30px 0' }}>
              <Routes>
                <Route path="/" element={<OfficeList />} />
                <Route path="/reservations" element={<MyReservations />} />
              </Routes>
              
              {/* Currency Widget - Always visible */}
              <div style={{ position: "fixed", bottom: 20, right: 20 }}>
                <CurrencyWidget />
              </div>
            </div>
          </>
        } />
      </Routes>
    </div>
  );
}
