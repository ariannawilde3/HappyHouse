import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

const API_URL = 'http://localhost:5000/api';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [googleLoaded, setGoogleLoaded] = useState(false);
  const navigate = useNavigate();

  // Load Google Identity Services
  useEffect(() => {
    const loadGoogleScript = () => {
      if (globalThis.google) {
        initializeGoogleSignIn();
        return;
      }

      const script = document.createElement('script');
      script.src = 'https://accounts.google.com/gsi/client';
      script.onload = initializeGoogleSignIn;
      script.onerror = () => {
        console.error('Failed to load Google Identity Services');
      };
      document.body.appendChild(script);
    };

    const initializeGoogleSignIn = () => {
      if (globalThis.google) {
        globalThis.google.accounts.id.initialize({
          client_id: "291819576173-dn0jkt0lj92e13ubfqjo8ham8qbh8v0u.apps.googleusercontent.com",
          callback: handleGoogleCredentialResponse,
        });
        setGoogleLoaded(true);
      }
    };

    loadGoogleScript();
  }, []);

  // Render Google button when loaded
  useEffect(() => {
    if (googleLoaded && globalThis.google) {
      globalThis.google.accounts.id.renderButton(
        document.getElementById("google-signin-button"),
        { 
          theme: "outline", 
          size: "large",
          width: "100%",
          text: "signin_with"
        }
      );
    }
  }, [googleLoaded]);

  const handleGoogleCredentialResponse = async (response) => {
    setError('');
    setLoading(true);

    try {
      // Send the raw id_token to backend and let server verify it
      console.log('GSI credential received (first 40 chars):', response.credential?.slice(0,40));
      const res = await fetch(`${API_URL}/auth/google`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ credential: response.credential })
      });

      // Always log status and body for debugging
      const dataText = await res.text();
      let data;
      try { data = JSON.parse(dataText); } catch(e) { data = { raw: dataText }; }
      console.log('POST /api/auth/google status=', res.status, 'body=', data);

      if (res.ok) {
        // Backend returns flat structure: { token, refreshToken, userId, anonymousUsername, userType }
        const accessToken = data.token;
        const refreshToken = data.refreshToken;

        if (!accessToken) {
          console.warn('No access token returned by backend', data);
          setError('Google sign-in failed (no token returned).');
        } else {
          // Store all authentication data - backend returns flat structure
          localStorage.setItem('token', accessToken);
          if (refreshToken) localStorage.setItem('refreshToken', refreshToken);
          if (data.userId) localStorage.setItem('userId', data.userId);
          if (data.anonymousUsername) localStorage.setItem('anonymousUsername', data.anonymousUsername);
          if (data.userType) localStorage.setItem('userType', data.userType);

          navigate('/neighborhood');
        }
      } else {
        // show backend error message if present
        setError(data.message || data.error || `Google sign-in failed (${res.status})`);
      }
    } catch (err) {
      console.error('Google sign-in network/error', err);
      setError('Google sign-in failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleSignIn = async () => {
    // Clear any previous errors
    setError('');
    
    // Validate inputs
    if (!email || !password) {
      setError('Please enter both email and password');
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json();

      if (response.ok) {
        // Store authentication data in localStorage
        localStorage.setItem('token', data.token);
        localStorage.setItem('refreshToken', data.refreshToken);
        localStorage.setItem('userId', data.userId);
        localStorage.setItem('anonymousUsername', data.anonymousUsername);
        localStorage.setItem('userType', data.userType);
        
        console.log('✅ Login successful!', data);
        console.log('Anonymous Username:', data.anonymousUsername);
        
        // Navigate to forum
        navigate('/neighborhood');
      } else {
        // Handle error response
        setError(data.message || 'Invalid email or password');
        console.error('❌ Login failed:', data);
      }
    } catch (error) {
      console.error('❌ Login error:', error);
      setError('Unable to connect to server. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleGuestSignIn = async () => {
    setError('');
    setLoading(true);

    try {
      const response = await fetch(`${API_URL}/auth/guest`, {
        method: 'POST',
      });

      const data = await response.json();

      if (response.ok) {
        // Store authentication data in localStorage
        localStorage.setItem('token', data.token);
        localStorage.setItem('refreshToken', data.refreshToken);
        localStorage.setItem('userId', data.userId);
        localStorage.setItem('anonymousUsername', data.anonymousUsername);
        localStorage.setItem('userType', data.userType);
        
        console.log('✅ Guest login successful!', data);
        console.log('Anonymous Username:', data.anonymousUsername);
        
        // Navigate to forum
        navigate('/neighborhood');
      } else {
        setError('Guest registration failed. Please try again.');
        console.error('❌ Guest login failed:', data);
      }
    } catch (error) {
      console.error('❌ Guest login error:', error);
      setError('Unable to connect to server. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const goToSignup = () => {
    navigate('/signup');
  };

  // Allow Enter key to submit
  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSignIn();
    }
  };

  return (
    <div className="login-container">
      <div className="phone-frame">
        <div className="status-bar">
          <span>9:41</span>
          <span>📶 📡 🔋</span>
        </div>
        
        <div className="content-area">
          <div className="logo-container">
            <div className="logo-circle">
              <span className="logo-text">HH</span>
            </div>
          </div>

          <div className="welcome-section">
            <p className="welcome-subtitle">Welcome To</p>
            <h1 className="welcome-title">HappyHouse</h1>
          </div>

          <div className="form-card">
            {/* Error Message */}
            {error && (
              <div style={{
                backgroundColor: '#fee2e2',
                border: '1px solid #ef4444',
                color: '#991b1b',
                padding: '0.75rem',
                borderRadius: '6px',
                marginBottom: '1rem',
                fontSize: '0.875rem'
              }}>
                {error}
              </div>
            )}

            <div className="input-group">
              <label className="input-label">Email</label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                onKeyPress={handleKeyPress}
                placeholder="Enter your email"
                className="input-field"
                disabled={loading}
              />
            </div>

            <div className="input-group-password">
              <label className="input-label">Password</label>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                onKeyPress={handleKeyPress}
                placeholder="Enter your password"
                className="input-field"
                disabled={loading}
              />
            </div>

            <button 
              onClick={handleSignIn} 
              className="btn btn-primary"
              disabled={loading}
            >
              {loading ? 'Signing In...' : 'Sign In'}
            </button>

            <div className="divider">or</div>

            <div className="google-signin-container">
              <div id="google-signin-button"></div>
              {!googleLoaded && (
                <div style={{ 
                  padding: '12px', 
                  textAlign: 'center', 
                  backgroundColor: '#f3f4f6',
                  borderRadius: '8px',
                  color: '#6b7280',
                  marginBottom: '1rem'
                }}>
                  Loading Google Sign-In...
                </div>
              )}
            </div>

            <button 
              onClick={handleGuestSignIn} 
              className="btn btn-primary"
              disabled={loading}
            >
              {loading ? 'Creating Guest...' : 'Register as Guest'}
            </button>

            <div className="forgot-password-container">
              <span style={{ color: '#6b7280', fontSize: '0.875rem' }}>
                Don't have an account?{' '}
              </span>
              <a 
                onClick={goToSignup} 
                className="forgot-password-link"
                style={{ cursor: 'pointer' }}
              >
                Create Account
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
