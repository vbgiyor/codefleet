import React from 'react';
import { Link } from 'react-router-dom';

const BasicAuth = () => {
  return (
    <div className="container mx-auto py-8 bg-gray-100 min-h-full">
      {/* Back Link */}
      <div className="mb-4">
        <Link
          to="/case-studies/automation"
          className="text-blue-600 hover:text-blue-800 underline"
          id="back_to_automation_link"
        >
          ← Back to Automation
        </Link>
      </div>

      {/* Title */}
      <h2 className="text-2xl font-bold text-center mb-6" id="basic_auth_title">
        Basic Authentication
      </h2>

      {/* Success Message (shown if authenticated) */}
      <div
        className="bg-green-50 p-6 rounded-lg shadow-lg text-center"
        id="auth_success_message"
      >
        <p className="text-green-700 font-semibold">
          Congratulations! You have successfully authenticated using Basic Auth.
        </p>
        <p className="text-gray-700 mt-2">
          This page is protected by HTTP Basic Authentication. You provided valid
          credentials (e.g., username: <strong>admin</strong>, password:{' '}
          <strong>admin</strong>).
        </p>
      </div>

      {/* Instructions */}
      <div className="mt-8 text-center" id="basic_auth_instructions">
        <p className="text-gray-700">
          To access this page, use the credentials <strong>admin:admin</strong>.
          You can:
        </p>
        <ul className="list-disc list-inside text-gray-700 mt-2">
          <li>
            Enter the credentials in the browser's login prompt when visiting{' '}
            <code>/basicauth</code>.
          </li>
          <li>
            Access the page directly via a URL like:{' '}
            <code>http://admin:admin@localhost:3000/basicauth</code>.
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
          . Ensure your server is configured to validate Basic Auth credentials.
        </p>
      </div>
    </div>
  );
};

export default BasicAuth;