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
        <div className="home-container">
            <div className="topbar">
                <h2 style={{ margin: 0 }}>My Reservations</h2>
                <button className="btn-secondary" onClick={onBack}>Back to Home</button>
            </div>

            {err && <div className="error-message">{err}</div>}

            <div className="offices-grid">
                {items.length === 0 && (
                    <div className="reviews-empty" style={{ gridColumn: '1 / -1', padding: '48px 24px' }}>
                        No reservations yet.
                    </div>
                )}

                {items.map((r) => (
                    <div key={r.id} className="office-card">
                        <h3 className="office-title">{r.officeName}</h3>
                        <div className="office-address">
                            {r.startDate} → {r.endDate} ({r.totalDays} days)
                        </div>
                        <div className="office-price">
                            <span className="office-price-amount">${r.totalPrice}</span>
                        </div>
                        {r.status && (
                            <div style={{ 
                                marginTop: '12px', 
                                padding: '6px 12px', 
                                background: '#f0fff4', 
                                color: '#38a169',
                                borderRadius: '6px',
                                fontSize: '13px',
                                fontWeight: '600',
                                textAlign: 'center'
                            }}>
                                {r.status}
                            </div>
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
}
