import { useState } from "react";
import ReviewsModal from "./ReviewsModal";
import ReserveModal from "./ReserveModal";

export default function OfficeCard({ office }) {
  const [reviewsOpen, setReviewsOpen] = useState(false);
  const [reserveOpen, setReserveOpen] = useState(false);

  return (
    <div className="office-card">
      <h3 className="office-title">{office.name}</h3>
      <div className="office-address">{office.address}</div>
      <div className="office-price">
        <span className="office-price-amount">${office.dailyPrice}</span> / day
      </div>

      <div className="office-actions">
        <button className="btn-card btn-reserve" onClick={() => setReserveOpen(true)}>Reserve</button>
        <button className="btn-card btn-reviews" onClick={() => setReviewsOpen(true)}>Reviews</button>
      </div>

      <ReserveModal office={office} open={reserveOpen} onClose={() => setReserveOpen(false)} />
      <ReviewsModal office={office} open={reviewsOpen} onClose={() => setReviewsOpen(false)} />
    </div>
  );
}

