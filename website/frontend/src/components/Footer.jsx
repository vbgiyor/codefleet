import React from 'react';
import { NavLink } from 'react-router-dom';

const Footer = () => {
  const logo = `${process.env.REACT_APP_API_URL}/static/images/logo.png`;

  return (
    <footer className="footer" id="footer">
      <div className="container mx-auto flex flex-col sm:flex-row justify-between items-center">
        <div className="flex items-center mb-2 sm:mb-0">
          <img src={logo} alt="Codefleet Logo" className="h-28 w-auto" id="footer_logo" loading="lazy" />
          <p className="ml-2" id="copyright">© 2025 Codefleet. All rights reserved.</p>
        </div>
        <NavLink
          to="/contact"
          className={({ isActive }) =>
            isActive ? 'text-white' : 'hover:text-white'
          }
          id="contact_link"
        >
          Contact Us
        </NavLink>
      </div>
    </footer>
  );
};

export default Footer;