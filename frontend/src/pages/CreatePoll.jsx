import React, { useState } from 'react';
import './CreatePoll.css';
import house from '../assets/images/house.png';
import neighborhood from'../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

export default function CreatePoll() {

    const goToChat = () => {
        console.log('House icon clicked');
    }

    const goToProfile = () => {
        console.log('Settings icon clicked');
    };

    return (
        <div className="polls-outer-container">
            <div className="polls-phone-frame">
                {/* Status bar */}
                <div className="polls-status-bar">
                    <span>9:54</span>
                    <span>📶 📡 🔋</span>
                </div>

                {/* Content */}
                <div className="polls-content-area">

                    {/* Welcome text */}
                    <div className="polls-welcome-section">
                        <p className="polls-welcome-subtitle">Your</p>
                        <h1 className="polls-welcome-title">HouseName</h1>
                    </div>
					<div className="chat-btn-bar">
						<button className="chat-bar-btn">Messages</button>
						<button className="chat-bar-btn">Pinned</button>
						<button className="chat-bar-btn active-chat-bar-btn">Polls</button>
					</div>
					
					
					{/* Poll create */}
					<div className="poll">
						<p className="poll-title">Creating a poll...</p>
						<input className="poll-desc-text" type="text" placeholder="Enter your description"></input>
						<div className="poll-option-btn">
							<input className="poll-radio" type="radio" disabled></input>
							<input className="poll-option-text" type="text" placeholder="Option 1"></input>
						</div>
						<div className="poll-option-btn">
							<input className="poll-radio" type="radio" disabled></input>
							<input className="poll-option-text" type="text" placeholder="Option 2"></input>
						</div>
					</div>
					<button className="poll-finish-btn primary-btn">Send Poll</button>
					<button className="poll-finish-btn">Cancel</button>
                </div>


        
                {/* Navigation Bar */}
                <div className="polls-nav-bar">
                    <button onClick={goToChat} className="nav-btn active-btn">
                        <img src={house} desc="House Chat" style={{ width: '50px', height: '50px'}}/>
                    </button>

                    <button className="nav-btn inactive-btn">
                        <img src={neighborhood} desc="Forum" style={{ width: '115px', height: '50px' }}/>
                    </button>

                    <button onClick={goToProfile} className="nav-btn inactive-btn">
                        <img src={settings} desc="Settings" style={{ width: '50px', height: '50px' }}/>
                    </button>

                </div>
            </div>
        </div>
    );
};