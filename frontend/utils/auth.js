// src/utils/auth.js
// Authentication utility functions for HappyHouse

const API_URL = 'http://localhost:5000/api';

export const auth = {
  /**
   * Get stored JWT token
   */
  getToken: () => {
    return localStorage.getItem('token');
  },

  /**
   * Get stored refresh token
   */
  getRefreshToken: () => {
    return localStorage.getItem('refreshToken');
  },

  /**
   * Get anonymous username
   */
  getAnonymousUsername: () => {
    return localStorage.getItem('anonymousUsername');
  },

  /**
   * Get user ID
   */
  getUserId: () => {
    return localStorage.getItem('userId');
  },

  /**
   * Get user type (GUEST, REGULAR, ADMIN)
   */
  getUserType: () => {
    return localStorage.getItem('userType');
  },

  /**
   * Check if user is authenticated
   */
  isAuthenticated: () => {
    return !!localStorage.getItem('token');
  },

  /**
   * Logout - clear all authentication data
   */
  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('userId');
    localStorage.removeItem('anonymousUsername');
    localStorage.removeItem('userType');
  },

  /**
   * Make authenticated API call
   * Automatically includes Authorization header with JWT token
   * 
   * @param {string} endpoint - API endpoint (e.g., '/posts', '/chat/messages')
   * @param {object} options - Fetch options (method, body, etc.)
   * @returns {Promise<Response>} - Fetch response
   * 
   * Example usage:
   * const response = await auth.fetchWithAuth('/posts', {
   *   method: 'POST',
   *   body: JSON.stringify({ title: 'My Post', content: 'Hello!' })
   * });
   */
  fetchWithAuth: async (endpoint, options = {}) => {
    const token = auth.getToken();

    const headers = {
      'Content-Type': 'application/json',
      ...options.headers,
    };

    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    let response = await fetch(`${API_URL}${endpoint}`, {
      ...options,
      headers,
    });

    // If token expired (401), try to refresh
    if (response.status === 401) {
      console.log('🔄 Token expired, attempting to refresh...');
      const refreshed = await auth.refreshToken();
      
      if (refreshed) {
        // Retry the request with new token
        headers['Authorization'] = `Bearer ${auth.getToken()}`;
        response = await fetch(`${API_URL}${endpoint}`, {
          ...options,
          headers,
        });
      } else {
        // Refresh failed, logout user
        console.log('❌ Token refresh failed, logging out');
        auth.logout();
        globalThis.location.href = '/';
      }
    }

    return response;
  },

  /**
   * Refresh access token using refresh token
   * @returns {Promise<boolean>} - True if successful, false otherwise
   */
  refreshToken: async () => {
    const refreshToken = auth.getRefreshToken();
    
    if (!refreshToken) {
      return false;
    }

    try {
      const response = await fetch(`${API_URL}/auth/refresh`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ refreshToken }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem('token', data.token);
        console.log('✅ Token refreshed successfully');
        return true;
      }
      
      return false;
    } catch (error) {
      console.error('❌ Token refresh error:', error);
      return false;
    }
  },

  /**
   * Example: Fetch posts from the backend (when implemented)
   */
  getPosts: async () => {
    const response = await auth.fetchWithAuth('/posts');
    if (response.ok) {
      return await response.json();
    }
    throw new Error('Failed to fetch posts');
  },

  /**
   * Example: Create a new post (when implemented)
   */
  createPost: async (postData) => {
    const response = await auth.fetchWithAuth('/posts', {
      method: 'POST',
      body: JSON.stringify(postData),
    });
    if (response.ok) {
      return await response.json();
    }
    throw new Error('Failed to create post');
  },
};

export default auth;
