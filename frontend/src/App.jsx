import { useState } from "react";
import { useAuth } from "./auth/AuthContext";
import AuthPage from "./pages/AuthPage";
import Home from "./pages/Home";
import MyReservations from "./pages/MyReservations";

export default function App() {
  const { user, loading } = useAuth();
  const [page, setPage] = useState("home"); // home | myres

  if (loading) return <div style={{ padding: 20 }}>Loading...</div>;

  if (!user) {
    return <AuthPage onLoggedIn={() => setPage("home")} />;
  }

  if (page === "myres") {
    return <MyReservations onBack={() => setPage("home")} />;
  }

  return <Home onGoMyReservations={() => setPage("myres")} />;
}
