import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./PGCMake.css";
import NavBar from './NavBar.jsx';



const API_URL = "http://localhost:5000/api";

export default function PGCMake() {
  const navigate = useNavigate();

  const [invitecode, setInviteCode] = useState();
      
  const checkInviteCode = async (code) => {
    const res = await fetch(`${API_URL}/gcc/exists/${Number(code)}`);
    const exists = await res.json(); // backend returns boolean
    return exists;
  };

const handleInviteJoin = async (e) => {
  e.preventDefault();
  const code = Number(invitecode);

  const isValid = await checkInviteCode(code);
  if (!isValid) {
    alert("Wrong Invite Code. Try again!");
    return;
  }

  const token = localStorage.getItem("token");
  const res = await fetch(`${API_URL}/gcc/join/${code}`, {
    method: "POST",
    headers: { Authorization: `Bearer ${token}` },
  });

  const gc = await res.json(); 

  // 🟢 Now you can safely check unlocked
  if (gc.unlocked) {
    navigate("/house"); // last person joined → open house chat
  } else {
    navigate("/gcJoinedWaiting", { state: { invitecode: code } });
  }

  //console.log("Invite Entered and Valid:", invitecode);
};

        const handleCreateGC = () => {
          navigate('/gcSettings');
          console.log('CreateGC Clicked');
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
                  <div className="divider">Looks like you aren't part of a house yet. Let's fix that!</div>
                  <button onClick={handleCreateGC} className="btn btn-primary">
                    Create a House
                  </button>

                  <div className="divider">or</div>
                  <div className="divider">Join a House</div>
                  <div className="input-group">
                    <input
                      type="text"
                      value={invitecode}
                      onChange={(e) => setInviteCode(e.target.value)}
                      placeholder="Enter your code"
                      className="input-field"
                    />
                  </div>
                  
                  <button onClick={handleInviteJoin} className="btn btn-primary">
                    Join
                  </button>
      
      
                  
                </div>
              </div>
              {/* Navigation Bar */}
              <NavBar tab="chat" />
            </div>
          </div>
        );
      }