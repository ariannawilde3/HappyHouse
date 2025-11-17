import React from 'react';
import { useNavigate } from 'react-router-dom';
import './GuestSettingsPage.css';
import NavBar from './NavBar.jsx';

export default function GuestSettingsPage() {
  const navigate = useNavigate();

  const handleProceedToLogin = () => {
    navigate('/');
    console.log('Proceed to Login clicked');
  };

  return (
    <div className="guest-settings-outer-container">
      <div className="guest-settings-phone-frame">
        {/* Status bar */}
        <div className="guest-settings-status-bar">
          <span>9:41</span>
          <span>📶 📡 🔋</span>
        </div>

        {/* Content */}
        <div className="guest-settings-content-area">
          {/* Header */}
          <div className="guest-settings-header">
            <p className="guest-settings-your-text">Your</p>
            <h1 className="guest-settings-app-title">HappyHouse</h1>
          </div>

          {/* Guest Message Card */}
          <div className="guest-settings-guest-card">
            <h2 className="guest-settings-greeting">Hi, Guest!</h2>
            <p className="guest-settings-message">
              Sign in or Register to gain access to all HappyHouse has to offer.
            </p>
            <button onClick={handleProceedToLogin} className="guest-settings-btn guest-settings-btn-primary">
              Proceed to Login
            </button>
          </div>
        </div>

        {/* Navigation Bar */}
        <NavBar tab="settings" />
      </div>
    </div>
  );
}