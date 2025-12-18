import { useState } from "react";
import { useAuth } from "./auth/AuthContext";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";

export default function App() {
  const { user, loading } = useAuth();
  const [mode, setMode] = useState("login"); // login | register

  if (loading) return <div style={{ padding: 20 }}>Loading...</div>;

  if (!user) {
    if (mode === "register") {
      return <Register onGoLogin={() => setMode("login")} />;
    }
    return <Login onGoRegister={() => setMode("register")} onLoggedIn={() => {}} />;
  }

  return <Home />;
}
