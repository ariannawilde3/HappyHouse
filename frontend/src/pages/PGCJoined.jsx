import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./PGCJoined.css";
import house from "../assets/images/house.png";
import neighborhood from "../assets/images/neighborhood.png";
import settings from "../assets/images/settings.png";

const API_URL = "http://localhost:5000/api";


export default function PGCJoined() {
  //const [waitingtojoin] = useState('');
  const navigate = useNavigate();
  const location = useLocation();
  const { invitecode } = location.state ?? {};

  const [houseName, setHouseName] = useState("");
  const [expectedRoommates, setExpected] = useState(0);
  const [currentRoommates, setCurrent] = useState(0);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const run = async () => {
        if (invitecode) {
          const r = await fetch(`${API_URL}/gcc/by-code/${invitecode}`, { headers: { Accept: "application/json" }});
          const t = await r.text();
          const gc = JSON.parse(t);
          setHouseName(gc.houseName ?? "");
          setExpected(gc.expectedRoomieCount ?? 0);
          setCurrent(gc.currentRoomieCount ?? 0);
          return;
        }
    };
    run();
  }, [invitecode]);


 
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
            <p className="welcome-subtitle">Welcome to</p>
            <h1 className="welcome-title">{houseName}</h1>
          </div>

          <div className="form-card">
            <div className="divider">
              You have successfully joined your House. Below is your invite code, send it to your roommates and once all have joined, the group chat will open!
            </div>

                <div className = "form-card-mini"> 
                    <div className = "invitecode"> { invitecode } </div>
                </div>

            <button className="btn btn-off">
              {currentRoommates}/{expectedRoommates} Roommates Joined
            </button>
          </div>
        </div>

        {/* Navigation Bar */}
        <div className="forum-nav-bar">
          <button onClick={goToChat} className="nav-btn active-btn">
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