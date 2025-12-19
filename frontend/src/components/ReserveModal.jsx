import { useState } from "react";
import ReactDOM from "react-dom";
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

    const ok = window.confirm("Are you sure?");
    if (!ok) return;

    setBusy(true);
    try {
      const res = await apiFetch(
        `/api/reservations?officeId=${office.id}&startDate=${startDate}&endDate=${endDate}`,
        { method: "POST" }
      );

      const reservationId = res?.reservationId;
      if (!reservationId) {
        throw new Error("Reservation created but id missing");
      }

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

  return ReactDOM.createPortal(
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <h3 className="modal-title">Reserve — {office.name}</h3>

        {err && <div className="error-message">{err}</div>}

        <div className="modal-form">
          <div className="modal-row">
            <input
              className="modal-input"
              type="date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
            />
            <input
              className="modal-input"
              type="date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
            />
          </div>
        </div>

        <div className="modal-actions">
          <button className="btn-secondary" onClick={onClose}>Close</button>
          <button className="btn-primary" onClick={reserve} disabled={busy}>
            {busy ? "Processing..." : "Confirm Reservation"}
          </button>
        </div>
      </div>
    </div>,
    document.body
  );
}

