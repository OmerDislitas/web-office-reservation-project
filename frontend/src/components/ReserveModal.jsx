import { useState } from "react";
import { apiFetch } from "../api/client";

export default function ReserveModal({ office, open, onClose, onDone }) {
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [err, setErr] = useState("");
  const [busy, setBusy] = useState(false);

  if (!open) return null;

  async function reserve() {
  setErr("");

  if (!startDate || !endDate) {
    setErr("Pick start and end date");
    return;
  }

  const ok = window.confirm("Emin misin?");
  if (!ok) return;

  setBusy(true);
  try {
    // reservation -> id dönüyor
    const res = await apiFetch(
      `/api/reservations?officeId=${office.id}&startDate=${startDate}&endDate=${endDate}`,
      { method: "POST" }
    );

    const reservationId = res?.reservationId;
    if (!reservationId) {
      throw new Error("Reservation created but id missing");
    }

    // payment (fake)
    await apiFetch(`/api/payments?reservationId=${reservationId}`, {
      method: "POST",
    });

    alert("Reservation completed (payment recorded).");
    onDone?.();
    onClose();
  } catch (e) {
    setErr(e.message);
  } finally {
    setBusy(false);
  }
}


  return (
    <div style={overlay}>
      <div style={modal}>
        <h3>Reserve — {office.name}</h3>

        {err && <p style={{ color: "crimson" }}>{err}</p>}

        <div style={{ display: "flex", gap: 8, marginBottom: 12 }}>
          <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
          <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
          <button onClick={reserve} disabled={busy}>
            {busy ? "..." : "Finish"}
          </button>
        </div>

        <div style={{ textAlign: "right" }}>
          <button onClick={onClose}>Close</button>
        </div>
      </div>
    </div>
  );
}

const overlay = {
  position: "fixed",
  inset: 0,
  background: "rgba(0,0,0,0.35)",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  padding: 16,
};

const modal = {
  background: "white",
  width: "min(520px, 100%)",
  borderRadius: 10,
  padding: 16,
};
