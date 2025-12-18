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
    <div className="modal-overlay">
      <div className="modal">
        <h3 className="modal-title">Reserve — {office.name}</h3>

        {err && <div className="error-message">{err}</div>}

        <div className="modal-row" style={{ marginBottom: 20 }}>
          <input className="modal-input" type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
          <input className="modal-input" type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
        </div>

        <div className="modal-actions">
          <button className="btn-secondary" onClick={onClose}>Close</button>
          <button className="btn-primary" onClick={reserve} disabled={busy}>
            {busy ? "..." : "Finish"}
          </button>
        </div>
      </div>
    </div>
  );
}

