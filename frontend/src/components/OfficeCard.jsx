import { useState } from "react";
import ReviewsModal from "./ReviewsModal";
import ReserveModal from "./ReserveModal";

export default function OfficeCard({ office }) {
  const [reviewsOpen, setReviewsOpen] = useState(false);
  const [reserveOpen, setReserveOpen] = useState(false);

  return (
    <div style={card}>
      <h3 style={{ margin: "0 0 6px" }}>{office.name}</h3>
      <div style={{ fontSize: 14, color: "#444" }}>{office.address}</div>
      <div style={{ marginTop: 8 }}>
        <b>{office.dailyPrice}</b> / day
      </div>

      <div style={{ display: "flex", gap: 8, marginTop: 12 }}>
        <button onClick={() => setReserveOpen(true)}>Reserve</button>
        <button onClick={() => setReviewsOpen(true)}>Reviews</button>
      </div>

      <ReserveModal office={office} open={reserveOpen} onClose={() => setReserveOpen(false)} />
      <ReviewsModal office={office} open={reviewsOpen} onClose={() => setReviewsOpen(false)} />
    </div>
  );
}

const card = {
  border: "1px solid #ddd",
  borderRadius: 10,
  padding: 14,
};
