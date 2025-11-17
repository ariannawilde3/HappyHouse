import { Component } from 'react';
import './NavBar.css'
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

export default function NavBar({ tab }) {
	const navigate = useNavigate();

	const goToChat = async () => {
        // guests can’t join/open house
        const userType = localStorage.getItem('userType');
        if (userType === 'GUEST') {
            alert('Guests cannot access private chats. Please sign up!');
            return;
        }

        const token = localStorage.getItem('token');
        const res = await fetch(`${API_URL}/gcc/me`, {
            headers: {
                Accept: 'application/json',
                Authorization: `Bearer ${token}`,
            },
        });

        // 404 = user has no GC yet
        if (res.status === 404) {
            navigate('/makeGC');           
            return;
        }

        // user has a GC
        const gc = await res.json();     
        if (gc.unlocked) {
            navigate('/house');            
        } else {
            navigate('/gcJoinedWaiting', { state: { invitecode: gc.inviteCode } });
        }
    };
	
	const goToProfile = () => {
        navigate('/profile');
        console.log('Settings icon clicked');
    };
	
	const goToForum = () => {
        navigate('/neighborhood');
        console.log('forum clicked');
    };
	
    return (
		<div className="nav-bar">
			<button onClick={goToChat} className={tab=="chat" ? "nav-btn active-btn" : "nav-btn inactive-btn"}>
                        <img src={house} alt="House Chat" style={{ width: '50px', height: '50px'}}/>
                    </button>
                
                    <button onClick={goToForum} className={tab=="forum" ? "nav-btn active-btn" : "nav-btn inactive-btn"}>
                        <img src={neighborhood} alt="Forum" style={{ width: '115px', height: '50px' }}/>
                    </button>
                
                    <button onClick={goToProfile} className={tab=="settings" ? "nav-btn active-btn" : "nav-btn inactive-btn"}>
                        <img src={settings} alt="Settings" style={{ width: '50px', height: '50px' }}/>
                    </button>
        </div>
	);
}