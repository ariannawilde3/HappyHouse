import { useNavigate } from 'react-router-dom';
import { useState } from "react";
import './CreatePoll.css';
import NavBar from './NavBar.jsx'


const API_URL = "http://localhost:5000/api";

export default function CreatePoll() {
    const navigate = useNavigate();

    const [pollQuestion, setTitle] = useState("");
    const [pollOpt1, setVoteOpt1] = useState("");
    const [pollOpt2, setVoteOpt2] = useState("");

    const handlePollQuestion = (value) => setTitle(value);
    const handlePollOpt1 = (value) => setVoteOpt1(value);
    const handlePollOpt2 = (value) => setVoteOpt2(value);

    const goToChat = () => {
        navigate('/house');
        console.log('chat icon clicked');
    };

    const goToPins = () => {
        navigate('/pins');
        console.log('pins icon clicked');
    };

    const addPoll = async () => {
        const token = localStorage.getItem('token');
        await fetch(`${API_URL}/pollMV/`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
			title: pollQuestion,
			voteOption1: pollOpt1,
			voteOption2: pollOpt2
			}),
		});
        console.log(pollQuestion +": " + pollOpt1 + ", " + pollOpt2);
		navigate('/polls');
	}



    const sendPoll = () => {
        if (!pollQuestion.trim()) {
        alert("Please enter a question.");
        return;
        }
        if (!pollOpt1.trim()) {
        alert("Please enter option 1.");
        return;
        }
        if (!pollOpt2.trim()) {
        alert("Please enter option 2.");
        return;
        }

        addPoll();
    };

    const cancelPoll = () => {
        navigate('/polls');
        console.log('polls icon clicked');
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
					
					
					{/* Poll create */}
					<div className="poll">
						<p className="poll-title">Creating a poll...</p>
						<input 
                            className="poll-desc-text" 
                            type="text" 
                            placeholder="Enter your description" 
                            value={pollQuestion} 
                            onChange={(e) => handlePollQuestion(e.target.value)}>
                            </input>
						<div className="poll-option-btn">
							<input className="poll-radio" type="radio" disabled></input>
							<input 
                                className="poll-option-text" 
                                type="text" 
                                placeholder="Option 1"
                                value= {pollOpt1}
                                onChange={(e) => handlePollOpt1(e.target.value)}>
                            </input>
						</div>
						<div className="poll-option-btn">
							<input className="poll-radio" type="radio" disabled></input>
							<input 
                                className="poll-option-text" 
                                type="text" 
                                placeholder="Option 2"
                                value= {pollOpt2}
                                onChange={(e) => handlePollOpt2(e.target.value)}>
                            </input>
						</div>
					</div>
					<button onClick={sendPoll} className="poll-finish-btn primary-btn">Send Poll</button>
					<button onClick={cancelPoll} className="poll-finish-btn">Cancel</button>
                </div>


        
                {/* Navigation Bar */}
                <NavBar tab="chat" />
            </div>
        </div>
    );
};