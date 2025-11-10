/*Keira*/


/*
TODO: add line break between tags and votes
TODO: send post id in fetch
TODO: make content area consistent size and consistent with comments
TODO: add anonymous usernames to comment
TODO: add adding comments
TODO: store uservote in user
*/
import React, { useState } from 'react';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLocation } from "react-router-dom";
import './PostViewing.css';
import house from '../assets/images/house.png';
import neighborhood from'../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';
import ThumbsUp from '../assets/images/ThumbsUp.png';
import ThumbsDown from '../assets/images/ThumbsDown.png';

const API_URL = 'http://localhost:5000/api';

export default function ForumPage() {
	
	/*for the main post part*/
    const [post, setPost] = useState({
        id: -1,
        title: "404 Error",
        content: "Post not found",
        votes: 0,
        userVote: null,
        tags: [],
		comments: []
    });
	
	const location = useLocation();
	console.log(location.state);
	
	const loadContent = async () => {
		// add setError later
		var data = await fetch(`${API_URL}/viewpost/${location.state}`)
		.then(response => response.json());
		console.log(data);
		console.log(data.title);
		
		/*for the main post part*/
		/*const [post, setPost] = useState({
			id: 1,
			title: "My roommate is allergic to peanuts. Why?",
			content: "My roommate is allergic to peanuts and it really weirds me out. She won't ever tell me how it happened or where it started so I just give up. I'm looking for new roommates to take her place, she has two beds in her room, for some odd reason so I can definitely house more people. BLAH BLAH BALAH BALHABLHAJHDFHDOSFHDFUIWEFPBEFBEF",
			votes: 927,
			userVote: null,
			tags: ["Most Popular", "Finding a Roommate"]
		});*/
		
		setPost({
			id: 1,
			title: data.title,
			content: data.content,
			votes: data.votes,
			userVote: null,
			tags: data.tags,
			comments: data.comments
		});
		
		setComments(data.comments);
	};
	
	useEffect(() => {loadContent();}, []);



    /*comments*/
     const [comments, setComments] = useState([
        ]);

    const navigate = useNavigate();

    /* Vote handling function*/
    // const handleVote = (type) => {
    //     setPost(prev => ({
    //         ...prev,
    //         votes: prev.userVote === type ? prev.votes - (type === 'up' ? 1 : -1) : 
    //                prev.userVote ? prev.votes + (type === 'up' ? 2 : -2) :
    //                prev.votes + (type === 'up' ? 1 : -1),
    //         userVote: prev.userVote === type ? null : type
    //     }));
    // };

    const handleVote = (type, itemId, isPost = false) => {
        if (isPost) {
            setPost(prev => ({
                ...prev,
                votes: prev.userVote === type ? prev.votes - (type === 'up' ? 1 : -1) : 
                       prev.userVote ? prev.votes + (type === 'up' ? 2 : -2) :
                       prev.votes + (type === 'up' ? 1 : -1),
                userVote: prev.userVote === type ? null : type
            }));
        } else {
            setComments(prev => prev.map(comment => 
                comment.id === itemId ? {
                    ...comment,
                    votes: comment.userVote === type ? comment.votes - (type === 'up' ? 1 : -1) :
                           comment.userVote ? comment.votes + (type === 'up' ? 2 : -2) :
                           comment.votes + (type === 'up' ? 1 : -1),
                    userVote: comment.userVote === type ? null : type
                } : comment
            ));
        }
    };

    const addPost = () => {
        navigate('/makePost');
        console.log('Post added to forum');
    };

    const goToChat = () => {
        const userType = localStorage.getItem('userType');
        if (userType == 'GUEST') {
            alert('Guests cannot access private chats. Please sign up!');
            return;
        }
        navigate('/makeGC');
    };

    const goToProfile = () => {
        navigate('/profile');
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

                {/* Content area*/}
                <div className="forum-content-area">

                    {/* welcome section*/}
                    <div className="forum-welcome-section">
                        <p className="forum-welcome-subtitle">
                            {/* takes you back to previous page*/}
                            <button onClick={() => window.history.back()} style={{ 
                                background: 'none', 
                                border: 'none', 
                                color: '#6b7280', 
                                fontSize: '1.125rem',
                                cursor: 'pointer',
                                padding: '0',
                                marginRight: '0.5rem'
                            }}>
                                &lt;
                            </button>
                            Back to Your
                        </p>
                        <h1 className="forum-welcome-title">Neighborhood</h1>
                    </div>

                    {/*Main Post Section*/}
                    <div style={{
                        backgroundColor: 'white',
                        borderRadius: '12px',
                        padding: '1.5rem',
                        marginBottom: '1rem',
                        boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
                    }}>
                        {/* Post Title */}
                        <h3 style={{
                            fontSize: '1.25rem',
                            fontWeight: '600',
                            color: '#1f2937',
                            marginTop: 0,
                            marginBottom: '1rem'
                        }}>
                            {post.title}
                        </h3>
                        
                        {/* Post Content */}
                        <p style={{
                            color: '#4b5563',
                            fontSize: '0.95rem',
                            lineHeight: '1.6',
                            marginBottom: '1rem'
                        }}>
                            {post.content}
                        </p>

                        {/* Tags and Voting */}
                        <div style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'space-between',
                            flexWrap: 'wrap',
                            gap: '0.5rem'
                        }}>
                            {/* Tags */}
                            <div style={{ display: 'flex', gap: '0.5rem' }}>
                                {post.tags.map((tag, index) => (
                                    <span key={index} style={{
                                        backgroundColor: tag === "Most Popular" ? '#7a9b7e' : '#6b9b7f',
                                        color: 'white',
                                        padding: '0.4rem 0.8rem',
                                        borderRadius: '6px',
                                        fontSize: '0.85rem'
                                    }}>
                                        {tag}
                                    </span>
                                ))}
                            </div>

                            {/* Vote buttons */}
                            <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                                <span style={{ color: '#6b7280', fontSize: '0.9rem' }}>
                                    {post.votes} Votes
                                </span>
                                
                                <button onClick={() => handleVote('up', post.id, true)} style={{
                                    background: 'none',
                                    border: 'none',
                                    cursor: 'pointer',
                                    fontSize: '1.5rem',
                                    color: post.userVote === 'up' ? '#7a9b7e' : '#9ca3af'
                                }}>
                                    <img 
                                        src={ThumbsUp} 
                                        alt="Like" 
                                        style={{ 
                                            width: '24px', 
                                            height: '24px',
                                            opacity: post.userVote === 'up' ? 1 : 0.5  // Dim when not selected
                                        }}
                                    />
                                </button>
                                
                                <button onClick={() => handleVote('down', post.id, true)} style={{
                                    background: 'none',
                                    border: 'none',
                                    cursor: 'pointer',
                                    fontSize: '1.5rem',
                                    color: post.userVote === 'down' ? '#ef4444' : '#9ca3af'
                                }}>
                                    <img 
                                        src={ThumbsDown} 
                                        alt="Dislike" 
                                        style={{ 
                                            width: '24px', 
                                            height: '24px',
                                            opacity: post.userVote === 'down' ? 1 : 0.5  // Dim when not selected
                                        }}
                                    />
                                </button>
                            </div>
                        </div>
                    </div>
                    {/* END OF MAIN POST SECTION */}

                    {/* ADD COMMENTS SECTION HERE */}
                    {/* Vertical connecting line 
                    <div style={{
                        width: '3px',
                        height: '30px',
                        backgroundColor: '#7a9b7e',
                        alignSelf: 'flex-start',
                        marginLeft: '1.5rem',
                        marginBottom: '0.5rem'
                    }}></div>*/}

                    {/* Comments */}
                    {comments.map((comment, index) => (
                        <React.Fragment key={comment.id}>
                            {/* Comment Card */}
                            <div style={{
                                backgroundColor: 'white',
                                borderRadius: '12px',
                                padding: '1rem 1.5rem',
                                marginBottom: '0.5rem',
                                boxShadow: '0 1px 3px rgba(0,0,0,0.1)',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'space-between',
                                gap: '1rem',
                                width: '100%'
                            }}>
                                {/* Comment text */}
                                <p style={{
                                    color: '#4b5563',
                                    fontSize: '0.95rem',
                                    margin: 0,
                                    flex: 1
                                }}>
                                    {comment.content}
                                </p>

                                {/* Vote section */}
                                <div style={{ 
                                    display: 'flex', 
                                    alignItems: 'center', 
                                    gap: '0.75rem', 
                                    flexShrink: 0 
                                }}>
                                    <span style={{ 
                                        color: '#6b7280', 
                                        fontSize: '0.85rem', 
                                        whiteSpace: 'nowrap' 
                                    }}>
                                        {comment.votes} Votes
                                    </span>
                                    
                                    {/* Thumbs up */}
                                    <button 
                                        onClick={() => handleVote('up', comment.id, false)} 
                                        style={{
                                            background: 'none',
                                            border: 'none',
                                            cursor: 'pointer',
                                            padding: 0
                                        }}
                                    >
                                        <img 
                                            src={ThumbsUp} 
                                            alt="Like" 
                                            style={{ 
                                                width: '20px', 
                                                height: '20px',
                                                opacity: comment.userVote === 'up' ? 1 : 0.5
                                            }}
                                        />
                                    </button>
                                    
                                    {/* Thumbs down */}
                                    <button 
                                        onClick={() => handleVote('down', comment.id, false)} 
                                        style={{
                                            background: 'none',
                                            border: 'none',
                                            cursor: 'pointer',
                                            padding: 0
                                        }}
                                    >
                                        <img 
                                            src={ThumbsDown} 
                                            alt="Dislike" 
                                            style={{ 
                                                width: '20px', 
                                                height: '20px',
                                                opacity: comment.userVote === 'down' ? 1 : 0.5
                                            }}
                                        />
                                    </button>
                                </div>
                            </div>
                            
                            {/* Vertical line between comments */}
                            {index < comments.length - 1 && (
                                <div style={{
                                    width: '3px',
                                    height: '20px',
                                    backgroundColor: '#7a9b7e',
                                    alignSelf: 'flex-start',
                                    marginLeft: '0',
                                    marginBottom: '0.5rem'
                                }}></div>
                            )}
                        </React.Fragment>
                    ))}

                </div>
                {/* END OF CONTENT AREA */}


                {/* Post Button */}
                <button onClick={addPost} className="post-btn">
                    +
                </button>
        
                {/* Navigation Bar */}
                <div className="view-post-nav-bar">
                    <button onClick={goToChat} className="nav-btn inactive-btn">
                        <img src={house} alt="House Chat" style={{ width: '50px', height: '50px'}}/>
                    </button>
                
                    <button className="nav-btn active-btn">
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