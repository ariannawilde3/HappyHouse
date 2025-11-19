import { useNavigate } from 'react-router-dom';
import './Pins.css';
import NavBar from './NavBar.jsx';
import { usePinnedMessages } from '../sharedStoragePinnedMessages';

export default function Pins() {
    const navigate = useNavigate();

    // get pinnned messages array, unpin fun, and loading state 
    const { pinnedMessages, unpinMessage, loading } = usePinnedMessages();

    const goToChat = () => {
        navigate('/house');
        console.log('chat icon clicked');
    };

    const goToPolls = () => {
        navigate('/polls');
        console.log('polls icon clicked');
    };

    return (
        <div className="pins-outer-container">
            <div className="pins-phone-frame">
                {/* Status bar */}
                <div className="pins-status-bar">
                    <span>9:54</span>
                    <span>📶 📡 🔋</span>
                </div>

                {/* Content */}
                <div className="pins-content-area">

                    {/* Welcome text */}
                    <div className="pins-welcome-section">
                        <p className="pins-welcome-subtitle">Your</p>
                        <h1 className="pins-welcome-title">HouseName</h1>
                    </div>
					<div className="chat-btn-bar">
						<button onClick={goToChat} className="chat-bar-btn">Messages</button>
						<button className="chat-bar-btn active-chat-bar-btn">Pinned</button>
						<button onClick={goToPolls} className="chat-bar-btn">Polls</button>
					</div>
					
					{loading ? (
                        // this is here if there is a delay from backend and it just says loading pinned messages
                        <p style={{ textAlign: 'center', color: '#6b7280', marginTop: '2rem' }}>
                            Loading pinned messages...
                        </p>
                    // if no pinned messages, display this message
                    ) : pinnedMessages.length === 0 ? (
                        <p style={{ textAlign: 'center', color: '#6b7280', marginTop: '2rem' }}>
                            No pinned messages yet. Pin messages from the chat!
                        </p>
                    ) : (
                        // if pinned messages exits then do this and go through each pinned message
                        pinnedMessages.map(function(message) {
                            return (
                                // details for displaying pin message 
                                <div key={message.id} className="pin">
                                    <div className="pin-header">
                                        <p className="pin-date">{message.timestamp}</p>
                                        <button 
                                            className="unpin-button"
                                            onClick={() => unpinMessage(message.id)}
                                            title="Unpin message"
                                        >
                                            ❌
                                        </button>
                                    </div>
                                    <div className="pin-content">
                                        <b>{message.username}:</b> {message.content}
                                    </div>
                                </div>
                            );
                        })
                    )}
					
                </div>

                {/* Navigation Bar */}
                <NavBar tab="chat" />
            </div>
        </div>
    );
};