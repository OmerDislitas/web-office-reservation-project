import { useEffect, useState } from "react";
import api from "../services/api";

export default function OfficeList() {
  const [offices, setOffices] = useState([]);
  const [error, setError] = useState("");

  // filtreler (öncekinden kaldıysa dert değil)
  const [city, setCity] = useState("");
  const [minPrice, setMinPrice] = useState("");
  const [maxPrice, setMaxPrice] = useState("");
  const [minSize, setMinSize] = useState("");
  const [maxSize, setMaxSize] = useState("");

  // rezervasyon için
  const [selectedOfficeId, setSelectedOfficeId] = useState(null);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [msg, setMsg] = useState("");
  
  // yorumlar için
  const [reviews, setReviews] = useState({});
  const [showReviews, setShowReviews] = useState({});
  const [newReview, setNewReview] = useState({ rating: 5, comment: "" });

  const load = () => {
    const params = {};
    if (city.trim()) params.city = city.trim();
    if (minPrice) params.minPrice = minPrice;
    if (maxPrice) params.maxPrice = maxPrice;
    if (minSize) params.minSize = minSize;
    if (maxSize) params.maxSize = maxSize;

    api.get("/api/offices", { params })
      .then((res) => {
        setOffices(res.data);
        setError("");
      })
      .catch(() => setError("Backend'e bağlanamadı."));
  };

  useEffect(() => {
    load();
  }, []);

  const reserve = () => {
    setMsg("");
    if (!selectedOfficeId || !startDate || !endDate) {
      setMsg("Office + startDate + endDate zorunlu.");
      return;
    }
    
    // Show confirmation dialog
    if (!window.confirm("Are you sure you want to reserve this office?")) {
      return;
    }

    api.post("/api/reservations", {
      officeId: selectedOfficeId,
      startDate,
      endDate,
    })
      .then(() => {
        setMsg("Rezervasyon oluşturuldu (PENDING).");
        setSelectedOfficeId(null);
        setStartDate("");
        setEndDate("");
        // Also hide reviews when reservation is made
        setShowReviews(prev => ({ ...prev, [selectedOfficeId]: false }));
      })
      .catch((e) => {
        // 401 gelirse login olman gerekir
        if (e?.response?.status === 401 || e?.response?.status === 302) {
          setMsg("Login olman gerekiyor (backend /login).");
        } else {
          setMsg("Hata: rezervasyon oluşturulamadı.");
        }
      });
  };

  const toggleReviews = (officeId) => {
    // Set the selected office ID to show reviews for that office
    setSelectedOfficeId(officeId);
    
    setShowReviews(prev => ({ ...prev, [officeId]: !prev[officeId] }));
    
    // Load reviews if not already loaded
    if (!reviews[officeId]) {
      api.get(`/api/reviews/office/${officeId}`)
        .then(res => {
          setReviews(prev => ({ ...prev, [officeId]: res.data }));
        })
        .catch(() => {
          setMsg("Failed to load reviews");
        });
    }
  };

  const submitReview = (officeId) => {
    api.post("/api/reviews", {
      officeId,
      rating: parseInt(newReview.rating),
      comment: newReview.comment
    })
      .then(() => {
        setMsg("Review submitted successfully");
        // Refresh reviews
        api.get(`/api/reviews/office/${officeId}`)
          .then(res => {
            setReviews(prev => ({ ...prev, [officeId]: res.data }));
          });
        setNewReview({ rating: 5, comment: "" });
        // Clear selection after submitting review
        setSelectedOfficeId(null);
      })
      .catch(() => {
        setMsg("Failed to submit review");
      });
  };

  return (
    <div className="container">
      <h1 className="page-title">Available Offices</h1>
      
      <div className="card" style={{ marginBottom: '30px' }}>
        <div className="grid grid-cols-2" style={{ gap: '15px' }}>
          <div className="form-group">
            <input 
              placeholder="City" 
              className="form-control" 
              value={city} 
              onChange={(e) => setCity(e.target.value)} 
            />
          </div>
          <div className="form-group">
            <input 
              type="number" 
              placeholder="Min Price" 
              className="form-control" 
              value={minPrice} 
              onChange={(e) => setMinPrice(e.target.value)} 
            />
          </div>
          <div className="form-group">
            <input 
              type="number" 
              placeholder="Max Price" 
              className="form-control" 
              value={maxPrice} 
              onChange={(e) => setMaxPrice(e.target.value)} 
            />
          </div>
          <div className="form-group">
            <input 
              type="number" 
              placeholder="Min Size (sqm)" 
              className="form-control" 
              value={minSize} 
              onChange={(e) => setMinSize(e.target.value)} 
            />
          </div>
          <div className="form-group">
            <input 
              type="number" 
              placeholder="Max Size (sqm)" 
              className="form-control" 
              value={maxSize} 
              onChange={(e) => setMaxSize(e.target.value)} 
            />
          </div>
        </div>
        
        <div style={{ display: 'flex', gap: '10px', marginTop: '15px' }}>
          <button className="btn" onClick={load}>Search</button>
          <button className="btn btn-outline" onClick={() => { setCity(""); setMinPrice(""); setMaxPrice(""); setMinSize(""); setMaxSize(""); setTimeout(load, 0); }}>
            Clear Filters
          </button>
        </div>
      </div>

      {msg && <div className="alert alert-success">{msg}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <div className="grid" style={{ gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '20px' }}>
        {offices.map((o) => (
          <div key={o.id} className="card">
            <h3 style={{ marginBottom: '10px', color: '#333' }}>{o.title}</h3>
            <p style={{ marginBottom: '5px' }}><strong>City:</strong> {o.city}</p>
            <p style={{ marginBottom: '5px' }}><strong>Price:</strong> ₺{o.pricePerMonth}/month</p>
            <p style={{ marginBottom: '15px' }}><strong>Size:</strong> {o.sizeInSqm} m²</p>

            <div style={{ display: 'flex', gap: '10px' }}>
              <button className="btn btn-outline" onClick={() => { setSelectedOfficeId(o.id); setMsg(""); }}>
                Reserve
              </button>
              <button className="btn" onClick={() => toggleReviews(o.id)}>
                {showReviews[o.id] ? "Hide Reviews" : "Show Reviews"} ({reviews[o.id]?.length || 0})
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* Rezervasyon Formu */}
      {selectedOfficeId && (
        <div className="card" style={{ marginTop: '20px', borderLeft: '4px solid #4361ee' }}>
          <h4 style={{ marginBottom: '15px' }}>Reserve This Office</h4>
          <div className="grid grid-cols-2" style={{ gap: '15px' }}>
            <div className="form-group">
              <label>Start Date</label>
              <input 
                type="date" 
                className="form-control" 
                value={startDate} 
                onChange={(e) => setStartDate(e.target.value)} 
              />
            </div>
            <div className="form-group">
              <label>End Date</label>
              <input 
                type="date" 
                className="form-control" 
                value={endDate} 
                onChange={(e) => setEndDate(e.target.value)} 
              />
            </div>
          </div>
          <div style={{ display: 'flex', gap: '10px', marginTop: '15px' }}>
            <button className="btn btn-success" onClick={reserve}>Confirm Reservation</button>
            <button className="btn btn-outline" onClick={() => { setSelectedOfficeId(null); setStartDate(""); setEndDate(""); setShowReviews(prev => ({ ...prev, [selectedOfficeId]: false })); }}>
              Cancel
            </button>
          </div>
        </div>
      )}

      {/* Yorumlar Bölümü */}
      {selectedOfficeId && showReviews[selectedOfficeId] && (
        <div className="card" style={{ marginTop: '20px' }}>
          <h3>Reviews</h3>
          
          {/* Yeni Yorum Ekleme */}
          <div className="card" style={{ marginBottom: '20px', backgroundColor: '#f8f9fa' }}>
            <h4>Add Review</h4>
            <div className="form-group">
              <label>Rating</label>
              <select 
                className="form-control" 
                value={newReview.rating} 
                onChange={(e) => setNewReview({...newReview, rating: e.target.value})}
              >
                {[1,2,3,4,5].map(num => (
                  <option key={num} value={num}>{num} Star{num > 1 ? 's' : ''}</option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <label>Comment</label>
              <textarea 
                placeholder="Write your review..." 
                className="form-control" 
                value={newReview.comment}
                onChange={(e) => setNewReview({...newReview, comment: e.target.value})}
                style={{ minHeight: 100 }}
              />
            </div>
            <button className="btn" onClick={() => submitReview(selectedOfficeId)}>
              Submit Review
            </button>
            <button className="btn btn-outline" style={{ marginLeft: '10px' }} onClick={() => { setSelectedOfficeId(null); setShowReviews(prev => ({ ...prev, [selectedOfficeId]: false })); }}>
              Cancel
            </button>
          </div>
          
          {/* Mevcut Yorumlar */}
          <div>
            <h4>Existing Reviews</h4>
            {reviews[selectedOfficeId] && reviews[selectedOfficeId].length > 0 ? (
              reviews[selectedOfficeId].map(review => (
                <div key={review.id} className="card" style={{ marginBottom: '15px' }}>
                  <div><strong>Rating:</strong> {"★".repeat(review.rating)} {review.rating}/5</div>
                  <div><strong>Comment:</strong> {review.comment || "No comment"}</div>
                  <div style={{ fontSize: "0.8em", color: "#666", marginTop: '10px' }}>
                    By {review.user?.fullName || "Anonymous"} on {new Date(review.createdAt).toLocaleDateString()}
                  </div>
                </div>
              ))
            ) : (
              <p>No reviews yet.</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
