import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const logo = `${process.env.REACT_APP_API_URL}/static/images/logo.png`;

  return (
    <header className="bg-blue-600 text-white p-4 fixed w-full top-0 z-10" id="header">
      <div className="container mx-auto flex flex-col sm:flex-row justify-between items-center">
        <div className="flex items-center justify-between w-full sm:w-auto">
          <div className="flex items-center">
            <img src={logo} alt="Codefleet Logo" className="h-10" id="logo" loading="lazy" />
            <h1 className="text-2xl font-bold ml-2" id="company_name">Codefleet</h1>
          </div>
          <button onClick={() => setIsMenuOpen(!isMenuOpen)} className="sm:hidden focus:outline-none" id="menu_btn">
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16m-7 6h7"></path>
            </svg>
          </button>
        </div>
        <nav className={`sm:flex ${isMenuOpen ? 'block' : 'hidden'} w-full sm:w-auto mt-4 sm:mt-0`}>
          <div className="flex flex-col sm:flex-row items-center">
            <Link to="/signup">
              <button className="bg-white text-blue-600 px-4 py-2 rounded mb-2 sm:mb-0 sm:mr-2" id="signup_btn">Sign Up</button>
            </Link>
            <Link to="/login">
              <button className="bg-white text-blue-600 px-4 py-2 rounded mb-2 sm:mb-0 sm:mr-2" id="login_btn">Login</button>
            </Link>
            <div className="relative">
              <button onClick={() => setIsMenuOpen(!isMenuOpen)} className="hidden sm:block focus:outline-none" id="menu_btn">
                <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16m-7 6h7"></path>
                </svg>
              </button>
              {isMenuOpen && (
                <div className="absolute right-0 mt-2 w-48 bg-white text-black rounded shadow-lg sm:block" id="menu_dropdown">
                  <Link to="/java" className="block px-4 py-2 hover:bg-gray-200" id="menu_java">Java</Link>
                  <Link to="/python" className="block px-4 py-2 hover:bg-gray-200" id="menu_python">Python</Link>
                  <Link to="/cicd" className="block px-4 py-2 hover:bg-gray-200" id="menu_cicd">CICD</Link>
                  <Link to="/automation" className="block px-4 py-2 hover:bg-gray-200" id="menu_automation">Automation</Link>
                </div>
              )}
            </div>
          </div>
        </nav>
      </div>
    </header>
  );
};

export default Header;