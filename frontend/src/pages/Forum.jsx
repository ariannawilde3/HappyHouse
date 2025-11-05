import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Forum.css';
import filter from '../assets/images/filter.png';
import house from '../assets/images/house.png';
import neighborhood from'../assets/images/neighborhood.png';
import search from '../assets/images/search.png';
import settings from '../assets/images/settings.png';
import ThumbsUp from '../assets/images/ThumbsUp.png';
import ThumbsDown from '../assets/images/ThumbsDown.png';


export default function ForumPage() {
    const [searchQuery, setSearchQuery] = useState('');
    const [anonymousUsername, setAnonymousUsername] = useState('');
    const navigate = useNavigate();

    // Check authentication on component mount
    useEffect(() => {
        const token = localStorage.getItem('token');
        const username = localStorage.getItem('anonymousUsername');
        
        if (!token) {
            // Not authenticated, redirect to login
            console.log('❌ No token found, redirecting to login');
            navigate('/');
        } else {
            // Set the anonymous username
            setAnonymousUsername(username || 'Anonymous User');
            console.log('✅ User authenticated:', username);
        }
    }, [navigate]);

    const handleSearchSubmit = (e) => {
        e.preventDefault();
        console.log('Search submitted: ', searchQuery);
    };

    const handleSearch = (e) => {
        setSearchQuery(e.target.value);
        console.log('User typed:', e.target.value);
    };

    const handleFilter = () => {
        console.log('Filter clicked');
    };

    const updateTags = () => {
        console.log('Added/removed tag');
    };

    const viewPost = () => {
        navigate('/viewPost');
        console.log('post clicked');
    };

    const updateVotes = () => {
        console.log('voted');
    };

    const addPost = () => {
        const userType = localStorage.getItem('userType');
        if (userType === 'GUEST') {
            alert('Guests cannot create posts. Please sign up!');
            return;
        }
        navigate('/makePost');
    };

    const goToChat = () => {
        const userType = localStorage.getItem('userType');
        if (userType == 'GUEST') {
            alert('Guests cannot access private chats. Please sign up!');
            return;
        }
        navigate('/house');
    };

    const goToProfile = () => {
        navigate('/profile');
        console.log('Settings icon clicked');
    };

    const handleLogout = () => {
        // Clear all authentication data
        localStorage.removeItem('token');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('userId');
        localStorage.removeItem('anonymousUsername');
        localStorage.removeItem('userType');
        
        console.log('👋 User logged out');
        
        // Redirect to login
        navigate('/');
    };

    return (
        <div className="forum-outer-container">
            <div className="forum-phone-frame">
                {/* Status bar */}
                <div className="forum-status-bar">
                    <span>9:54</span>
                    <span>📶 📡 🔋</span>
                </div>

                {/* Content */}
                <div className="forum-content-area">

                    {/* Welcome text with anonymous username */}
                    <div className="forum-welcome-section">
                        <p className="forum-welcome-subtitle">Your</p>
                        <h1 className="forum-welcome-title">Neighborhood</h1>
                        {anonymousUsername && (
                            <p style={{
                                color: '#7a9b7e',
                                fontSize: '0.9rem',
                                margin: '0.5rem 0 0 0',
                                fontWeight: '500'
                            }}>
                                Posting as: {anonymousUsername}
                            </p>
                        )}
                    </div>

                    {/* Search Bar */}
                    <div className="searchBar">
                        <form onSubmit={handleSearchSubmit} className="search-form">
                            <div className="search-input-container">
                                <button type="submit" className="search-btn">
                                    <img src={search} alt="Search" style={{ width: '20px', height: '20px' }}/>
                                </button>
                                <input
                                    type="text"
                                    value={searchQuery}
                                    onChange={handleSearch}
                                    placeholder='Search a keyword...'
                                    className="search-input"
                                />
                                <button type="button" onClick={handleFilter} className="filter-btn">
                                    <img src={filter} alt="Filter" style={{ width: '20px', height: '20px' }}/>
                                </button>
                            </div>
                        </form>
                    </div>

                    {/* Tags */}
                    <div className="tags-container">
                        <p className="tags-label">Tags:</p>
                        <button type="button" onClick={updateTags} className="tag">
                            Most Popular
                        </button>
                        <button type="button" onClick={updateTags} className="tag">
                            Finding a Roommate
                        </button>
                        <button type="button" onClick={updateTags} className="tag">
                            Safety
                        </button>
                    </div>

                    {/* No Posts Placeholder
                    <div className="no-posts-txt-container">
                        <p className="no-posts-txt1">No posts...</p>
                        <p className="no-posts-txt2">Maybe try expanding your search?</p>
                    </div>*/}

                    {/* Ex. Post */}
                    <div onClick={viewPost} className="post-container">
                        {/* Post Content */}
                        <h3 className="post-title">My roommate is allergic to peanuts. Why?</h3>
                        <p className="post-content">My roommate is allergic to peanuts and it really weirds me out. She won't ever tell me how it happened or where it started so I just give up. I'm looking for new roommates to take her place, she has two beds in her room, for some odd reason so I can defintely house more people. BLAH BLAH BALAH BALHABLHAJHDFHDOSFHDFUIWEFPBEFBEF</p>
                        
                        {/* Tags and Voting */}
                        <div className="post-stats-container">
                            {/* Tags */}
                            <div className="post-tag">
                                <p>Most Popular</p>
                            </div>
                            <div className="post-tag">
                                <p>Finding a Roommate</p>
                            </div>

                            {/* Voting Buttons */}
                            <div className="votes-container">
                                <p className="votes-label"> 927 Votes</p>
                                <button onClick={updateVotes} className="up-votes">
                                    <img src={ThumbsUp} alt="Like" style={{width: '24px', height: '24px'}}/>
                                </button>
                                <button onClick={updateVotes} className="down-votes">
                                    <img src={ThumbsDown} alt="Dislike" style={{width: '24px', height: '24px'}}/>
                                </button>
                            </div>
                        </div>
                    </div>

                    {/* Logout Button (temporary - you can move this to settings page later) */}
                    <button 
                        onClick={handleLogout}
                        style={{
                            backgroundColor: '#ef4444',
                            color: 'white',
                            border: 'none',
                            padding: '0.75rem 1.5rem',
                            borderRadius: '8px',
                            cursor: 'pointer',
                            fontSize: '0.9rem',
                            fontWeight: '500',
                            marginTop: '1rem'
                        }}
                    >
                        Logout
                    </button>
                </div>

                {/* Post Button */}
                <button onClick={addPost} className="post-btn">
                    +
                </button>
            
                {/* Navigation Bar */}
                <div className="forum-nav-bar">
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
}
