import { useNavigate } from "react-router-dom";
import { useState, useEffect, useRef } from "react";
import axios from "axios";
import "./Chat.css";
import NavBar from "./NavBar.jsx";
import { supabase } from "../../utils/supabaseClient.js";
import { usePinnedMessages } from '../sharedStoragePinnedMessages';

const API_URL = "http://localhost:5000/api";

export default function Chat() {
  const { pinMessage, pinnedMessages } = usePinnedMessages();
  const [time, setTime] = useState(new Date());
  const navigate = useNavigate();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [loading, setLoading] = useState(true);
  const [chatId, setChatId] = useState(null);
  // const [houseName, setHouseName] = useState("HouseName");
  const [houseName, setHouseName] = useState();
  const chatContainerRef = useRef(null);

  const anonymousUsername = localStorage.getItem("anonymousUsername");
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");
  
  useEffect(() => {
      const fetchHouse = async () => {
          const token = localStorage.getItem("token");
          const res = await fetch(`${API_URL}/gcc/me`, {
              headers: { Authorization: `Bearer ${token}` },
          });
          const data = await res.json();
          setHouseName(data.houseName);
      };
      fetchHouse();
  }, []);

  // Fetch user's group chat
  useEffect(() => {
    const fetchUserChat = async () => {
      if (!token) {
        navigate("/login");
        return;
      }

      try {
        const response = await axios.get(`${API_URL}/gcc/me`, {
          headers: { Authorization: `Bearer ${token}` },
        });

        const chatData = response.data;

        if (!chatData.unlocked) {
          navigate("/PGCMake", { state: { invitecode: chatData.inviteCode } });
          return;
        }

        setChatId(chatData.id);
        //setHouseName(chatData.housename || "HouseName");
        //setHouseName(data.houseName);
        localStorage.setItem("chatId", chatData._id);
        localStorage.setItem("inviteCode", chatData.inviteCode.toString());

        setLoading(false);
      } catch (error) {
        if (error.response?.status === 404) {
          navigate("/PGCMake");
        } else if (error.response?.status === 401) {
          navigate("/login");
        } else {
          alert("Could not load chat.");
        }
      }
    };

    fetchUserChat();
  }, [token, navigate]);

  // Clock
  useEffect(() => {
    const timer = setInterval(() => setTime(new Date()), 60000);
    return () => clearInterval(timer);
  }, []);

  // Supabase real-time setup
  useEffect(() => {
    if (!chatId) return;

    const channelName = `chat_${chatId}`;
    const channel = supabase.channel(channelName);

    channel.on("broadcast", { event: "message" }, (payload) => {
      if (payload.payload.sender_anonymous !== anonymousUsername) {
        setMessages((prev) => [...prev, payload.payload]);
      }
    });

    channel.subscribe();

    return () => {
      channel.unsubscribe();
    };
  }, [chatId, anonymousUsername]);

  // Send message
  const sendMessage = async (e) => {
    e.preventDefault();

    if (!chatId || !newMessage.trim() || !anonymousUsername) {
      return;
    }

    const messagePayload = {
      content: newMessage.trim(),
      sender_anonymous: anonymousUsername,
      created_at: new Date().toISOString(),
      chat_id: chatId,
    };

    const { data, error } = await supabase
      .from("messages")
      .insert(messagePayload)
      .select();

    if (error) {
      console.error("Send error:", error);
      alert("Failed to send message");
      return;
    }

    setMessages((prev) => [...prev, data[0]]);

    const channelName = `chat_${chatId}`;
    await supabase.channel(channelName).send({
      type: "broadcast",
      event: "message",
      payload: data[0],
    });

    setNewMessage("");
  };

  // Fetch existing messages
  useEffect(() => {
    if (!chatId) return;

    const fetchMessages = async () => {
      setLoading(true);
      
      const { data, error } = await supabase
        .from('messages')
        .select('*')
        .eq('chat_id', chatId)
        .order('created_at', { ascending: true });

      if (error) {
        console.error("Error fetching messages:", error);
      } else {
        setMessages(data || []);
      }
      
      setLoading(false);
    };

    fetchMessages();
  }, [chatId]);

  // Auto-scroll
  useEffect(() => {
    setTimeout(() => {
      if (chatContainerRef.current) {
        chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
      }
    }, 100);
  }, [messages]);

  // Pin message handler
  const handlePinMessage = (message) => {
    pinMessage(message);
  };

  const isAlreadyPinned = (messageId) => {
    return pinnedMessages.some(pinnedMsg => 
      String(pinnedMsg.messageId) === String(messageId)
    );
  };

  const goToForum = () => navigate("/neighborhood");
  const goToProfile = () => navigate("/profile");
  const goToPins = () => navigate("/pins");
  const goToPolls = () => navigate("/polls");

  return (
    <div className="chat-outer-container">
      <div className="chat-phone-frame">
        <div className="chat-status-bar">
          <span>
            {time.toLocaleTimeString([], {
              hour: "2-digit",
              minute: "2-digit",
            })}
          </span>
          <span>📶 📡 🔋</span>
        </div>

        <div className="chat-content-area" ref={chatContainerRef}>
          <div className="chat-header-sticky">
            <div className="chat-welcome-section">
              <p className="chat-welcome-subtitle">Your</p>
              <h1 className="chat-welcome-title">{houseName}</h1>
            </div>
            
            <div className="chat-btn-bar">
              <button 
                className="chat-bar-btn active-chat-bar-btn"
                style={{ padding: '5px 8px' }}
              >
                Messages
              </button>
              <button onClick={goToPins} className="chat-bar-btn" style={{ padding: '5px 8px' }}>
                Pinned
              </button>
              <button onClick={goToPolls} className="chat-bar-btn" style={{ padding: '5px 8px' }}>
                Polls
              </button>
            </div>
          </div>

          <div className="chat-messages-container">
            {loading ? (
              <p>Loading messages...</p>
            ) : messages.length === 0 ? (
              <p>No messages yet. Be the first to say hello!</p>
            ) : (
              messages.map((msg, i) => {
                const isPinned = isAlreadyPinned(msg.id);
                const isYours = msg.sender_anonymous === anonymousUsername;

                return (
                  <div
                    key={msg.id || i}
                    className={isYours ? "your-message" : "message"}
                  >
                    <div className="message-header">
                      <b>{msg.sender_anonymous}</b>
                      <button
                        className="pin-button"
                        onClick={() => handlePinMessage({
                          id: msg.id,
                          username: msg.sender_anonymous,
                          content: msg.content,
                          timestamp: msg.created_at,
                          isYours: isYours
                        })}
                        title={isPinned ? "Already pinned" : "Pin this message"}
                        disabled={isPinned}
                      >
                        📌
                      </button>
                    </div>
                    <div className="message-content">{msg.content}</div>
                  </div>
                );
              })
            )}
          </div>
        </div>

        <div className="chat-input-container">
          <input
            type="text"
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
            onKeyPress={(e) => e.key === "Enter" && sendMessage(e)}
            placeholder="Type a message..."
            className="chat-input"
          />
          <button onClick={sendMessage} className="send-button">
            Send
          </button>
        </div>

        <div className="chat-nav-bar">
          <NavBar tab="chat" />
        </div>
      </div>
    </div>
  );
}