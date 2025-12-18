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
    <div className="modal-overlay">
      <div className="modal modal-wide">
        <h3 className="modal-title">Reviews — {office.name}</h3>

        {err && <div className="error-message">{err}</div>}

        <div className="modal-row" style={{ marginBottom: 20 }}>
          <select className="modal-input" value={rating} onChange={(e) => setRating(Number(e.target.value))}>
            {[5, 4, 3, 2, 1].map((n) => (
              <option key={n} value={n}>
                {n} Stars
              </option>
            ))}
          </select>
          <input
            className="modal-input"
            placeholder="Write a comment"
            value={comment}
            onChange={(e) => setComment(e.target.value)}
          />
          <button className="btn-primary btn-small" onClick={add}>Add</button>
        </div>

        <div className="reviews-container">
          {items.length === 0 && <div className="reviews-empty">No reviews yet.</div>}
          {items.map((r) => (
            <div key={r.id} className="review-item">
              <span className="review-rating">{r.rating}/5</span>
              <div className="review-comment">{r.comment}</div>
            </div>
          ))}
        </div>

        <div className="modal-actions">
          <button className="btn-secondary" onClick={onClose}>Close</button>
        </div>
      </div>
    </div>
  );
}

