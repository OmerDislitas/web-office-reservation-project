import { createContext, useContext, useState, useEffect } from "react";
import api from "../services/api";

const AuthContext = createContext();

export function useAuth() {
  return useContext(AuthContext);
}

export function AuthProvider({ children }) {
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Check if user is authenticated on app load
  useEffect(() => {
    checkAuthStatus();
  }, []);

  const checkAuthStatus = async () => {
    try {
      // Make a request to a protected endpoint to check auth status
      await api.get("/api/offices?page=0&size=1");
      // If successful, user is authenticated
      setCurrentUser({ authenticated: true });
    } catch (error) {
      // If unauthorized, user is not authenticated
      if (error.response?.status === 401) {
        setCurrentUser(null);
      } else {
        // For other errors, we assume user is authenticated
        setCurrentUser({ authenticated: true });
      }
    } finally {
      setLoading(false);
    }
  };

  const login = async (email, password) => {
    try {
      // Create form data for Spring Security
      const formData = new FormData();
      formData.append("username", email);
      formData.append("password", password);
      
      // Use the traditional form login endpoint
      await api.post("/login", formData, {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });
      
      setCurrentUser({ authenticated: true });
      return { success: true };
    } catch (error) {
      return { 
        success: false, 
        message: error.response?.data?.message || "Login failed" 
      };
    }
  };

  const register = async (fullName, email, password) => {
    try {
      await api.post("/api/auth/register", { fullName, email, password });
      return { success: true };
    } catch (error) {
      return { 
        success: false, 
        message: error.response?.data?.message || "Registration failed" 
      };
    }
  };

  const logout = async () => {
    try {
      await api.post("/logout");
      setCurrentUser(null);
      return { success: true };
    } catch (error) {
      // Even if logout fails, clear local state
      setCurrentUser(null);
      return { 
        success: true, // Still consider it successful locally
        message: error.response?.data?.message 
      };
    }
  };

  const value = {
    currentUser,
    login,
    register,
    logout,
    loading
  };

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  );
}