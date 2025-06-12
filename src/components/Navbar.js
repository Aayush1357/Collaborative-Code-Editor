"use client";
import React, { useState } from "react";
import { Menu, X } from "lucide-react";
import Link from "next/link";
import Image from "next/image";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleNavbar = () => {
    setIsOpen(!isOpen);
  };

  return (
    <header className="bg-black/30 backdrop-blur-lg mx-3 my-9 p-4 rounded-full shadow-lg shadow-blue-400/20 flex justify-between items-center border border-blue-400/30 transition-all duration-300 hover:shadow-blue-400/50 font-poppins relative">
      <div className="md:text-2xl text-base font-bold flex items-center space-x-2">
        <span className="hover:text-blue-400 transition-all duration-300">
          Logo
        </span>
      </div>

      <nav>
        <ul className="hidden md:flex space-x-6 text-lg">
          <li className="hover:text-blue-400 transition-all duration-300 cursor-pointer">
            <Link href="/">Home</Link>
          </li>
          <li className="hover:text-blue-400 transition-all duration-300 cursor-pointer">
            <Link href="/Collaboration">Collaboration</Link>
          </li>
        </ul>
      </nav>
      
      <div className="hidden md:flex gap-4">
        <Link href='/Login' className="bg-blue-500 text-black px-4 py-2 rounded-full font-semibold hover:bg-blue-400 transition-all duration-300">
          Log In
        </Link>
        <Link href='/Signup' className="bg-white text-black px-4 py-2 rounded-full font-semibold hover:bg-gray-300 transition-all duration-300">
          Sign Up
        </Link>
      </div>

      <button
        className="md:hidden p-2 text-blue-300 hover:text-blue-400 transition-all duration-300"
        onClick={toggleNavbar}
      >
        {isOpen ? <X size={28} /> : <Menu size={28} />}
      </button>

      {isOpen && (
        <div className="absolute top-full left-0 w-full bg-black/90 backdrop-blur-lg p-4 flex flex-col items-center space-y-4 border-t border-blue-400/30 shadow-lg md:hidden">
          <Link href="/" className="text-lg hover:text-blue-400 transition-all duration-300">Home</Link>
          <Link href="/Collaboration" className="text-lg hover:text-blue-400 transition-all duration-300">Collaboration</Link>
          <button className="bg-blue-500 text-black px-4 py-2 rounded-full font-semibold hover:bg-blue-400 transition-all duration-300 w-full">Log In</button>
          <button className="bg-white text-black px-4 py-2 rounded-full font-semibold hover:bg-gray-300 transition-all duration-300 w-full">Sign Up</button>
        </div>
      )}
    </header>
  );
};

export default Navbar;