import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css'; // Reusing Login.css for consistent styling

const API_URL = 'http://localhost:5000/api';

export default function SignupPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSignup = async () => {
    // Clear any previous errors
    setError('');
    
    // Validate inputs
    if (!email || !password || !confirmPassword) {
      setError('Please fill in all fields');
      return;
    }

    if (password !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    if (password.length < 6) {
      setError('Password must be at least 6 characters');
      return;
    }

    // Validate email format
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError('Please enter a valid email address');
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(`${API_URL}/auth/signup`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json();

      if (response.ok) {
        // Store authentication data in localStorage
        localStorage.setItem('token', data.token);
        localStorage.setItem('refreshToken', data.refreshToken);
        localStorage.setItem('userId', data.userId);
        localStorage.setItem('anonymousUsername', data.anonymousUsername);
        localStorage.setItem('userType', data.userType);
        
        console.log('✅ Signup successful!', data);
        console.log('Anonymous Username:', data.anonymousUsername);
        
        // Navigate to forum
        navigate('/neighborhood');
      } else {
        // Handle error response
        setError(data.message || 'Signup failed. Please try again.');
        console.error('❌ Signup failed:', data);
      }
    } catch (error) {
      console.error('❌ Signup error:', error);
      setError('Unable to connect to server. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  // Allow Enter key to submit
  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSignup();
    }
  };

  const goToLogin = () => {
    navigate('/');
  };

  return (
    <div className="login-container">
      <div className="phone-frame">
        <div className="status-bar">
          <span>9:41</span>
          <span>📶 📡 🔋</span>
        </div>
        
        <div className="content-area">
          <div className="logo-container">
            <div className="logo-circle">
              <span className="logo-text">HH</span>
            </div>
          </div>

          <div className="welcome-section">
            <p className="welcome-subtitle">Join</p>
            <h1 className="welcome-title">HappyHouse</h1>
          </div>

          <div className="form-card">
            {/* Error Message */}
            {error && (
              <div style={{
                backgroundColor: '#fee2e2',
                border: '1px solid #ef4444',
                color: '#991b1b',
                padding: '0.75rem',
                borderRadius: '6px',
                marginBottom: '1rem',
                fontSize: '0.875rem'
              }}>
                {error}
              </div>
            )}

            <div className="input-group">
              <label className="input-label">Email</label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                onKeyPress={handleKeyPress}
                placeholder="Enter your email"
                className="input-field"
                disabled={loading}
              />
            </div>

            <div className="input-group">
              <label className="input-label">Password</label>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                onKeyPress={handleKeyPress}
                placeholder="At least 6 characters"
                className="input-field"
                disabled={loading}
              />
            </div>

            <div className="input-group-password">
              <label className="input-label">Confirm Password</label>
              <input
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                onKeyPress={handleKeyPress}
                placeholder="Re-enter your password"
                className="input-field"
                disabled={loading}
              />
            </div>

            <button 
              onClick={handleSignup} 
              className="btn btn-primary"
              disabled={loading}
            >
              {loading ? 'Creating Account...' : 'Create Account'}
            </button>

            <div className="forgot-password-container">
              <span style={{ color: '#6b7280', fontSize: '0.875rem' }}>
                Already have an account?{' '}
              </span>
              <a 
                onClick={goToLogin} 
                className="forgot-password-link"
                style={{ cursor: 'pointer' }}
              >
                Sign In
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
