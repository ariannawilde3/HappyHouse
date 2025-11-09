import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './ProfilePage.css';
import { getCurrentUser } from "../api";
import house from '../assets/images/house.png';
import neighborhood from '../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

export default function ProfilePage() {
  const navigate = useNavigate();
  
  const [name, setName] = useState('Loading...');
  const [email, setEmail] = useState('Loading...');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Fetch user data when component mounts
    const fetchUserData = async () => {
      try {
        const user = await getCurrentUser();  // ← Now it's being used!
        setName(user.name);
        setEmail(user.email);
      } catch (error) {
        console.error('Error fetching user:', error);
        navigate('/');
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, [navigate]);

  const handleEditProfile = () => {
    navigate('/editProfile');
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  const goToChat = () => {
<<<<<<< Updated upstream
    navigate('/house');
=======
    navigate('/makeGC');
    console.log('house icon clicked');
>>>>>>> Stashed changes
  };

  const goToForum = () => {
    navigate('/neighborhood');
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="profile-outer-container">
      <div className="profile-phone-frame">
        <div className="profile-status-bar">
          <span>9:41</span>
          <span>📶 📡 🔋</span>
        </div>
        
        <div className="profile-content-area">
          <div className="profile-logo-container">
            <div className="profile-logo-circle">
              <span className="profile-logo-text">HH</span>
            </div>
          </div>

          <div className="profile-welcome-section">
            <p className="profile-welcome-subtitle">Your Profile</p>
            <h1 className="profile-welcome-title">HappyHouse</h1>
          </div>

          <div className="profile-info-card">
            <div className="profile-info-row">
              <p className="profile-info-label">Name</p>
              <p className="profile-info-value">{name}</p>
            </div>

            <div className="profile-info-row">
              <p className="profile-info-label">Email</p>
              <p className="profile-info-value">{email}</p>
            </div>

            <button onClick={handleEditProfile} className="profile-btn profile-btn-primary">
              Edit Profile
            </button>

            <button onClick={handleLogout} className="profile-btn profile-btn-secondary">
              Logout
            </button>
          </div>

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