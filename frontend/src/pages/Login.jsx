import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSignIn = () => {
    navigate('/neighborhood');
    console.log('Sign in clicked', { email, password });
  };

  const handleGoogleSignIn = () => {

    console.log('Google sign in clicked');
  };

  const handleGuestSignIn = () => {
    navigate('/neighborhood');
    console.log('Guest sign in clicked');
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
            <p className="welcome-subtitle">Welcome To</p>
            <h1 className="welcome-title">HappyHouse</h1>
          </div>

          <div className="form-card">
            <div className="input-group">
              <label className="input-label">Email</label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Enter your email"
                className="input-field"
              />
            </div>

            <div className="input-group-password">
              <label className="input-label">Password</label>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Enter your password"
                className="input-field"
              />
            </div>

            <button onClick={handleSignIn} className="btn btn-primary">
              Sign In
            </button>

            <div className="divider">or</div>

            <button onClick={handleGoogleSignIn} className="btn btn-primary">
              Continue with Google
            </button>

            <button onClick={handleGuestSignIn} className="btn btn-primary">
              Register as Guest
            </button>

            <div className="forgot-password-container">
              <a href="#" className="forgot-password-link">
                Forgot password?
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}