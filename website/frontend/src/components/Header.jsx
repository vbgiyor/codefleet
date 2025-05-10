import React, { useState, useEffect } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isAuthDropdownOpen, setIsAuthDropdownOpen] = useState(false);
  const [firstName, setFirstName] = useState(localStorage.getItem('first_name') || null);
  const logo = '/static/images/logo.png';
  const navigate = useNavigate();

  useEffect(() => {
    const updateFirstName = () => {
      const storedFirstName = localStorage.getItem('first_name');
      setFirstName(storedFirstName || null);
    };

    // Initial check
    updateFirstName();

    // Listen for storage changes (e.g., login/logout)
    window.addEventListener('storage', updateFirstName);

    // Fetch profile if token exists but no first name
    const fetchUserProfile = async () => {
      const accessToken = localStorage.getItem('access_token');
      if (accessToken && !firstName) {
        try {
          const response = await axios.get('http://localhost:8000/api/user/', {
            headers: {
              Authorization: `Bearer ${accessToken}`
            }
          });
          setFirstName(response.data.first_name);
          localStorage.setItem('first_name', response.data.first_name);
        } catch (err) {
          console.error('Failed to fetch user profile:', err);
          if (err.response?.status === 401) {
            localStorage.removeItem('access_token');
            localStorage.removeItem('refresh_token');
            localStorage.removeItem('first_name');
            setFirstName(null);
          }
        }
      }
    };
    fetchUserProfile();

    return () => window.removeEventListener('storage', updateFirstName);
  }, [firstName]);

  const handleSignOut = () => {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('first_name');
    setFirstName(null);
    setIsAuthDropdownOpen(false);
    navigate('/');
  };

  return (
    <header className="navbar" id="header">
      <div className="container mx-auto flex flex-col sm:flex-row justify-between items-center">
        <div className="flex items-center justify-between w-full sm:w-auto">
          <div className="flex items-center">
            <img
              src={logo}
              alt="Codefleet Logo"
              className="h-28 w-auto"
              id="logo"
              loading="lazy"
              onError={() => console.error('Logo failed to load')}
            />
            <h1 className="text-2xl font-bold ml-2" id="company_name">Codefleet</h1>
          </div>
          <button
            onClick={() => setIsMenuOpen(!isMenuOpen)}
            className="sm:hidden focus:outline-none"
            id="menu_btn"
          >
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M4 6h16M4 12h16m-7 6h7"
              />
            </svg>
          </button>
        </div>
        <nav className={`sm:flex ${isMenuOpen ? 'block' : 'hidden'} w-full sm:w-auto mt-4 sm:mt-0`}>
          <div className="flex flex-col sm:flex-row items-center space-y-2 sm:space-y-0 sm:space-x-4">
            <NavLink
              to="/"
              className={({ isActive }) =>
                `w-24 text-center h-10 flex items-center justify-center ${isActive ? 'text-white' : 'hover:text-bluish-black'}`
              }
              id="menu_home"
            >
              Home
            </NavLink>
            <NavLink
              to="/java"
              className={({ isActive }) =>
                `w-24 text-center h-10 flex items-center justify-center ${isActive ? 'text-white' : 'hover:text-bluish-black'}`
              }
              id="menu_java"
            >
              Java
            </NavLink>
            <NavLink
              to="/python"
              className={({ isActive }) =>
                `w-24 text-center h-10 flex items-center justify-center ${isActive ? 'text-white' : 'hover:text-bluish-black'}`
              }
              id="menu_python"
            >
              Python
            </NavLink>
            <NavLink
              to="/cicd"
              className={({ isActive }) =>
                `w-24 text-center h-10 flex items-center justify-center ${isActive ? 'text-white' : 'hover:text-bluish-black'}`
              }
              id="menu_cicd"
            >
              CI/CD
            </NavLink>
            <NavLink
              to="/automation"
              className={({ isActive }) =>
                `w-24 text-center h-10 flex items-center justify-center ${isActive ? 'text-white' : 'hover:text-bluish-black'}`
              }
              id="menu_automation"
            >
              Automation
            </NavLink>
            <NavLink
              to="/contact"
              className={({ isActive }) =>
                `w-24 text-center h-10 flex items-center justify-center ${isActive ? 'text-white' : 'hover:text-bluish-black'}`
              }
              id="menu_contact"
            >
              Contact
            </NavLink>
            <div className="relative">
              <button
                onClick={() => setIsAuthDropdownOpen(!isAuthDropdownOpen)}
                className="btn w-28 text-center h-10 flex items-center justify-center"
                id="auth_dropdown_btn"
              >
                {firstName ? `${firstName}` : 'Account'}
              </button>
              {isAuthDropdownOpen && (
                <div className="absolute right-0 mt-2 w-48 bg-glass-bg text-primary-blue rounded shadow-lg sm:block" id="auth_dropdown">
                  {firstName ? (
                    <button
                      onClick={handleSignOut}
                      className="block px-4 py-2 hover:bg-white w-full text-left"
                      id="menu_signout"
                    >
                      Sign Out
                    </button>
                  ) : (
                    <>
                      <NavLink
                        to="/signin"
                        className="block px-4 py-2 hover:bg-white"
                        id="menu_signin"
                        onClick={() => setIsAuthDropdownOpen(false)}
                      >
                        Sign In
                      </NavLink>
                      <NavLink
                        to="/signup"
                        className="block px-4 py-2 hover:bg-white"
                        id="menu_signup"
                        onClick={() => setIsAuthDropdownOpen(false)}
                      >
                        Sign Up
                      </NavLink>
                    </>
                  )}
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