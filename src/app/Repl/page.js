"use client"

import '../../css/Repl.css';
import { useEffect, useState, useRef, version } from "react";
import SockJS from 'sockjs-client';
import { Stomp } from "@stomp/stompjs";
import { Editor } from "@monaco-editor/react";

const Repl = () => {
  const roomId= localStorage.getItem('roomid');
  const [showRoomId, setShowRoomId] = useState(false);
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const [code, setCode] = useState("");
  const messagesEndRef = useRef(null);
  const [output , setOutput] = useState("");
  const isRendered = useRef(false)
  const language = localStorage.getItem('language');
  const projectName = localStorage.getItem('projectName');

  useEffect(() => {

    if(isRendered.current) return;
    isRendered.current = true;

    if (!roomId) {
      alert("Please enter a room ID");
      return;
    }

    const socket = new SockJS("http://localhost:8080/collab");
    const client = Stomp.over(socket);
      
    client.connect({}, () => {
      console.log("Connected to STOMP");
      setStompClient(client);
      
      client.subscribe(`/topic/room/${roomId}`, (message) => {
        const data = JSON.parse(message.body);
        if (data.event === "codeUpdate") {
          setCode(data.code);
        } else if (data.event === "userJoined" || data.event === "userLeft") {
          setMessages((prev) => [...prev, data.message]);
        }
      });
      
      client.send(`/app/joinRoom/${roomId}`, {}, JSON.stringify({ username }));
      }, (error) => {
        console.error("STOMP error:", error);
        setMessages((prev) => [...prev, "Connection failed. Please try again."]);
      });
  }, []);

  const compileCode = async () => {

    const response = await fetch("http://localhost:8080/api/compile", {
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify({
        language:language,
        version:"3",
        code:code
      }),
    });

    const result = await response.json();
    setOutput(result.output || "Error executing code");
  };

  const handleCodeChange = (newValue) => {
    setCode(newValue);
    console.log(newValue);
    if (stompClient && stompClient.connected) {
      stompClient.send(`/app/codeUpdate/${roomId}`, {}, JSON.stringify({ code: newValue }));
    }
  };


  const handleInviteFriends = () => {
    const storedRoomId = localStorage.getItem('roomid');
    if (storedRoomId) {
      setShowRoomId(true);
    } else {
      alert('No room ID found. Please create a room first.');
    }
  };

  const handleCopy = () => {
    navigator.clipboard.writeText(roomId);
    alert('Room ID copied to clipboard!');
  };

  return (
    <div className="editor bg-black/30 text-white backdrop-blur-lg rounded-xl p-8 mx-3 my-9 shadow-lg shadow-blue-400/20 border border-blue-400/30">
      <div className="editor_header flex justify-between mb-4">
        <button onClick={handleInviteFriends} className="bg-blue-500 text-black px-6 py-3 rounded-full font-semibold hover:bg-blue-400 transition-all duration-300">
          Invite Friends
        </button>
        <button onClick={compileCode} className="bg-white text-black px-6 py-3 rounded-full font-semibold hover:bg-blue-400 transition-all duration-300">
          Run code
        </button>
      </div>

      {showRoomId && (
        <div className="room-info bg-white/10 p-4 rounded-xl mb-4 flex items-center justify-between">
          <p className="text-white">Room ID: <span className="font-bold text-blue-400">{roomId}</span></p>
          <button onClick={handleCopy} className="bg-gray-300 text-black px-6 py-2 rounded-full font-semibold hover:bg-gray-400 transition-all duration-300">
            Copy Room ID
          </button>
        </div>
      )}

      <div className="editor_body flex">
        {/* <div className='editor_folder_structure bg-blue-400/30 border border-blue-400/50 rounded-lg p-4 shadow-md mr-4'>
          <p className="text-white">Folder</p>
        </div> */}

        <div className='editor_code_window flex-1'>
          <Editor
            theme='vs-dark'
            height='70vh'
            width='100%'
            value={code}
            language={language}
            onChange={handleCodeChange}
          />

        <h2>Output:</h2>
          <div className=" bg-gray-700 p-8 h-30 border rounded-xl">
            <pre>{output}</pre>
          </div>
        </div>

        
      </div>

      <div className="editor_footer mt-4 text-gray-400">Here names of the people joining the room will be showed</div>
    </div>
  );
}

export default Repl;
