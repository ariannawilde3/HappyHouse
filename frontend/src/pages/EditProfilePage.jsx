import { useNavigate } from 'react-router-dom';
import './EditProfilePage.css';
import house from '../assets/images/house.png';
import neighborhood from '../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

export default function EditProfilePage() {
  const navigate = useNavigate();

  const goToChat = () => {
    navigate('/house');
    console.log('House icon clicked');
  };

  const goToForum = () => {
    navigate('/neighborhood');
    console.log('Forum icon clicked');
  };

  const handleSaveProfile = () => {
    navigate('/profile');
    console.log('Save Profile clicked', { name, username, email });
  };

  const handleCancel = () => {
    navigate('/profile');
    console.log('Cancel clicked');
  };

  return (
    <div className="edit-outer-container">
      <div className="edit-phone-frame">
        {/* Status bar */}
        <div className="edit-status-bar">
          <span>9:41</span>
          <span>📶 📡 🔋</span>
        </div>

        {/* Content */}
        <div className="edit-content-area">
          {/* Header */}
          <div className="edit-header">
            <p className="edit-your-text">Your</p>
            <h1 className="edit-app-title">HappyHouse</h1>
          </div>

          {/* Edit Profile Card */}
          <div className="edit-profile-card">
            <div className="edit-input-group">
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

            <button onClick={handleSaveProfile} className="edit-btn edit-btn-primary">
              Save Profile
            </button>

            <button onClick={handleCancel} className="edit-btn edit-btn-secondary">
              Cancel
            </button>
          </div>
        </div>

        {/* Navigation Bar */}
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