import Login from "./Login";
import Register from "./Register";

export default function AuthPage({ onLoggedIn }) {
  return (
    <div style={{ 
      minHeight: '100vh',
      background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      padding: '40px 20px'
    }}>
      <div style={{ maxWidth: '960px', width: '100%' }}>
        <h1 style={{ 
          textAlign: 'center', 
          marginBottom: '48px', 
          fontWeight: 700,
          fontSize: '36px',
          color: '#ffffff',
          letterSpacing: '-0.5px'
        }}>
          Office Rental Store
        </h1>
        
        <div className="auth-grid">
          <div style={{ background: '#ffffff', borderRadius: '16px', overflow: 'hidden' }}>
            <Login onGoRegister={() => {}} onLoggedIn={onLoggedIn} />
          </div>
          <div style={{ background: '#ffffff', borderRadius: '16px', overflow: 'hidden' }}>
            <Register onGoLogin={() => {}} />
          </div>
        </div>
      </div>
    </div>
  );
}
