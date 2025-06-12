"use client";
import { useState } from "react";
import Link from "next/link";
import { faGithub, faGoogle } from "@fortawesome/free-brands-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useRouter } from "next/navigation";
import axios from "axios";

const SignupPage = () => {

  const router = useRouter();

  const [formState, setFormState] = useState({
    username:"",
    email:"",
    password:""
  }); 

  const handleChange = (e) => {
    const copy = { ...formState };
    copy[e.target.name] = e.target.value
    setFormState(copy);
  };


  const handleSignup = async  (e) => {
    e.preventDefault();

    const response = await fetch('http://localhost:8081/api/register' , {
      method:"POST",
      body: JSON.stringify(formState),
      headers: {
        "Content-Type":"application/json",
        "Access-Control-Allow-Credentials":"true"
      },
      credentials:'include'
    })

    const body = await response.text();

    if(response.ok){
      alert(body)
      router.push("/Login")
    }else{
      alert(body)
    }

  };

  return (
    <section className="min-h-screen flex items-center justify-center bg-black/30">
    <div className="p-6 bg-black/80 text-white shadow-lg rounded-xl border border-blue-400/30 w-full max-w-sm">
      <h2 className="text-2xl font-bold mb-4 text-center">Sign Up</h2>
      <form onSubmit={handleSignup} className="space-y-4">
        <div>
          <label className="block mb-1 text-sm">Username</label>
          <input 
            name="username"
            type="text"
            value={formState.username}
            onChange={handleChange}
            className="w-full p-2.5 bg-black/60 border border-blue-400/30 rounded-md text-sm"
            placeholder="Username"
            required
          />
        </div>
        <div>
          <label className="block mb-1 text-sm">Email Address</label>
          <input
            name="email"
            type="email"
            value={formState.email}
            onChange={handleChange}
            className="w-full p-2.5 bg-black/60 border border-blue-400/30 rounded-md text-sm"
            placeholder="Email"
            required
          />
        </div>
        <div>
          <label className="block mb-1 text-sm">Password</label>
          <input
            name="password"
            type="password"
            value={formState.password}
            onChange={handleChange}
            className="w-full p-2.5 bg-black/60 border border-blue-400/30 rounded-md text-sm"
            placeholder="Password"
            required
          />
        </div>
        <button
          type="submit"
          className="w-full bg-blue-500 text-black p-2.5 rounded-full font-semibold text-sm hover:bg-blue-400 transition-all duration-300"
        >
          Sign Up
        </button>
      </form>
      <p className="mt-3 text-center text-sm">or continue with</p>
      <div className="flex flex-col mt-3 gap-2">
        <button className="bg-white text-black px-3 py-2 rounded-full text-sm font-semibold hover:bg-gray-300">
          <FontAwesomeIcon icon={faGoogle} className="mr-2" /> Google
        </button>
        <button className="bg-white text-black px-3 py-2 rounded-full text-sm font-semibold hover:bg-gray-300">
          <FontAwesomeIcon icon={faGithub} className="mr-2" /> Github
        </button>
      </div>
      <p className="mt-4 text-center text-sm">
        Already have an account?{" "}
        <Link href="/Login" className="text-blue-400 hover:underline">
          Log In
        </Link>
      </p>
    </div>
  </section>

  );
};

export default SignupPage;