import { useEffect, useState } from "react";
import api from "../services/api";

export default function CurrencyWidget() {
  const [rates, setRates] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    // Using a free currency API
    fetch("https://api.fxratesapi.com/latest?base=USD")
      .then(response => response.json())
      .then(data => {
        setRates(data.rates);
        setLoading(false);
      })
      .catch(err => {
        setError("Failed to fetch currency rates");
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading currency rates...</div>;
  if (error) return <div>{error}</div>;

  // Get a few major currencies
  const majorCurrencies = ["EUR", "GBP", "TRY", "JPY"];
  
  return (
    <div className="card" style={{ minWidth: '250px' }}>
      <h3 style={{ fontSize: '1.1em', marginBottom: '15px' }}>Currency Exchange Rates</h3>
      <div>
        {majorCurrencies.map(currency => (
          <div key={currency} style={{ display: "flex", justifyContent: "space-between", marginBottom: '8px', padding: '5px 0', borderBottom: '1px solid #eee' }}>
            <span><strong>{currency}:</strong></span>
            <span>{rates[currency] ? rates[currency].toFixed(4) : "N/A"}</span>
          </div>
        ))}
      </div>
      <small style={{ display: 'block', marginTop: '10px', color: '#666' }}>Base: USD | Updated: {new Date().toLocaleTimeString()}</small>
    </div>
  );
}