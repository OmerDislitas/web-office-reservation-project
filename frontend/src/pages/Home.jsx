import { useEffect, useState } from "react";
import { apiFetch } from "../api/client";
import { useAuth } from "../auth/AuthContext";
import OfficeCard from "../components/OfficeCard";

export default function Home() {
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
    <div style={{ maxWidth: 980, margin: "20px auto", padding: "0 12px" }}>
      <div style={topbar}>
        <div>
          <b>Office Rental System</b>
          <div style={{ fontSize: 13, color: "#555" }}>Logged in as: {user?.email}</div>
        </div>
        <div style={{ display: "flex", gap: 8 }}>
          <button onClick={() => setCreateOpen(true)}>Create office reservation</button>
          <button onClick={logout}>Logout</button>
        </div>
      </div>

      {err && <p style={{ color: "crimson" }}>{err}</p>}

      <div style={{ display: "flex", gap: 8, margin: "16px 0" }}>
        <input
          placeholder="Search office by name"
          value={q}
          onChange={(e) => setQ(e.target.value)}
          style={{ flex: 1, padding: 10 }}
        />
        <button onClick={load}>Search</button>
      </div>

      <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(260px, 1fr))", gap: 12 }}>
        {items.map((o) => (
          <OfficeCard key={o.id} office={o} />
        ))}
      </div>

      {createOpen && (
        <div style={overlay}>
          <div style={modal}>
            <h3>Create office reservation (request)</h3>

            <div style={{ display: "grid", gap: 8 }}>
              <input placeholder="Name" value={newOffice.name} onChange={(e) => setNewOffice({ ...newOffice, name: e.target.value })} />
              <input placeholder="Description" value={newOffice.description} onChange={(e) => setNewOffice({ ...newOffice, description: e.target.value })} />
              <input placeholder="Address" value={newOffice.address} onChange={(e) => setNewOffice({ ...newOffice, address: e.target.value })} />
              <input
                placeholder="Daily Price"
                type="number"
                value={newOffice.dailyPrice}
                onChange={(e) => setNewOffice({ ...newOffice, dailyPrice: Number(e.target.value) })}
              />
              <button onClick={submitOffice}>Submit</button>
              <button onClick={() => setCreateOpen(false)}>Close</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

const topbar = {
  display: "flex",
  justifyContent: "space-between",
  alignItems: "center",
  borderBottom: "1px solid #eee",
  paddingBottom: 12,
};

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
