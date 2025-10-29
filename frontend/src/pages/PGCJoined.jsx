import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./PGCJoined.css";
import house from "../assets/images/house.png";
import neighborhood from "../assets/images/neighborhood.png";
import settings from "../assets/images/settings.png";

export default function PGCJoined() {
    const [waitingtojoin, setoff] = useState('');
    const navigate = useNavigate();

  const handleOff = () => {
    console.log("button pressed but off", { waitingtojoin });
  };

  const goToForum = () => {
    navigate('/neighborhood');
    console.log('forum clicked');
  };

  const goToProfile = () => {
    navigate('/profile');
    console.log('settings clicked');
  };

  return (
    <div className="login-container">
      <div className="phone-frame">
        <div className="status-bar">
          <span>9:41</span>
          <span>📶 📡 🔋</span>
        </div>

        <div className="content-area">
          <div className="welcome-section">
            <p className="welcome-subtitle">Your</p>
            <h1 className="welcome-title">House</h1>
          </div>

          <div className="form-card">
            <div className="divider">
              You have successfully joined your House. Below is your invite code, send it to your roommates and once all have joined, the group chat will open!
            </div>

                <div className = "form-card-mini"> 
                    <div className = "invitecode"> 128934 </div>
                </div>

            <button onClick={handleOff} className="btn btn-off">
              1/4 Roommates Joined
            </button>
          </div>
        </div>

        {/* Navigation Bar */}
        <div className="forum-nav-bar">
          <button className="nav-btn active-btn">
            <img
              src={house}
              alt="House Chat"
              style={{ width: "50px", height: "50px" }}
            />
          </button>
          <button onClick={goToForum} className="nav-btn inactive-btn">
            <img
              src={neighborhood}
              alt="Forum"
              style={{ width: "115px", height: "50px" }}
            />
          </button>
          <button onClick={goToProfile} className="nav-btn inactive-btn">
            <img
              src={settings}
              alt="Settings"
              style={{ width: "50px", height: "50px" }}
            />
          </button>
                    
        </div>
      </div>
    </div>
  );
}