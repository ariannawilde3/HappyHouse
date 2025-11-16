import React, { useState, useEffect, useReducer } from 'react';
import { useNavigate } from 'react-router-dom';
import './MakePost.css';
import house from '../assets/images/house.png';
import neighborhood from'../assets/images/neighborhood.png';
import settings from '../assets/images/settings.png';

const API_URL = 'http://localhost:5000/api';


export default function MakePostPage() {
	const [titleBox, setTitle] = useState("");
	const [contentBox, setContent] = useState("");
	const [tags, setTags] = useState([
		{text: "Legal", selected: false},
		{text: "Finding a Roommate", selected: false},
		{text: "Safety", selected: false},
		{text: "Landlord", selected: false}
        ]);
	const [, forceUpdate] = useReducer(x => x + 1, 0);

		
	const [selectedTags, setSelectedTags] = useState(new Set());
	
    const navigate = useNavigate();

    const updateTags = () => {
        console.log('Added/removed tag');
    };
	
	const addPost = async () => {
		console.log("ADDING A POST HOPEFULLY!");
		var data = await fetch(`${API_URL}/addpost/`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
			title: titleBox,
			content: contentBox,
			tags: Array.from(selectedTags)
			}),
		}).then(response => response.json());
        console.log("Server response:", data);
		navigate('/viewPost', {state: data.id});
	}

    const handlePostSubmit = () => {
		addPost();
    };

    const cancelPost = () => {
        navigate('/neighborhood');
        console.log('Post cancelled');
    };

    const goToChat = () => {
        navigate('/makeGC');
        console.log('House icon clicked');
    };

    const goToProfile = () => {
        navigate('/profile');
        console.log('Settings icon clicked');
    };

    return (
        <div className="post-outer-container">
            <div className="post-phone-frame">
                {/* Status bar */}
                <div className="post-status-bar">
                    <span>9:54</span>
                    <span>📶 📡 🔋</span>
                </div>

                {/* Content */}
                <div className="post-content-area">

                    {/* Welcome text */}
                    <div className="post-welcome-section">
                        <p className="post-welcome-subtitle">Your</p>
                        <h1 className="post-welcome-title">Post</h1>
                    </div>

                    {/* Post input fields */}
                    <div className="post-container">
                        <form onSubmit={handlePostSubmit} className="post-form">
                            <input
                                type="text"
                                placeholder='Enter a title'
                                className="title-input"
								onChange = {(e) => setTitle(e.target.value)}
                            />
                            <textarea
                                placeholder='Enter your post description'
                                rows="6"
                                className="desc-input"
								onChange = {(e) => setContent(e.target.value)}
                            />
                        </form>
                    </div>

                    {/* Tags */}				
					<div className="tags-container">
                        <p className="tags-label">Tags:</p>
						{tags.map((tag, index) => (
							<button type="button"
							onClick={() => {
								(tag.selected ? selectedTags.delete(tag.text) : setSelectedTags(selectedTags.add(tag.text)));
								tag.selected = !tag.selected;
								forceUpdate();
							}}
							className={tag.selected ? "tag tag-selected" : "tag"}>
								{tag.text}
							</button>
						))}

					</div>


                    {/* Submit and cancel buttons */}
                    <button type="submit" onClick={handlePostSubmit} className="submit-post-btn">
                        Submit your Post
                    </button>

                    <button type="button" onClick={cancelPost} className="cancel-post-btn">
                        Cancel
                    </button>

            
                    {/* Navigation Bar */}
                    <div className="post-nav-bar">
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
        </div>
    );
};