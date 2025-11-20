const API_URL = 'http://localhost:5000/api';

const getAuthToken = () => {
  return localStorage.getItem('token');
};

export const getCurrentUser = async () => {
  const token = getAuthToken();
  const response = await fetch(`${API_URL}/users/me`, {
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  
  if (!response.ok) {
    throw new Error('Failed to fetch user');
  }
  
  return response.json();
};

export const updateProfile = async (profileData) => {
  const token = getAuthToken();
  const response = await fetch(`${API_URL}/users/profile`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(profileData),
  });
  
  if (!response.ok) {
    const error = await response.text();
    throw new Error(error);
  }
  
  return response.json();
};

// comments api

/**
 * Fetch all comments for a post
 */
export const fetchComments = async (postId) => {
  const response = await fetch(`${API_URL}/posts/${postId}/comments`);
  
  if (!response.ok) {
    throw new Error('Failed to fetch comments');
  }
  
  return response.json();
};

/**
 * Add a new comment to a post
 */
export const addComment = async (postId, content) => {
  const token = getAuthToken();
  const response = await fetch(`${API_URL}/posts/${postId}/comments`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify({ content }),
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.error || 'Failed to add comment');
  }
  
  return response.json();
};

/**
 * Upvote a comment
 */
export const upvoteComment = async (postId, commentId) => {
  const token = getAuthToken();
  const response = await fetch(`${API_URL}/posts/${postId}/comments/${commentId}/upvote`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.error || 'Failed to upvote comment');
  }
  
  return response.json();
};

/**
 * Downvote a comment
 */
export const downvoteComment = async (postId, commentId) => {
  const token = getAuthToken();
  const response = await fetch(`${API_URL}/posts/${postId}/comments/${commentId}/downvote`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.error || 'Failed to downvote comment');
  }
  
  return response.json();
};

/**
 * Upvote a post
 */
export const upvotePost = async (postId) => {
  const token = getAuthToken();
  
  if (!token) {
    throw new Error('Authentication required - no token found');
  }
  
  const response = await fetch(`${API_URL}/viewpost/${postId}/upvote`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.error || 'Failed to upvote post');
  }
  
  return await response.json();
};

/**
 * Downvote a post
 */
export const downvotePost = async (postId) => {
  const token = getAuthToken();
  
  if (!token) {
    throw new Error('Authentication required - no token found');
  }
  
  const response = await fetch(`${API_URL}/viewpost/${postId}/downvote`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.error || 'Failed to downvote post');
  }
  
  return await response.json();
};