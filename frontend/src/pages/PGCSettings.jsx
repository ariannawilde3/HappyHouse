import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./PGCSettings.css";
import house from "../assets/images/house.png";
import neighborhood from "../assets/images/neighborhood.png";
import settings from "../assets/images/settings.png";

const API_URL = "http://localhost:5000/api";

export default function PGCSettings() {
  const navigate = useNavigate();

  // state
  const [housename, setHouseName] = useState("");
  const [svalue, setValue] = useState(7); // slider starts at 7

  // handlers
  const handleHouseName = (value) => setHouseName(value);

  const getSett = async () => {
      const token = localStorage.getItem("token");
      const res = await fetch(`${API_URL}/gcc/`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          houseName: housename,
          expectedRoomieCount: Number(svalue),
        }),
      });
      const n = await res.json();
      return n;
  };

  const handleCreateGCWait = async () => {
    if (!housename.trim()) {
      alert("Please enter a house name.");
      return;
    }
    if (housename.length > 15) {
      alert("House name too long. Please shorten.");
      return;
    }

    const data = await getSett();
    if (!data) return;

    navigate("/createdWaitingRoom", {
      state: {
        housename,
        roommates: Number(svalue),
        inviteCode: data,
      },
    });
  };

  const goToForum = () => {
    navigate("/neighborhood");
    console.log("forum clicked");
  };

  const goToProfile = () => {
    navigate("/profile");
    console.log("settings clicked");
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
              How many roommates? (Including you): {svalue}
            </div>

            {/* roommate selection slider */}
            <div className="slider-container">
              <input
                id="roommates"
                type="range"
                min={3}
                max={10}
                className="slider"
                value={svalue}
                onChange={(e) => setValue(Number(e.target.value))}
              />
            </div>

            {/* house name form */}
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

            {/* create button */}
            <button onClick={handleCreateGCWait} className="btn btn-primary">
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
