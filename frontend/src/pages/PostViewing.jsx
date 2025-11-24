/*Keira*/


/*
TODO: add line break between tags and votes
TODO: send post id in fetch
TODO: make content area consistent size and consistent with comments
TODO: add anonymous usernames to comment
TODO: add adding comments
TODO: store uservote in user
*/
import NavBar from './NavBar.jsx'
import React, { useState } from 'react';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLocation } from "react-router-dom";
import { upvotePost, downvotePost } from '../api';
import './PostViewing.css';
import ThumbsUp from '../assets/images/ThumbsUp.png';
import ThumbsDown from '../assets/images/ThumbsDown.png';
import { fetchComments, addComment, upvoteComment, downvoteComment } from '../api';

const API_URL = 'http://localhost:5000/api';

export default function ForumPage() {
	
	/*for the main post part*/
    const [post, setPost] = useState({
        id: null,
        title: "404 Error",
        content: "Post not found",
        votes: 0,
        userVote: null,
        tags: [],
		comments: []
    });
	
	const location = useLocation();
	const [comments, setComments] = useState([]);
    const [newCommentContent, setNewCommentContent] = useState('');
    const [isCommentReadOnly, setIsCommentReadOnly] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [loadingComments, setLoadingComments] = useState(false);
	
	const loadContent = async () => {
		// add setError later
		var data = await fetch(`${API_URL}/viewpost/${location.state}`)
		.then(response => response.json());
		console.log(data);
		console.log(data.title);
		
		setPost({
			id: data.objID || data._id || data.id || location.state,
			title: data.title,
			content: data.content,
			votes: data.votes,
			userVote: null,
			tags: data.tags,
			comments: data.comments
		});
		
		await loadComments(data.objID || data._id || data.id || location.state);
	};
	
    const loadComments = async (postId) => {
        setLoadingComments(true);
        try {
            const commentsData = await fetchComments(postId);
            // Backend now returns userVote field for each comment
            setComments(commentsData);
        } catch (error) {
            console.error('Error loading comments:', error);
            setComments(post.comments || []);
        } finally {
            setLoadingComments(false);
        }
    };

    useEffect(() => {
        const userType = localStorage.getItem('userType');
        if (userType === 'GUEST') {
            setIsCommentReadOnly(true);
        }
        loadContent();
    }, []);

    const navigate = useNavigate();

    const handleVote = async (type) => {
        const userType = localStorage.getItem('userType');
        if (userType === 'GUEST') {
            alert('Please sign up to vote on posts!');
            return;
        }

        // Check if user already voted
        if (post.userVote === type) {
            alert(`You have already ${type === 'up' ? 'upvoted' : 'downvoted'} this post`);
            return;
        }

        try {
            let updatedPost;
            if (type === 'up') {
                updatedPost = await upvotePost(post.id);
            } else {
                updatedPost = await downvotePost(post.id);
            }
            
            // Update post state with new vote count
            setPost(prev => ({
                ...prev,
                votes: updatedPost.votes,
                userVote: updatedPost.userVote || (type === 'up' ? 'up' : 'down')
            }));
        } catch (error) {
            console.error('Error voting on post:', error);
            alert(error.message || 'Failed to vote on post');
        }
    };

    const handleCommentInput = () => {
        const userType = localStorage.getItem('userType');
        if (userType === 'GUEST') {
            return;
        }
    };

    const handleCommentSubmit = async (e) => {
        e.preventDefault();
        
        const userType = localStorage.getItem('userType');
        if (userType === 'GUEST') {
            return;
        }

        if (!newCommentContent.trim()) {
            alert('Comment cannot be empty!');
            return;
        }

        if (newCommentContent.length > 1000) {
            alert('Comment cannot exceed 1000 characters!');
            return;
        }

        setIsSubmitting(true);
        try {
            const newComment = await addComment(post.id, newCommentContent.trim());
            
            // Add new comment to the beginning of the list
            setComments(prev => [newComment, ...prev]);
            
            // Clear input
            setNewCommentContent('');
        } catch (error) {
            console.error('Error adding comment:', error);
            if (error.message.includes('Authentication required')) {
                alert('Please log in to add comments!');
            } else {
                alert('Failed to add comment. Please try again.');
            }
        } finally {
            setIsSubmitting(false);
        }
    };

    const handleCommentVote = async (type, commentId) => {
        const userType = localStorage.getItem('userType');
        if (userType === 'GUEST') {
            alert('Please sign up to vote on comments!');
            return;
        }

        // Check if user already voted this way
        const comment = comments.find(c => c.id === commentId);
        if (comment.userVote === type) {
            alert(`You have already ${type === 'up' ? 'upvoted' : 'downvoted'} this comment`);
            return;
        }

        try {
            let updatedComment;
            if (type === 'up') {
                updatedComment = await upvoteComment(post.id, commentId);
            } else {
                updatedComment = await downvoteComment(post.id, commentId);
            }
            
            // Update comment in state with backend response (includes new userVote)
            setComments(prev => prev.map(c =>
                c.id === commentId ? updatedComment : c
            ));
        } catch (error) {
            console.error('Error voting on comment:', error);
            // Show the error message from backend
            alert(error.message || 'Failed to vote on comment');
        }
    };

    const formatTimestamp = (timestamp) => {
    const date = new Date(timestamp);
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);

    if (diffMins < 1) return 'just now';
    if (diffMins < 60) return `${diffMins} minute${diffMins > 1 ? 's' : ''} ago`;
    if (diffHours < 24) return `${diffHours} hour${diffHours > 1 ? 's' : ''} ago`;
    if (diffDays < 7) return `${diffDays} day${diffDays > 1 ? 's' : ''} ago`;
    return date.toLocaleDateString();
};

    const addPost = () => {
        const userType = localStorage.getItem('userType');
        if (userType === 'GUEST') {
            alert('Please sign up to vote on posts!');
            return;
        }
        navigate('/makePost');
        console.log('Post added to forum');
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

                        {/* Comment Input Form */}
                        <form onSubmit={handleCommentSubmit} style={{ marginTop: '1rem' }}>
                            <div style={{
                                border: '2px solid #e5e7eb',
                                borderRadius: '8px',
                                overflow: 'hidden'
                            }}>
                                <textarea
                                    placeholder={isCommentReadOnly ? 'Sign up to comment' : 'Write a comment...'}
                                    className='comment-input'
                                    value={newCommentContent}
                                    onChange={(e) => setNewCommentContent(e.target.value)}
                                    onFocus={handleCommentInput}
                                    readOnly={isCommentReadOnly}
                                    disabled={isSubmitting}
                                    maxLength={1000}
                                    rows={3}
                                    style={{
                                        width: '100%',
                                        padding: '0.75rem',
                                        border: 'none',
                                        outline: 'none',
                                        fontFamily: 'inherit',
                                        fontSize: '0.95rem',
                                        resize: 'vertical',
                                        cursor: isCommentReadOnly ? 'not-allowed' : 'text',
                                        opacity: isCommentReadOnly ? 0.6 : 1
                                    }}
                                />
                                <div style={{
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    alignItems: 'center',
                                    padding: '0.5rem 0.75rem',
                                    backgroundColor: '#f9fafb',
                                    borderTop: '1px solid #e5e7eb'
                                }}>
                                    <span style={{ fontSize: '0.875rem', color: '#6b7280' }}>
                                        {newCommentContent.length}/1000
                                    </span>
                                    <button
                                        type="submit"
                                        disabled={isSubmitting || isCommentReadOnly || !newCommentContent.trim()}
                                        style={{
                                            backgroundColor: isSubmitting || isCommentReadOnly || !newCommentContent.trim() ? '#9ca3af' : '#7a9b7e',
                                            color: 'white',
                                            border: 'none',
                                            borderRadius: '6px',
                                            padding: '0.5rem 1.5rem',
                                            fontSize: '0.95rem',
                                            cursor: isSubmitting || isCommentReadOnly || !newCommentContent.trim() ? 'not-allowed' : 'pointer',
                                            fontWeight: '500'
                                        }}
                                    >
                                        {isSubmitting ? 'Posting...' : 'Post Comment'}
                                    </button>
                                </div>
                            </div>
                        </form>

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
                                
                                <button onClick={() => handleVote('up')} style={{
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
                                
                                <button onClick={() => handleVote('down')} style={{
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

                    {/* Comments Section Title */}
                    {comments.length > 0 && (
                        <h3 style={{
                            fontSize: '1.1rem',
                            fontWeight: '600',
                            color: '#1f2937',
                            marginBottom: '1rem',
                            marginTop: '1.5rem'
                        }}>
                            Comments ({comments.length})
                        </h3>
                    )}

                    {/* Loading State */}
                    {loadingComments && (
                        <div style={{
                            textAlign: 'center',
                            padding: '2rem',
                            color: '#6b7280',
                            fontStyle: 'italic'
                        }}>
                            Loading comments...
                        </div>
                    )}

                    {/* No Comments Message */}
                    {!loadingComments && comments.length === 0 && (
                        <div style={{
                            textAlign: 'center',
                            padding: '2rem',
                            background: '#f9fafb',
                            border: '2px dashed #d1d5db',
                            borderRadius: '8px',
                            color: '#6b7280'
                        }}>
                            <p>No comments yet. Be the first to comment!</p>
                        </div>
                    )}

                    {/* Comments List */}
                    {!loadingComments && comments.map((comment, index) => (
                        <React.Fragment key={comment.id}>
                            {/* Comment Card */}
                            <div style={{
                                backgroundColor: 'white',
                                borderRadius: '12px',
                                padding: '1rem 1.5rem',
                                marginBottom: '0.75rem',
                                width: '100%',
                                boxShadow: '0 1px 3px rgba(0,0,0,0.1)',
                                boxSizing: 'border-box'
                            }}>
                                {/* Comment Header - USERNAME AND TIMESTAMP */}
                                <div style={{
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    alignItems: 'center',
                                    marginBottom: '0.5rem'
                                }}>
                                    <span style={{
                                        fontWeight: '600',
                                        color: '#1f2937',
                                        fontSize: '0.9rem'
                                    }}>
                                        {comment.username || comment.anonymousUsername || 'Anonymous'}
                                    </span>
                                    <span style={{
                                        fontSize: '0.75rem',
                                        color: '#6b7280'
                                    }}>
                                        {comment.createdAt ? formatTimestamp(comment.createdAt) : 'just now'}
                                    </span>
                                </div>

                                {/* Comment text */}
                                <p style={{
                                    color: '#4b5563',
                                    fontSize: '0.95rem',
                                    lineHeight: '1.5',
                                    margin: '0 0 0.75rem 0',
                                    wordWrap: 'break-word'
                                }}>
                                    {comment.content}
                                </p>

                                {/* Vote section */}
                                <div style={{ 
                                    display: 'flex', 
                                    alignItems: 'center', 
                                    gap: '0.75rem'
                                }}>
                                    {/* Thumbs up */}
                                    <button 
                                        onClick={() => handleCommentVote('up', comment.id)} 
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

                                    <span style={{ 
                                        color: '#6b7280',
                                        fontSize: '0.85rem',
                                        fontWeight: '600',
                                        minWidth: '2rem',
                                        textAlign: 'center'
                                    }}>
                                        {comment.votes || 0}
                                    </span>
                                    
                                    {/* Thumbs down */}
                                    <button 
                                        onClick={() => handleCommentVote('down', comment.id)} 
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
                        </React.Fragment>
                    ))}

                </div>
                {/* END OF CONTENT AREA */}


                {/* Post Button */}
                <button onClick={addPost} className="post-btn">
                    +
                </button>
        
                {/* Navigation Bar */}
                
				<NavBar tab="forum" />
            </div>
        </div>
    );
};