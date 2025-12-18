import Login from "./Login";
import Register from "./Register";

export default function AuthPage({ onLoggedIn }) {
  return (
    <div style={{ maxWidth: 980, margin: "24px auto", padding: "0 12px" }}>
      <h1 style={{ marginBottom: 18 }}>Office Rental Store</h1>

      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 16 }}>
        <div style={card}>
          <Login onGoRegister={() => {}} onLoggedIn={onLoggedIn} />
        </div>
        <div style={card}>
          <Register onGoLogin={() => {}} />
        </div>
      </div>
    </div>
  );
}

const card = {
  border: "1px solid #ddd",
  borderRadius: 10,
  padding: 12,
};
