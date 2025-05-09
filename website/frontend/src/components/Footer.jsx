import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {
  const logo = `${process.env.REACT_APP_API_URL}/static/images/logo.png`;
  
  return (
    <footer className="bg-blue-600 text-white p-4" id="footer">
      <div className="container mx-auto flex justify-between items-center">
        <div>
          <img src={logo} alt="Codefleet Logo" className="h-8" id="footer_logo" loading="lazy" />
          <p id="copyright">© 2025 Codefleet. All rights reserved.</p>
        </div>
        <Link to="/contact" className="text-white hover:underline" id="contact_link">Contact Us</Link>
      </div>
    </footer>
  );
};

export default Footer;