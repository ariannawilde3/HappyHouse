import { useNavigate } from 'react-router-dom';
import './Pins.css';
import NavBar from './NavBar.jsx';
import { usePinnedMessages } from '../sharedStoragePinnedMessages';

export default function Pins() {
    const navigate = useNavigate();

    const { pinnedMessages, unpinMessage, loading } = usePinnedMessages();

    const goToChat = () => {
        navigate('/house');
        console.log('chat icon clicked');
    };

    const goToPolls = () => {
        navigate('/polls');
        console.log('polls icon clicked');
    };

    const handleUnpin = (messageId) => {
    if (window.confirm('Unpin this message?')) {
        unpinMessage(messageId);
    }
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
                        <p style={{ textAlign: 'center', color: '#6b7280', marginTop: '2rem' }}>
                            Loading pinned messages...
                        </p>
                    ) : pinnedMessages.length === 0 ? (
                        <p style={{ textAlign: 'center', color: '#6b7280', marginTop: '2rem' }}>
                            No pinned messages yet. Pin messages from the chat!
                        </p>
                    ) : (
                        pinnedMessages.map(function(message) {
                            return (
                                <div key={message.id} className="pin">
                                    <div className="pin-header">
                                        <p className="pin-date">{message.timestamp}</p>
                                        <button 
                                            className="unpin-button"
                                            onClick={() => handleUnpin(message.id)}
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