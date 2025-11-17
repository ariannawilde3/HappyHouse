import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./PGCCreated.css";
import house from "../assets/images/house.png";
import neighborhood from "../assets/images/neighborhood.png";
import settings from "../assets/images/settings.png";
import { useLocation } from "react-router-dom";


export default function PGCCreated() {
    const [waitingtojoin] = useState(''); //dealing with gc lock
    const { state } = useLocation(); //allows pass of info
    const navigate = useNavigate(); // allows for nav
    const housename = state?.housename ?? "";
    const roommates = state?.roommates ?? 3; // sets 3 as default
    const inviteCode = state?.inviteCode ?? ""; // sets "" as default

   useEffect(() => {
    if (!inviteCode) return;

    const load = async () => {
      const r = await fetch(`${API_URL}/gcc/by-code/${inviteCode}`, {
        headers: { Accept: "application/json" },
      });
      if (!r.ok) return;
      const gc = await r.json();
      setCurrentRoommates(gc.currentRoomieCount ?? 1);
    };

    load(); // initial load
    const interval = setInterval(load, 2000); // poll every 2s
    return () => clearInterval(interval);
  }, [inviteCode]);


  //navigation
  const goToForum = () => {
    navigate('/neighborhood');
    console.log('forum clicked');
  };

  const goToProfile = () => {
    navigate('/profile');
    console.log('profile clicked');
  };

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
