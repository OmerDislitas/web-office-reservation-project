import { useEffect, useState } from "react";
import { apiFetch } from "../api/client";

export default function MyReservations({ onBack }) {
  const [items, setItems] = useState([]);
  const [err, setErr] = useState("");

  async function load() {
    setErr("");
    try {
      const data = await apiFetch("/api/reservations/mine");
      setItems(data || []);
    } catch (e) {
      setErr(e.message);
    }
  }

  useEffect(() => {
    load();
  }, []);

  return (
    <div style={{ maxWidth: 980, margin: "20px auto", padding: "0 12px" }}>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <h2>My Reservations</h2>
        <button onClick={onBack}>Back</button>
      </div>

      {err && <p style={{ color: "crimson" }}>{err}</p>}

      <div style={{ display: "grid", gap: 10 }}>
        {items.length === 0 && <p>No reservations yet.</p>}

        {items.map((r) => (
          <div key={r.id} style={card}>
            <div><b>Office:</b> {r.officeName}</div>
            <div><b>Date:</b> {r.startDate} → {r.endDate} ({r.totalDays} days)</div>
            <div><b>Total:</b> {r.totalPrice}</div>
            {r.status && <div><b>Status:</b> {r.status}</div>}
          </div>
        ))}
      </div>
    </div>
  );
}

const card = {
  border: "1px solid #ddd",
  borderRadius: 10,
  padding: 12,
};
