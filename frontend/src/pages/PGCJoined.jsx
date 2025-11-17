import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./PGCJoined.css";
import NavBar from './NavBar.jsx';


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
        <NavBar tab="chat" />
      </div>
    </div>
  );
}