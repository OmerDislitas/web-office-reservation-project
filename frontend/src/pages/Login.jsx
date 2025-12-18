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
    <div style={{ maxWidth: 420, margin: "40px auto" }}>
      <h2>Login</h2>
      {err && <p style={{ color: "crimson" }}>{err}</p>}
      <form onSubmit={submit}>
        <div style={{ marginBottom: 10 }}>
          <input
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            style={{ width: "100%", padding: 10 }}
          />
        </div>
        <div style={{ marginBottom: 10 }}>
          <input
            placeholder="Password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={{ width: "100%", padding: 10 }}
          />
        </div>
        <button disabled={busy} style={{ padding: 10, width: "100%" }}>
          {busy ? "..." : "Login"}
        </button>
      </form>

      <p style={{ marginTop: 12 }}>
        No account?{" "}
        <button onClick={onGoRegister} style={{ border: "none", background: "none", color: "blue", cursor: "pointer" }}>
          Register
        </button>
      </p>
    </div>
  );
}
