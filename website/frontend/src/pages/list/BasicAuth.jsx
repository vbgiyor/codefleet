import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const BasicAuth = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(null);

  useEffect(() => {
    // Call backend endpoint to check for valid auth
    fetch('/basic-auth-protected/', {
      headers: {
        Accept: 'application/json'
      },
      credentials: 'include'
    })
      .then((res) => {
        if (res.ok) {
          setIsAuthenticated(true);
        } else if (res.status === 401) {
          // Trigger browser Basic Auth prompt
          window.location.href = '/basic-auth-protected/';
        } else {
          setIsAuthenticated(false);
        }
      })
      .catch(() => setIsAuthenticated(false));
  }, []);

  if (isAuthenticated === null) {
    return (
      <div className="container mx-auto py-8 min-h-full">
        <br /> 
        <p className="text-gray-700 text-center">Demonstration of a Browser Basic Authentication pop-up, requiring `admin:admin` credentials.</p>
        <p className="text-gray-700 text-center">Sign in with provided credentials to proceed ahead.</p>
      </div>
    );
  }

  if (!isAuthenticated) {
    return (
      <div className="container mx-auto py-8 bg-gray-100 min-h-full">
        <p className="text-red-700 text-center">Please authenticate using Basic Auth (admin:admin).</p>
        <p className="text-gray-700 text-center mt-2">
          Try visiting <code>http://admin:admin@localhost:8000/basic-auth-protected/</code> directly.
        </p>
      </div>
    );
  }

  return (
    <div className="container mx-auto py-8 bg-gray-100 min-h-full">
      <div className="mb-4">
        <Link
          to="/case-studies/automation"
          className="text-blue-600 hover:text-blue-800 underline"
          id="back_to_automation_link"
        >
          ← Back to Automation
        </Link>
      </div>
      <h2 className="text-2xl font-bold text-center mb-6" id="basic_auth_title">
        Basic Authentication
      </h2>
      <div
        className="bg-green-50 p-6 rounded-lg shadow-lg text-center"
        id="auth_success_message"
      >
        <p className="text-green-700 font-semibold">
          Congratulations! You have successfully authenticated using Basic Auth.
        </p>
        <p className="text-gray-700 mt-2">
          This page is protected by HTTP Basic Authentication. You provided valid credentials
          (e.g., username: <strong>admin</strong>, password: <strong>admin</strong>).
        </p>
      </div>
      <div className="mt-8 text-center" id="basic_auth_instructions">
        <p className="text-gray-700">
          To access this page, use the credentials <strong>admin:admin</strong>. You can:
        </p>
        <ul className="list-disc list-inside text-gray-700 mt-2">
          <li>
            Enter the credentials in the browser's login prompt when visiting{' '}
            <code>/basic-auth-protected/</code>.
          </li>
          <li>
            Access the page directly via a URL like:{' '}
            <code>http://admin:admin@localhost:8000/basic-auth-protected/</code>.
          </li>
        </ul>
        <p className="text-gray-700 mt-4">
          Note: This is a testing example inspired by{' '}
          <a
            href="https://the-internet.herokuapp.com/basic_auth"
            className="text-blue-600 hover:text-blue-800 underline"
            id="heroku_basic_auth_link"
          >
            the-internet.herokuapp.com
          </a>
          .
        </p>
      </div>
    </div>
  );
};

export default BasicAuth;
