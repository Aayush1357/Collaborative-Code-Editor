import {useState , useEffect} from "react";
import axios from 'axios';
import '../css/Dashboard.css';
import Repl from "./Repl";
import { useNavigate } from "react-router-dom"

const Dashboard = () => {

    const navigate = useNavigate()
    const [user , setUser] = useState(null);
    const [replName,setReplname] = useState("");
    const [language , setLanguage] = useState("java");

    const handleSubmit = (e) => {

        navigate("/Repl");

        const data = {
            username:user.name,
            replname:replName,
            Language:language
        };

        var api =  "http://localhost:8081/createRepl?username=${user.name}&projectname={replName}&language={language}";
        fetch("" , {
            method:'POST',
            headers:{
                'Content-Type':'application/json',
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => console.log('Repl created successfully' , data))
        .catch(error => console.error('Error creating the repl:' , error));
        
    }


    useEffect(() =>{
        axios.get("http://localhost:8080/user-info" , {withCredentials:true})
            .then(response => {
                setUser(response.data);
            })
    } , []);

   

    return(

        <div className="dashboard">
            <h1>Dashboard</h1>
            <h3> Welcome User</h3>

            {user ? (
            <div>
                <div className="userinfo">
                    <p> <b>Name : </b> {user.name}</p>
                    <p> <b>Email : </b> {user.email}</p>
                    <p> <b>User Profile Photos : </b> <b/>
                        {user.picture && <img src={user.picture}
                        alt="User"
                        referrerPolicy="no-referrer"></img>}
                    </p>
                </div>

                <div className="repldetail">

                    <label>Select a language to createRepl: </label>
                    <select name="language" id="language" value = {language} 
                            onChange={(e) => setLanguage(e.target.value)}>
                        <option value="java">Java</option>
                        <option value="python">Python</option>
                    </select>


                    <label>Title</label>
                    <input id="title" value = {replName} placeholder="Name your app" 
                            onChange={(e) => setReplname(e.target.value)}/>

                    <button onClick={(e) => handleSubmit(e)} id="replButton">Create</button>

                </div>
            </div>

            ) : (
                <h4> Loading user data ..... </h4>
            )}
        </div>
        
    );
}


export default Dashboard;