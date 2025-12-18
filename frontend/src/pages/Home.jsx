import { useEffect, useState } from "react";
import { apiFetch } from "../api/client";
import { useAuth } from "../auth/AuthContext";
import OfficeCard from "../components/OfficeCard";

export default function Home({ onGoMyReservations }) {
  const { user, logout } = useAuth();
  const [q, setQ] = useState("");
  const [items, setItems] = useState([]);
  const [err, setErr] = useState("");

  const [createOpen, setCreateOpen] = useState(false);
  const [newOffice, setNewOffice] = useState({
    name: "",
    description: "",
    address: "",
    dailyPrice: 100,
  });

  async function load() {
    setErr("");
    try {
      const url = q ? `/api/offices?name=${encodeURIComponent(q)}` : "/api/offices";
      const data = await apiFetch(url, { method: "GET" });
      setItems(data || []);
    } catch (e) {
      setErr(e.message);
    }
  }

  useEffect(() => {
    load();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  async function submitOffice() {
    setErr("");
    try {
      await apiFetch("/api/offices", {
        method: "POST",
        body: JSON.stringify(newOffice),
      });
      alert("Office request submitted! Admin approval required.");
      setCreateOpen(false);
      setNewOffice({ name: "", description: "", address: "", dailyPrice: 100 });
    } catch (e) {
      setErr(e.message);
    }
  }

  return (
    <div className="home-container">
      <div className="topbar">
        <div>
          <div className="topbar-brand">Office Rental Shop</div>
          <div className="topbar-user">Logged in as: {user?.email}</div>
        </div>
        <div className="topbar-actions">
          <button className="btn-primary" onClick={onGoMyReservations}>My Reservations</button>

          <button className="btn-primary" onClick={() => setCreateOpen(true)}>Create office reservation</button>
          <button className="btn-secondary" onClick={logout}>Logout</button>
        </div>
      </div>

      {err && <div className="error-message">{err}</div>}

      <div className="search-bar">
        <input
          className="search-input"
          placeholder="Search office by name"
          value={q}
          onChange={(e) => setQ(e.target.value)}
        />
        <button className="btn-primary" onClick={load}>Search</button>
      </div>

      <div className="offices-grid">
        {items.map((o) => (
          <OfficeCard key={o.id} office={o} />
        ))}
      </div>

      {createOpen && (
        <div className="modal-overlay">
          <div className="modal">
            <h3 className="modal-title">Create office reservation (request)</h3>

            <div className="modal-form">
              <input className="modal-input" placeholder="Name" value={newOffice.name} onChange={(e) => setNewOffice({ ...newOffice, name: e.target.value })} />
              <input className="modal-input" placeholder="Description" value={newOffice.description} onChange={(e) => setNewOffice({ ...newOffice, description: e.target.value })} />
              <input className="modal-input" placeholder="Address" value={newOffice.address} onChange={(e) => setNewOffice({ ...newOffice, address: e.target.value })} />
              <input
                className="modal-input"
                placeholder="Daily Price"
                type="number"
                value={newOffice.dailyPrice}
                onChange={(e) => setNewOffice({ ...newOffice, dailyPrice: Number(e.target.value) })}
              />
            </div>
            <div className="modal-actions">
              <button className="btn-secondary" onClick={() => setCreateOpen(false)}>Close</button>
              <button className="btn-primary" onClick={submitOffice}>Submit</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
