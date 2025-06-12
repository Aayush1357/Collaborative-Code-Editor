"use client"
import Link from "next/link";
import {  useState } from "react";
import { v4 as uuidv4 } from 'uuid';
import { useRouter } from "next/navigation";


const Collaboration = () => {
  const [showRoomInput, setShowRoomInput] = useState(false);
  const [selectedLanguage, setSelectedLanguage] = useState('java');
  const [roomId , setRoomId] = useState("");
  const [projectName , setProjectName] = useState(null);

  const router = useRouter();

  const enterRoomId = () => {
    setShowRoomInput(true);
  };


  const createUUid = () => {
    const roomid = uuidv4();
    localStorage.setItem('roomid', roomid);

    if (projectName == null) {
      alert("Enter a project name");
      return;
    }
    localStorage.setItem('language', selectedLanguage);
    localStorage.setItem('projectName' , projectName);
      
    router.push("/Repl");
  }

  return (
    <section className="text-white bg-black/30 backdrop-blur-lg mx-3 my-9 p-12 rounded-xl shadow-lg shadow-blue-400/20 border border-blue-400/30 flex flex-col items-center justify-center space-y-6 h-[90vh]">
      <h1 className="text-4xl md:text-6xl font-bold text-center">Create or Join a Project</h1>
      <p className="text-lg text-gray-300 max-w-xl text-center">
        Start a collaborative coding session with your team using Java, JavaScript, or Python.
      </p>

      <div className="flex flex-col items-center space-y-6">
        <input className="bg-white px-4 text-black py-2 rounded-full focus:outline-none w-64" 
        placeholder="Enter Project Name" 
        onChange={(e) => setProjectName(e.target.value)}/>

        <select 
          value={selectedLanguage} 
          onChange={(e) => setSelectedLanguage(e.target.value)} 
          className="bg-white text-black px-4 py-2 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-400 w-64"
        >
          <option value="java">Java 17</option>
          <option value="javascript">javaScript</option>
          <option value="python3">Python3</option>
        </select>

        <div className="flex gap-5">
          <button onClick={createUUid} className="bg-blue-500 text-black px-6 py-3 rounded-full font-semibold hover:bg-blue-400 transition-all duration-300">
            Create Project
          </button>

          <button onClick={enterRoomId} className="bg-amber-600 px-6 py-3 rounded-full font-semibold hover:bg-amber-500 transition-all duration-300">
            Join Room
          </button>
        </div>

        {showRoomInput && (
          <div className="mt-6 flex flex-col items-center space-y-4">
            <input
              type="text"
              placeholder="Enter Room ID"
              onChange={(e) => setRoomId(e.target.value)}
              className="bg-white text-black px-4 py-2 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-400 w-64"
            />
            <Link href={{
              pathname:"/Invite",
              query:{
                roomid:roomId,
              }
            }}>
              <button className="bg-blue-500 text-black px-6 py-3 rounded-full font-semibold hover:bg-blue-400 transition-all duration-300">
                Join
              </button>
            </Link>
            
          </div>
        )}
      </div>
    </section>
  );
};

export default Collaboration;