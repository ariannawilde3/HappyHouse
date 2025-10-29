import React from 'react';
import { useNavigate } from 'react-router-dom';
import './GuestSettingsPage.css';
import house from '../assets/images/house.png';
import neighborhood from '../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

export default function GuestSettingsPage() {
  const navigate = useNavigate();

  const goToChat = () => {
    navigate('/house');
    console.log('House icon clicked');
  };

  const goToForum = () => {
    navigate('/neighborhood');
    console.log('Forum icon clicked');
  };

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
        <div className="guest-settings-nav-bar">
          <button onClick={goToChat} className="nav-btn inactive-btn">
            <img src={house} alt="House Chat" style={{ width: '50px', height: '50px'}}/>
          </button>
        
          <button onClick={goToForum} className="nav-btn inactive-btn">
            <img src={neighborhood} alt="Forum" style={{ width: '115px', height: '50px' }}/>
          </button>
        
          <button className="nav-btn active-btn">
            <img src={settings} alt="Settings" style={{ width: '50px', height: '50px' }}/>
          </button>
        </div>
      </div>
    </div>
  );
}