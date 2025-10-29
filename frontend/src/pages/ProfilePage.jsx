import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ProfilePage.css';
import house from '../assets/images/house.png';
import neighborhood from '../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

export default function ProfilePage() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const navigate = useNavigate();

  const handleEditProfile = () => {
    navigate('/editProfile');
    console.log('Edit Profile clicked');
  };

  const handleLogout = () => {
    navigate('/guestProfile');
    console.log('Logout clicked');
  };

  const handleSave = () => {
    console.log('Save clicked');
  };

  const goToChat = () => {
    navigate('/house');
    console.log('house icon clicked');
  };

  const goToForum = () => {
    navigate('/neighborhood');
    console.log('forum icon clicked');
  };

  return (
    <div className="profile-outer-container">
      <div className="profile-phone-frame">
        {/* Status bar */}
        <div className="profile-status-bar">
          <span>9:41</span>
          <span>📶 📡 🔋</span>
        </div>
        
        {/* Content */}
        <div className="profile-content-area">
          {/* Logo */}
          <div className="profile-logo-container">
            <div className="profile-logo-circle">
              <span className="profile-logo-text">HH</span>
            </div>
          </div>

          {/* Welcome text */}
          <div className="profile-welcome-section">
            <p className="profile-welcome-subtitle">Your Profile</p>
            <h1 className="profile-welcome-title">HappyHouse</h1>
          </div>

          {/* Profile info card */}
          <div className="profile-info-card">
            <div className="profile-info-row">
              <label className="profile-info-label">Name</label>
              <p className="profile-info-value">{name}</p>
            </div>

            <div className="profile-info-row">
              <label className="profile-info-label">Email</label>
              <p className="profile-info-value">{email}</p>
            </div>

            <button onClick={handleEditProfile} className="profile-btn profile-btn-primary">
              Edit Profile
            </button>

            <button onClick={handleLogout} className="profile-btn profile-btn-secondary">
              Logout
            </button>
          </div>

          {/* Navigation Bar */}
          <div className="profile-nav-bar">
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
    </div>
  );
}