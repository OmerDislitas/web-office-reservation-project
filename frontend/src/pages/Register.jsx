import { useState } from "react";
import { useAuth } from "../auth/AuthContext";

export default function Register({ onGoLogin }) {
  const { register } = useAuth();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [err, setErr] = useState("");
  const [ok, setOk] = useState("");
  const [busy, setBusy] = useState(false);

  async function submit(e) {
    e.preventDefault();
    setErr("");
    setOk("");
    setBusy(true);
    try {
      await register({ name, email, password, confirmPassword });
      setOk("Registered! Now login.");
    } catch (e2) {
      setErr(e2.message || "Register failed");
    } finally {
      setBusy(false);
    }
  }

  return (
    <div style={{ maxWidth: 420, margin: "40px auto" }}>
      <h2>Register</h2>
      {err && <p style={{ color: "crimson" }}>{err}</p>}
      {ok && <p style={{ color: "green" }}>{ok}</p>}
      <form onSubmit={submit}>
        <div style={{ marginBottom: 10 }}>
          <input placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} style={{ width: "100%", padding: 10 }} />
        </div>
        <div style={{ marginBottom: 10 }}>
          <input placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} style={{ width: "100%", padding: 10 }} />
        </div>
        <div style={{ marginBottom: 10 }}>
          <input placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} style={{ width: "100%", padding: 10 }} />
        </div>
        <div style={{ marginBottom: 10 }}>
          <input
            placeholder="Confirm Password"
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            style={{ width: "100%", padding: 10 }}
          />
        </div>
        <button disabled={busy} style={{ padding: 10, width: "100%" }}>
          {busy ? "..." : "Create account"}
        </button>
      </form>

      <p style={{ marginTop: 12 }}>
        Have an account?{" "}
        <button onClick={onGoLogin} style={{ border: "none", background: "none", color: "blue", cursor: "pointer" }}>
          Login
        </button>
      </p>
    </div>
  );
}
