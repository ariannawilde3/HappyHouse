import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./PGCCreated.css";
import NavBar from './NavBar.jsx';
import { useLocation } from "react-router-dom";


export default function PGCCreated() {
    const [waitingtojoin] = useState(''); //dealing with gc lock
    const { state } = useLocation(); //allows pass of info
    const navigate = useNavigate(); // allows for nav
    const housename = state?.housename ?? "";
    const roommates = state?.roommates ?? 3; // sets 3 as default
    const inviteCode = state?.inviteCode ?? ""; // sets "" as default

   useEffect(() => {
    const load = async () => {
      const r = await fetch(`${API_URL}/gcc/by-code/${inviteCode}`, {
        headers: { Accept: "application/json" },
      });

      const gc = await r.json();
      setCurrentRoommates(gc.currentRoomieCount);
    };

    load(); // initial load
    const interval = setInterval(load, 2000); // poll every 2s
    return () => clearInterval(interval);
  }, [inviteCode]);

  // main method
  return (
    <div className="login-container">
      <div className="phone-frame">
        <div className="status-bar">
          <span>9:41</span>
          <span>📶 📡 🔋</span>
        </div>

        <div className="content-area">
          <div className="welcome-section">
          <p className="welcome-subtitle">Welcome to</p>
            <h1 className="welcome-title">{ housename }</h1>
          </div>

          <div className="form-card">
            <div className="divider">
              Your group chat has been successfully created. Below is your invite code, send it to your roommates and once all have joined, the group chat will open!
            </div>

                <div className = "form-card-mini"> 
                    <div className = "invitecode"> { inviteCode } </div>
                </div>

            <button className="btn btn-off">
            1/{ roommates } Roommates Joined
            </button>
          </div>
        </div>

        {/* Navigation Bar */}
        <NavBar tab="chat" />
      </div>
    </div>
  );
}
