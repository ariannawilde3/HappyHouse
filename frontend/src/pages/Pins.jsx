import { useNavigate } from 'react-router-dom';
import './Pins.css';
import NavBar from './NavBar.jsx';


export default function Pins() {
    const navigate = useNavigate();

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
					
					
					{/* Pin 1 */}
					<div className="pin">
						<p className="pin-date">Tuesday 5:16pm</p>
						<div className="pin-content">
							New roommate chore chart!
							<ul>
								<li>Drew: Kitchen cleaning</li>
								<li>Leili: Sweep all floors</li>
								<li>Keira: Dust</li>
								<li>Arianna: Resident handyman</li>
								<li>Holly: Grocery list keeper</li>
							</ul>
						</div>
					</div>
					
					{/* Pin 2 */}
					<div className="pin">
						<p className="pin-date">Today 11:12am</p>
						<div className="pin-content">
							REMINDER!! My brother and his girlfriend will be
							staying at our place NEXT week from Saturday to Thursday.
						</div>
					</div>
                </div>


        
                {/* Navigation Bar */}
                <NavBar tab="chat" />
            </div>
        </div>
    );
};