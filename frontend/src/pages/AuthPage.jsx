import Login from "./Login";
import Register from "./Register";

export default function AuthPage({ onLoggedIn }) {
  return (
    <div className="home-container">
      <h1 style={{ textAlign: 'center', marginBottom: 40, fontWeight: 800 }}>Office Rental Store</h1>
      
      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 30 }}>
        {/* CSS sınıfları sayesinde card objesine gerek kalmadı */}
        <div className="auth-card">
          <Login onGoRegister={() => {}} onLoggedIn={onLoggedIn} />
        </div>
        <div className="auth-card">
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
