import { useEffect, useState } from "react";
import api from "../services/api";

export default function MyReservations() {
  const [reservations, setReservations] = useState([]);
  const [error, setError] = useState("");

  const loadReservations = () => {
    api.get("/api/reservations/my")
      .then((res) => {
        setReservations(res.data);
        setError("");
      })
      .catch(() => setError("Failed to load reservations"));
  };

  useEffect(() => {
    loadReservations();
  }, []);

  return (
    <div className="container">
      <h1 className="page-title">My Reservations</h1>
      
      {error && <div className="alert alert-danger">{error}</div>}
      
      {reservations.length === 0 ? (
        <div className="card" style={{ textAlign: 'center', padding: '40px' }}>
          <h3>You have no reservations</h3>
          <p style={{ marginTop: '10px' }}>Browse offices and make a reservation today!</p>
        </div>
      ) : (
        <div className="grid grid-cols-2">
          {reservations.map((r) => (
            <div key={r.id} className="card">
              <h3 style={{ marginBottom: '15px' }}>{r.office.title}</h3>
              <p style={{ marginBottom: '10px' }}><strong>Location:</strong> {r.office.city}</p>
              <p style={{ marginBottom: '10px' }}><strong>Dates:</strong> {r.startDate} to {r.endDate}</p>
              <p style={{ marginBottom: '10px' }}>
                <strong>Status:</strong> 
                <span className={`status-${r.status.toLowerCase()}`}>
                  {r.status}
                </span>
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}