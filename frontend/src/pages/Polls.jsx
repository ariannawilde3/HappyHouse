import { useNavigate } from 'react-router-dom';
import './Polls.css';
import house from '../assets/images/house.png';
import neighborhood from'../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

export default function Polls() {
    const navigate = useNavigate();

    const goToForum = () => {
        navigate('/neighborhood');
        console.log('House icon clicked');
    }

    const goToProfile = () => {
        navigate('/profile');
        console.log('Settings icon clicked');
    };

    const goToChat = () => {
        navigate('/house');
        console.log('chat icon clicked');
    };

    const goToPins = () => {
        navigate('/pins');
        console.log('pins icon clicked');
    };

    const createPoll = () => {
        navigate('/makePoll');
        console.log('make poll icon clicked');
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
						<button onClick={goToChat} className="chat-bar-btn">Messages</button>
						<button onClick={goToPins} className="chat-bar-btn">Pinned</button>
						<button className="chat-bar-btn active-chat-bar-btn">Polls</button>
					</div>
					
					
					{/* Poll list */}
					<div className="poll">
						<p className="poll-title">mama.mia created a poll</p>
						should refilling the brita be part of the chore chart or just
						a responsibility of everyone's?
						<div className="poll-option-btn">
							<input className="poll-radio" type="radio" name="poll-1"></input>
							{/* */}Yes
						</div>
						<div className="poll-option-btn">
							<input className="poll-radio" type="radio" name="poll-1"></input>
							{/* */}No, everyone's
						</div>
						Poll Expires in 2hrs<br></br>
						2/3 Roommates Voted
					</div>
					
					{/* Create button */}
					<button onClick={createPoll} className="create-btn">+</button>
                </div>


        
                {/* Navigation Bar */}
                <div className="polls-nav-bar">
                    <button className="nav-btn active-btn">
                        <img src={house} alt="House Chat" style={{ width: '50px', height: '50px'}}/>
                    </button>

                    <button onClick={goToForum} className="nav-btn inactive-btn">
                        <img src={neighborhood} alt="Forum" style={{ width: '115px', height: '50px' }}/>
                    </button>

                    <button onClick={goToProfile} className="nav-btn inactive-btn">
                        <img src={settings} alt="Settings" style={{ width: '50px', height: '50px' }}/>
                    </button>

                </div>
            </div>
        </div>
    );
};