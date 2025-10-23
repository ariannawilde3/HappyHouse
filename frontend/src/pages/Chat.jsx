import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Chat.css';
import house from '../assets/images/house.png';
import neighborhood from'../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

export default function Chat() {
    const navigate = useNavigate();

    const goToForum = () => {
        navigate('/neighborhood');
        console.log('House icon clicked');
    };

    const goToProfile = () => {
        navigate('/profile');
        console.log('Settings icon clicked');
    };

    const goToPins = () => {
        navigate('/pins');
        console.log('pins icon clicked');
    };

    const goToPolls = () => {
        navigate('/polls');
        console.log('polls icon clicked');
    };

    return (
        <div className="chat-outer-container">
            <div className="chat-phone-frame">
                {/* Status bar */}
                <div className="chat-status-bar">
                    <span>9:54</span>
                    <span>📶 📡 🔋</span>
                </div>

                {/* Content */}
                <div className="chat-content-area">

                    {/* Welcome text */}
                    <div className="chat-welcome-section">
                        <p className="chat-welcome-subtitle">Your</p>
                        <h1 className="chat-welcome-title">HouseName</h1>
                    </div>
					<div className="chat-btn-bar">
						<button className="chat-bar-btn active-chat-bar-btn">Messages</button>
						<button onClick={goToPins} className="chat-bar-btn">Pinned</button>
						<button onClick={goToPolls} className="chat-bar-btn">Polls</button>
					</div>
					
                    <p className="message-date">Tuesday 5:16pm</p>
					
					{/* message 1 */}
					<div className="message">
						<div className="message-content">
							<b>archnemesis.pink</b>
							<ul>
								so sorry, but you guys need to refill the brita when you drink it... im dying of thirst
							</ul>
						</div>
					</div>
					
					{/* Message 2 made by*/}
					<div className="your-message">
						<div className="message-content">
                            <b>purple.lion.palace</b>
							So sorry I had to run out this morning
						</div>
					</div>

                    {/* Message 3 */}
					<div className="message">
                        <b>mama.mia</b>
						<div className="message-content">
							that is my bad too
						</div>
					</div>

                    {/* Message 4 */}
					<div className="message">
                        <b>mama.mia</b>
						<div className="message-content">
							wait im making a poll right now
						</div>
					</div>

                </div>



        
                {/* Navigation Bar */}
                <div className="chat-nav-bar">
                    <button className="nav-btn active-btn">
                        <img src={house} desc="House Chat" style={{ width: '50px', height: '50px'}}/>
                    </button>

                    <button onClick={goToForum} className="nav-btn inactive-btn">
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