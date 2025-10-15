import React, { useState } from 'react';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSignIn = () => {
    console.log('Sign in clicked', { email, password });
    // Will connect to backend later
  };

  const handleGoogleSignIn = () => {
    console.log('Google sign in clicked');
    // Will connect to backend later
  };

  const handleGuestSignIn = () => {
    console.log('Guest sign in clicked');
    // Will connect to backend later
  };

  return (
    <div className="min-h-screen w-full bg-[#f5f5f0] flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        <div className="flex justify-center mb-8">
          <div className="w-20 h-20 rounded-full bg-gradient-to-br from-slate-700 to-slate-900 flex items-center justify-center shadow-lg">
            <span className="text-white text-2xl font-bold">HH</span>
          </div>
        </div>

        <div className="text-center mb-8">
          <p className="text-gray-600 text-lg mb-1">welcome to</p>
          <h1 className="text-4xl font-semibold text-[#7a9b7e]">HappyHouse</h1>
        </div>

        <div className="bg-white rounded-lg shadow-md p-8">
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-medium mb-2">
              Email
            </label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#7a9b7e] focus:border-transparent"
            />
          </div>

          <div className="mb-6">
            <label className="block text-gray-700 text-sm font-medium mb-2">
              Password
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Enter your password"
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#7a9b7e] focus:border-transparent"
            />
          </div>

          <button
            onClick={handleSignIn}
            className="w-full bg-[#7a9b7e] hover:bg-[#6a8b6e] text-white font-medium py-3 rounded-md transition duration-200 mb-3"
          >
            Sign In
          </button>

          <div className="text-center text-gray-500 text-sm my-4">or</div>

          <button
            onClick={handleGoogleSignIn}
            className="w-full bg-[#7a9b7e] hover:bg-[#6a8b6e] text-white font-medium py-3 rounded-md transition duration-200 mb-3"
          >
            Continue with Google
          </button>

          <button
            onClick={handleGuestSignIn}
            className="w-full bg-[#7a9b7e] hover:bg-[#6a8b6e] text-white font-medium py-3 rounded-md transition duration-200"
          >
            Register as Guest
          </button>

          <div className="text-center mt-6">
            <a
              href="#"
              className="text-[#7a9b7e] hover:text-[#6a8b6e] text-sm underline"
            >
              Forgot password?
            </a>
          </div>
        </div>
      </div>
    </div>
  );
}