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
    <div className="auth-container">
      <div className="auth-card">
        <h2 className="auth-title">Register</h2>
        {err && <div className="auth-error">{err}</div>}
        {ok && <div className="auth-success">{ok}</div>}
        <form className="auth-form" onSubmit={submit}>
          <input className="auth-input" placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} />
          <input className="auth-input" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
          <input className="auth-input" placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          <input
            className="auth-input"
            placeholder="Confirm Password"
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />
          <button className="auth-button" disabled={busy}>
            {busy ? "..." : "Create account"}
          </button>
        </form>

        <div className="auth-footer">
          Have an account?{" "}
          <button onClick={onGoLogin} className="auth-link">
            Login
          </button>
        </div>
      </div>
    </div>
  );
}
