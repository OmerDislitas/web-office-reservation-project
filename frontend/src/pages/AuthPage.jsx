import { useEffect, useState } from "react";
import Login from "./Login";
import Register from "./Register";

export default function AuthPage({ onLoggedIn }) {
  const [quote, setQuote] = useState(null);

  useEffect(() => {
    // ZenQuotes API fetch
    fetch("https://zenquotes.io/api/random")
      .then((r) => r.json())
      .then((arr) => {
        const item = Array.isArray(arr) ? arr[0] : null;
        if (item?.q && item?.a) setQuote({ text: item.q, author: item.a });
      })
      .catch(() => {
        // Hata durumunda sessizce devam eder
      });
  }, []);

  return (
    <div style={{ 
      minHeight: '100vh',
      background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      padding: '40px 20px',
      fontFamily: 'system-ui, -apple-system, sans-serif'
    }}>
      <div style={{ maxWidth: '960px', width: '100%' }}>
        <h1 style={{ 
          textAlign: 'center', 
          marginBottom: '24px', 
          fontWeight: 700,
          fontSize: '36px',
          color: '#ffffff',
          letterSpacing: '-0.5px'
        }}>
          Office Rental Store
        </h1>

        
        {quote && (
          <div style={{ 
            background: 'rgba(255, 255, 255, 0.15)', 
            backdropFilter: 'blur(10px)',
            borderRadius: '16px', 
            padding: '20px', 
            marginBottom: '40px',
            textAlign: 'center',
            color: '#fff',
            border: '1px solid rgba(255, 255, 255, 0.2)',
            boxShadow: '0 4px 15px rgba(0,0,0,0.1)'
          }}>
            <div style={{ fontStyle: 'italic', fontSize: '18px', marginBottom: '8px' }}>
              "{quote.text}"
            </div>
            <div style={{ fontWeight: '600', fontSize: '14px', opacity: 0.9 }}>
              — {quote.author}
            </div>
            <div style={{ marginTop: '10px', fontSize: '10px', opacity: 0.6 }}>
              Inspirational quotes provided by ZenQuotes.io
            </div>
          </div>
        )}
        
        <div style={{ 
          display: 'grid', 
          gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', 
          gap: '24px' 
        }}>
          <div style={cardStyle}>
            <Login onGoRegister={() => {}} onLoggedIn={onLoggedIn} />
          </div>
          <div style={cardStyle}>
            <Register onGoLogin={() => {}} />
          </div>
        </div>
      </div>
    </div>
  );
}

// Ortak Card Stili
const cardStyle = {
  background: '#ffffff',
  borderRadius: '16px',
  overflow: 'hidden',
  boxShadow: '0 10px 25px rgba(0,0,0,0.1)',
  padding: '10px' // İçerideki bileşenlerin kenara yapışmaması için opsiyonel
};