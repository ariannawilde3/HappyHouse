import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./PGCSettings.css";
import house from "../assets/images/house.png";
import neighborhood from "../assets/images/neighborhood.png";
import settings from "../assets/images/settings.png";
import { getCurrentUser } from "../api";

const API_URL = "http://localhost:5000/api";

export default function PGCSettings() {
  // allows navigation
  const navigate = useNavigate();

  /*sets house name from what user inputs */
  const handleHouseName = (value) => {
    setHouseName(value);
  };

  const [housename, setHouseName] = useState("");

  //defining getSett which allows for post from controller
  const getSett = async () => {
      const res = await fetch(`${API_URL}/gcc/`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ // get housename and roomie num from user input
          houseName: housename,
          roomieCount: Number(svalue),
        }),
      })

      const data = await res.json(); //putting data into json
      //console.log("Server response:", data);
      return data;
  };


  /*passes name and number of roommates just for ui, then navigates */
  const handleCreateGCWait = async () => {
    /*if no house name alert */
    if (!housename.trim()) {
      alert("Please enter a house name.");
      return;
    }

    if (housename.length > 15) {
      alert("House name too long. Please shorten.");
      return;
    }

    /* calling to get user input*/
    const data = await getSett();
    /*giving info to other page*/
    navigate("/createdWaitingRoom", {
      state: {
        housename,
        roommates: Number(svalue),
        inviteCode: data.inviteCode,    
      },
    }); 
  };

  // navigation
  const goToForum = () => {
    navigate("/neighborhood");
    console.log("forum clicked");
  };

  const goToProfile = () => {
    navigate("/profile");
    console.log("settings clicked");
  };

  //sets the slider to 7 immediately
  const [svalue, setValue] = useState(7);

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

            {/*roommate selection slider*/}
            <div className="slider-container">
              <input
                id="roommates"
                type="range"
                min="3"
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
