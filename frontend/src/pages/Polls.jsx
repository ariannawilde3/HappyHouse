 import { useNavigate } from 'react-router-dom'; 
 import './Polls.css'; 
 import NavBar from './NavBar.jsx'; 
 import { useState, useEffect } from "react";

const API_URL = "http://localhost:5000/api";


export default function Polls() {
    const [polls, setPolls] = useState([]);
    const navigate = useNavigate();
    const [houseName, setHouseName] = useState("");
    const [selected, setSelected] = useState({});
    const [roommateCount, setRoommateCount] = useState(0);

     const createPoll = () => {
        navigate('/makePoll');
        console.log('make poll icon clicked');
    };

    const goToChat = () => {
        navigate('/house');
        console.log('chat icon clicked');
    };

    const goToPins = () => {
        navigate('/pins');
        console.log('pins icon clicked');
    };

    //when user votes
    const onChoose = async (pollId, option) => {
        const poll = polls.find(p => p.id === pollId); 
        if (poll?.hasVoted || selected[pollId]) {  //if alr voted or closed
            alert("You've already voted on this poll!"); 
            return; 
        }
        setSelected(prev => ({ ...prev, [pollId]: option }));

        const token = localStorage.getItem("token"); //gives backend choice
        const res = await fetch(`${API_URL}/pollMV/${pollId}/vote`, {
            method: "POST",
            headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({ option }),
        });

        const updated = await res.json(); // updated poll from backend

        // update counts in local state
        setPolls(prev =>
            prev.map(p =>
            (p.id === updated.id)
                ? {
                    ...p,
                    totalVotes: updated.totalVotes,
                    votesFor1: updated.votesFor1,
                    votesFor2: updated.votesFor2,
                }
                : p
            )
        );
        console.log(`Voted option ${option} on poll ${pollId}`); //debugging
    };

    //displays all polls when user clicks on poll tab
    useEffect(() => {
        const fetchPolls = async () => {
        const token = localStorage.getItem("token");
        const headers = { Authorization: `Bearer ${token}` };


        const res = await fetch(`${API_URL}/pollMV`, { headers });
        const data = await res.json();

        const houseRes = await fetch(`${API_URL}/gcc/me`, { headers });
        const houseData = await houseRes.json();
        const roomieCount = houseData.expectedRoomieCount; //for totals
        const housename = houseData.houseName; //just to display

        const pollsWithNames = await Promise.all( // gets user names to see whos voted
            data.map(async (poll) => {
                const url = `${API_URL}/users/anonymous-name?email=${encodeURIComponent(poll.emailOfCreator)}`;
                const userRes = await fetch(url, { headers });
                const creatorName = userRes.ok ? await userRes.text() : "Anonymous";
                return { ...poll, creatorName };
            })
        );
        setPolls(pollsWithNames);

        setHouseName(housename);
        setRoommateCount(roomieCount);
        console.log(houseData.expectedRoomieCount);
        console.log(roomieCount);
        }; fetchPolls();
    }, []);



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
                        <p className="polls-welcome-subtitle">Welcome to</p>
                        <h1 className="polls-welcome-title">{houseName || "Loading..."}</h1>
                    </div>
					<div className="chat-btn-bar">
						<button onClick={goToChat} className="chat-bar-btn">Messages</button>
						<button onClick={goToPins} className="chat-bar-btn">Pinned</button>
						<button className="chat-bar-btn active-chat-bar-btn">Polls</button>
					</div>
					
					
		{/* polls list */}
        <>
        {polls.length === 0 && (
          <div className="empty-state">No polls yet.</div>
        )}

        {polls           
            .map((p) => {
                const pid = p.id;
                const locked = p.hasVoted || !!selected[pid]; // per-poll lock after user votes
                const resolved = p.totalVotes == roommateCount;
                const opt1Wins = p.votesFor1 > p.votesFor2;
                const opt2Wins = p.votesFor2 > p.votesFor1;
                const isTie    = p.votesFor1 === p.votesFor2;
                    return(
                        <div key={p.id} className="poll">
                            <p className="poll-title">
                                {p.creatorName} created a poll.<br></br><br></br>{p.title}
                            </p>


                            {/* Option 1 */}
<div
  className={
    `poll-option-btn` +
    (resolved && opt1Wins ? " poll-option-winner" : "") +
    (resolved && isTie    ? " poll-option-tie"     : "") +
    (resolved && opt2Wins ? " poll-option-loser"   : "")
  }
>
  <input
    className="poll-radio"
    type="radio"
    name={`poll-${pid}`}
    checked={selected[pid] === 1}
    disabled={resolved}     // optional: lock when resolved
    onChange={() => onChoose(pid, 1)}
    readOnly
  />
  {p.voteOpt1 ?? p.voteOption1}
</div>

{/* Option 2 */}
<div
  className={
    `poll-option-btn` +
    (resolved && opt2Wins ? " poll-option-winner" : "") +
    (resolved && isTie    ? " poll-option-tie"     : "") +
    (resolved && opt1Wins ? " poll-option-loser"   : "")
  }
>
  <input
    className="poll-radio"
    type="radio"
    name={`poll-${pid}`}
    checked={selected[pid] === 2}
    disabled={resolved}  
    onChange={() => onChoose(pid, 2)}
    readOnly
  />
  {p.voteOpt2 ?? p.voteOption2}
</div>


                            <span key = {pid} className="poll-votes">
                                {(p.totalVotes)}/{roommateCount} votes.
                            </span>
                        </div>);
            }).reverse()}
        </>
                    
		{/* Create button */}
		<button onClick={createPoll} className="create-btn">+</button>
        </div>


        
                {/* Navigation Bar */}
                <NavBar tab="chat" />
            </div>
        </div>
    );
};