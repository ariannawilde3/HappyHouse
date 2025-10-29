import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./PGCSettings.css";
import house from "../assets/images/house.png";
import neighborhood from "../assets/images/neighborhood.png";
import settings from "../assets/images/settings.png";

export default function PGCSettings() {
  const [housename, setHouseName] = useState("");

  const handleHouseName = (value) => {
    setHouseName(value); }


  const goToForum = () => {
    navigate('/neighborhood');
    console.log('forum clicked');
  };

  const goToProfile = () => {
    navigate('/profile');
    console.log('settings clicked');
  };

  const [svalue, setValue] = useState(1);


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
              Choose your House's settings, then we'll do the rest!
            </div>

            {/* Allow Anonymous Chats Checkbox */}
          <div className="checkbox-container">
            <div className="checkbox-wrapper">
              <input
                type="checkbox"
                id="anonymous"
              />
            </div>
            <label htmlFor="anonymous" className="checkbox-label">
              <div className="checkbox-label-title">Allow Anonymous Chats</div>
            </label>
          </div>

          {/* Regenerate Usernames Checkbox */}
          <div className="checkbox-container">
            <div className="checkbox-wrapper">
              <input
                type="checkbox"
                id="regenerate"
                
              />
            </div>
            <label htmlFor="regenerate" className="checkbox-label">
              <div className="checkbox-label-title">Regenerate Usernames</div>
            </label>
          </div>

          {/*roommate selection slider*/}
          <div className="slider-container">
          <label htmlFor="roommates" className="divider">
            How many roommates? (Including you): {svalue}
          </label>
                <input
                  id="roommates"
                  type="range"
                  min="1"
                  max="10"
                  className="slider"
                  svalue={svalue}
                  onChange={(e) => setValue(e.target.value)}
                />
            </div>


          {/*house name form*/}
            <div className="divider">Choose a House Name</div>
            <div className="input-group">
              <input
                type="text"
                value={housename}
                onChange={(e) => handleHouseName(e.target.value)}
                placeholder="Enter your group chat name"
                className="input-field"
              />
            </div>


          {/*create button */}
            <button className="btn btn-primary">
              Create
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