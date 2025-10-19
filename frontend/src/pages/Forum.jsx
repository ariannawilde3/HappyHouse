import React, { useState } from 'react';
import './Forum.css';
import filter from '../assets/images/filter.png';
import house from '../assets/images/house.png';
import neighborhood from'../assets/images/neighborhood.png';
import search from '../assets/images/search.png';
import settings from '../assets/images/settings.png';

export default function ForumPage() {
    // Add state for search
    const [searchQuery, setSearchQuery] = useState('');

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

    const addPost = () => {
        console.log('Post added to forum');
    };

    const goToChat = () => {
        console.log('House icon clicked');
    };

    const goToProfile = () => {
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

                {/* Content */}
                <div className="forum-content-area">

                    {/* Welcome text */}
                    <div className="forum-welcome-section">
                        <p className="forum-welcome-subtitle">Your</p>
                        <h1 className="forum-welcome-title">Neighborhood</h1>
                    </div>

                    {/* Search Bar */}
                    <div className="searchBar">
                        <form onSubmit={handleSearchSubmit} className="search-form">
                            <div className="search-input-container">
                                <button type="submit" className="search-btn">
                                    <img src={search} desc="Search" style={{ width: '20px', height: '20px' }}/>
                                </button>
                                <input
                                    type="text"
                                    value={searchQuery}
                                    onChange={handleSearch}
                                    placeholder='Search a keyword...'
                                    className="search-input"
                                />
                                <button type="button" onClick={handleFilter} className="filter-btn">
                                    <img src={filter} desc="Filter" style={{ width: '20px', height: '20px' }}/>
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

                    {/* No Posts Placeholder */}
                    <div className="no-posts-txt-container">
                        <p className="no-posts-txt1">No posts...</p>
                        <p className="no-posts-txt2">Maybe try expanding your search?</p>
                    </div>

                    {/* Post Button */}
                    <button onClick={addPost} className="post-btn">
                        +
                    </button>
            
                    {/* Navigation Bar */}
                    <div className="forum-nav-bar">
                        <button onClick={goToChat} className="chat-btn">
                            <img src={house} desc="House Chat" style={{ width: '50px', height: '50px' }}/>
                        </button>

                        <button className="forum-btn">
                            <img src={neighborhood} desc="Forum" style={{ width: '115px', height: '50px' }}/>
                        </button>

                        <button onClick={goToProfile} className="profile-btn">
                            <img src={settings} desc="Settings" style={{ width: '50px', height: '50px' }}/>
                        </button>
                    </div> 
                </div>
            </div>
        </div>
    );
};