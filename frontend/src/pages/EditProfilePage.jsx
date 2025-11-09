import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './EditProfilePage.css';
import { getCurrentUser, updateProfile } from "../api";
import house from '../assets/images/house.png';
import neighborhood from '../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

export default function EditProfilePage() {
  const navigate = useNavigate();
  
  const [name, setName] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    // Fetch current user data
    const fetchUserData = async () => {
      try {
        const user = await getCurrentUser();  // ← Now it's being used!
        setName(user.name);
        setEmail(user.email);
        setUsername(user.username || 'HappyUser123');
      } catch (error) {
        console.error('Error fetching user:', error);
        navigate('/');
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, [navigate]);

  const goToChat = () => {
    navigate('/house');
  };

  const goToForum = () => {
    navigate('/neighborhood');
  };

  const handleSaveProfile = async () => {
    if (newPassword || confirmPassword || currentPassword) {
      if (newPassword !== confirmPassword) {
        setPasswordError('New passwords do not match!');
        return;
      }
      if (newPassword.length < 6) {
        setPasswordError('Password must be at least 6 characters!');
        return;
      }
      if (!currentPassword) {
        setPasswordError('Current password is required to change password!');
        return;
      }
    }

    setPasswordError('');
    setSaving(true);

    try {
      const profileData = {
        name,
        email,
        currentPassword: currentPassword || null,
        newPassword: newPassword || null,
      };

      await updateProfile(profileData);  // ← Now it's being used!
      alert('Profile updated successfully!');
      navigate('/profile');
    } catch (error) {
      setPasswordError(error.message || 'Failed to update profile');
    } finally {
      setSaving(false);
    }
  };

  const handleCancel = () => {
    navigate('/profile');
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="edit-outer-container">
      <div className="edit-phone-frame">
        <div className="edit-status-bar">
          <span>9:41</span>
          <span>📶 📡 🔋</span>
        </div>

        <div className="edit-content-area">
          <div className="edit-header">
            <p className="edit-your-text">Your</p>
            <h1 className="edit-app-title">HappyHouse</h1>
          </div>

          <div className="edit-profile-card">
            <div className="edit-input-group">
              <label className="edit-label">Name:</label>
              <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="Name"
                className="edit-input-field"
              />
            </div>

            <div className="edit-username-section">
              <p className="edit-username-label">Today your username is:</p>
              <p className="edit-username-value">{username}</p>
            </div>

            <div className="edit-input-group">
              <label className="edit-label">Email:</label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Email"
                className="edit-input-field"
              />
            </div>

            <div className="edit-input-group">
              <label className="edit-label">Current Password:</label>
              <input
                type="password"
                value={currentPassword}
                onChange={(e) => setCurrentPassword(e.target.value)}
                placeholder="Enter current password"
                className="edit-input-field"
              />
            </div>

            <div className="edit-input-group">
              <label className="edit-label">New Password:</label>
              <input
                type="password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                placeholder="Enter new password"
                className="edit-input-field"
              />
            </div>

            <div className="edit-input-group">
              <label className="edit-label">Confirm New Password:</label>
              <input
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="Confirm new password"
                className="edit-input-field"
              />
            </div>

            {passwordError && (
              <p style={{ color: 'red', fontSize: '0.875rem', marginBottom: '1rem' }}>
                {passwordError}
              </p>
            )}

            <button 
              onClick={handleSaveProfile} 
              className="edit-btn edit-btn-primary"
              disabled={saving}
            >
              {saving ? 'Saving...' : 'Save Profile'}
            </button>

            <button onClick={handleCancel} className="edit-btn edit-btn-secondary">
              Cancel
            </button>
          </div>
        </div>

        <div className="edit-profile-nav-bar">
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