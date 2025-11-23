import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from "react";
import './Chat.css';
import NavBar from './NavBar.jsx';
import { usePinnedMessages } from '../sharedStoragePinnedMessages';

const API_URL = "http://localhost:5000/api";

export default function Chat() {
    const navigate = useNavigate();
    const { pinMessage, pinnedMessages } = usePinnedMessages();
    const [houseName, setHouseName] = useState();

    const goToPins = () => {
        navigate('/pins');
        console.log('pins icon clicked');
    };

    const goToPolls = () => {
        navigate('/polls');
        console.log('polls icon clicked');
    };

    useEffect(() => {
        const fetchHouse = async () => {
            const token = localStorage.getItem("token");
            const res = await fetch(`${API_URL}/gcc/me`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            const data = await res.json();
            setHouseName(data.houseName);
        };
        fetchHouse();
    }, []);

    // hard coded messages for testing currently, will delete obviously 
    const messages = [
        {
            id: 1,
            username: 'archnemesis.pink',
            content: 'so sorry, but you guys need to refill the brita when you drink it... im dying of thirst',
            timestamp: 'Tuesday 5:16pm',
            isYours: false
        },
        {
            id: 2,
            username: 'purple.lion.palace',
            content: 'So sorry I had to run out this morning',
            timestamp: 'Tuesday 5:16pm',
            isYours: true
        },
        {
            id: 3,
            username: 'mama.mia',
            content: 'that is my bad too',
            timestamp: 'Tuesday 5:16pm',
            isYours: false
        },
        {
            id: 4,
            username: 'mama.mia',
            content: 'wait im making a poll right now',
            timestamp: 'Tuesday 5:16pm',
            isYours: false
        }
    ];

    // function for pinning a message called when user clicks on pin button
    const handlePinMessage = (message) => {
        pinMessage(message);
    };

    const isAlreadyPinned = (messageId) => {
        return pinnedMessages.some(function(pinnedMsg) {
            return String(pinnedMsg.messageId) === String(messageId);
        });
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
                        <h1 className="chat-welcome-title">{houseName}</h1>
                    </div>
					<div className="chat-btn-bar">
						<button className="chat-bar-btn active-chat-bar-btn">Messages</button>
						<button onClick={goToPins} className="chat-bar-btn">Pinned</button>
						<button onClick={goToPolls} className="chat-bar-btn">Polls</button>
					</div>
					
                    <p className="message-date">Tuesday 5:16pm</p>
					
                    {/* go through each message in the array that is hard coded */}
					{messages.map(function(message) {
                        const isPinned = isAlreadyPinned(message.id);
    
                        return (
                            <div 
                                key={message.id} 
                                className={message.isYours ? "your-message" : "message"}
                            >
                                <div className="message-header">
                                    <b>{message.username}</b>
                                    <button
                                        className="pin-button"
                                        onClick={() => handlePinMessage(message)}
                                        title={isPinned ? "Already pinned" : "Pin this message"}
                                        disabled={isPinned}
                                    >
                                        📌
                                    </button>
                                </div>
                                <div className="message-content">
                                    {message.content}
                                </div>
                            </div>
                        );
                    })}

                </div>
                {/* Navigation Bar */}
                <NavBar tab="chat"/>
            </div>
        </div>
    );
};