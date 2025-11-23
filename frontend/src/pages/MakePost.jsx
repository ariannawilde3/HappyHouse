import React, { useState, useEffect, useReducer } from 'react';
import { useNavigate } from 'react-router-dom';
import './MakePost.css';
import NavBar from './NavBar.jsx';


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
	const [error, setError] = useState(1);
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
		
		setError(data.status);
		forceUpdate();
		if (data.status == 1) {
			console.log("ADDING A POST HOPEFULLY!");
			navigate('/viewPost', {state: data.id});
		}
	}

    const handlePostSubmit = () => {
		addPost();
    };

    const cancelPost = () => {
        navigate('/neighborhood');
        console.log('Post cancelled');
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
						{error == 1 ? <div></div> :
							<div className="error-message">
								<p> ERROR!</p>
								{error%2 == 0 ? <p>Your post must have a title</p> : <div></div>}
								{error % 5 == 0 ? <p>Your title can't have more than 50 characters</p> : <div></div>}
								{error % 3 == 0 ? <p>Your post must have a body</p> : <div></div>}
								{error % 7 == 0 ? <p>Your body can't have more than 1200 characters</p> : <div></div>}
							</div>}
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
                    <NavBar tab="forum" />
                </div>
            </div>
        </div>
    );
};