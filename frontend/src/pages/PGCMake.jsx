import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./PGCMake.css";
import house from '../assets/images/house.png';
import neighborhood from'../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

export default function PGCMake() {
  const navigate = useNavigate();

        const [invitecode, setInviteCode] = useState('');
      
        const handleInviteJoin = () => {
          console.log('Invite Entered', { email, password });
        };
      
        const handleCreateGC = () => {
          console.log('CreateGC Clicked');
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
                  <div className="divider">Looks like you aren't part of a house yet. Let's fix that!</div>
                  <button onClick={handleCreateGC} className="btn btn-primary">
                    Create a House
                  </button>

                  <div className="divider">or</div>
                  <div className="divider">Join a House</div>
                  <div className="input-group">
                    <input
                      type="invitecode"
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
              <div className="forum-nav-bar">
                    <button  className="nav-btn active-btn">
                        <img src={house} desc="House Chat" style={{ width: '50px', height: '50px'}}/>
                    </button>

                    <button onClick={goToForum} className="nav-btn inactive-btn">
                        <img src={neighborhood} desc="Forum" style={{ width: '115px', height: '50px' }}/>
                    </button>

                    <button conClick={goToProfile} lassName="nav-btn inactive-btn">
                        <img src={settings} desc="Settings" style={{ width: '50px', height: '50px' }}/>
                    </button>

                </div>
            </div>
          </div>
        );
      }