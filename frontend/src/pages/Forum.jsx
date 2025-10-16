import React, { useState } from 'react';
import './Forum.css';
import house from '../assets/images/house.png';
import neighborhood from'../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

export default function ForumPage() {

    const addPost = () => {
        console.log('Post added to forum');
    };

    const goToChat = () => {
        console.log('House icon clicked');
    };

    const goToProfile = () => {
        console.log('Settings icon clicked');
    };

    return (
        <div className="forum-outer-container">
            <div className="forum-phone-frame">
                {/* Status bar */}
                <div className="forum-status-bar">
                    <span>9:54</span>
                    <span>📶 📡 🔋</span>
                </div>

                {/* Content */}
                <div className="forum-content-area">

                    {/* Welcome text */}
                    <div className="forum-welcome-section">
                        <p className="forum-welcome-subtitle">Your</p>
                        <h1 className="forum-welcome-title">Neighborhood</h1>
                    </div>

                    
                </div>

                {/* Post Button */}
                <button onClick={addPost} className="post-btn">
                    +
                </button>
        
                {/* Navigation Bar */}
                <div className="forum-nav-bar">
                    <button onClick={goToChat} className="chat-btn">
                        <img src={house} desc="House Chat" style={{ width: '50px', height: '50px' }}/>
                    </button>

                    <button className="forum-btn">
                        <img src={neighborhood} desc="Forum" style={{ width: '115px', height: '50px' }}/>
                    </button>

                    <button onClick={goToProfile} className="profile-btn">
                        <img src={settings} desc="Settings" style={{ width: '50px', height: '50px' }}/>
                    </button>

                </div>
            </div>
        </div>
    );
};