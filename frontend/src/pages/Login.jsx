import { useState } from "react";
import { useAuth } from "../auth/AuthContext";

export default function Login({ onGoRegister, onLoggedIn }) {
  const { login } = useAuth();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [err, setErr] = useState("");
  const [busy, setBusy] = useState(false);

  async function submit(e) {
    e.preventDefault();
    setErr("");
    setBusy(true);
    try {
      await login(email, password);
      onLoggedIn();
    } catch (e2) {
      setErr(e2.message || "Login failed");
    } finally {
      setBusy(false);
    }
  }

  return (
    <div className="auth-card">
      <h2 className="auth-title">Login</h2>
      {err && <div className="auth-error">{err}</div>}
      <form className="auth-form" onSubmit={submit}>
        <input
          className="auth-input"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          className="auth-input"
          placeholder="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button className="auth-button" disabled={busy}>
          {busy ? "..." : "Login"}
        </button>
      </form>

      <div className="auth-footer">
        No account?{" "}
        <button onClick={onGoRegister} className="auth-link">
          Register
        </button>
      </div>
    </div>
  );
}
