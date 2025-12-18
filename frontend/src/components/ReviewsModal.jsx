import { useEffect, useState } from "react";
import { apiFetch } from "../api/client";

export default function ReviewsModal({ office, open, onClose }) {
  const [items, setItems] = useState([]);
  const [rating, setRating] = useState(5);
  const [comment, setComment] = useState("");
  const [err, setErr] = useState("");

  async function load() {
    const data = await apiFetch(`/api/reviews?officeId=${office.id}`);
    setItems(data || []);
  }

  useEffect(() => {
    if (open) load().catch(() => {});
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [open]);

  if (!open) return null;

  async function add() {
    setErr("");
    try {
      await apiFetch(`/api/reviews?officeId=${office.id}&rating=${rating}&comment=${encodeURIComponent(comment)}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
      });
      setComment("");
      await load();
    } catch (e) {
      setErr(e.message);
    }
  }

  return (
    <div style={overlay}>
      <div style={modal}>
        <h3>Reviews — {office.name}</h3>

        {err && <p style={{ color: "crimson" }}>{err}</p>}

        <div style={{ display: "flex", gap: 8, marginBottom: 12 }}>
          <select value={rating} onChange={(e) => setRating(Number(e.target.value))}>
            {[5, 4, 3, 2, 1].map((n) => (
              <option key={n} value={n}>
                {n}
              </option>
            ))}
          </select>
          <input
            placeholder="Write a comment"
            value={comment}
            onChange={(e) => setComment(e.target.value)}
            style={{ flex: 1, padding: 8 }}
          />
          <button onClick={add}>Add</button>
        </div>

        <div style={{ maxHeight: 260, overflow: "auto", border: "1px solid #ddd", padding: 10 }}>
          {items.length === 0 && <p>No reviews yet.</p>}
          {items.map((r) => (
            <div key={r.id} style={{ borderBottom: "1px solid #eee", padding: "8px 0" }}>
              <b>{r.rating}/5</b> — {r.comment}
            </div>
          ))}
        </div>

        <div style={{ marginTop: 12, textAlign: "right" }}>
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
  width: "min(720px, 100%)",
  borderRadius: 10,
  padding: 16,
};
