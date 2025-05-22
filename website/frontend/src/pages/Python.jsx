import React from 'react';
import { NavLink } from 'react-router-dom';
import '../styles.css';

const Python = () => {
  return (
    <div className="container mx-auto">
      <h2 className="text-3xl font-bold text-center my-8" id="python_title">Python Content Library</h2>
      <p className="text-1xl text-left" id="python_description">This page will contain details about a Python Knowledgebase.</p>
      
      <ul className="max-w-md space-y-1 text-gray-500 list-disc list-inside dark:text-gray-400">
        <li>
          <NavLink
            to="/documentation/drfsessions.md"
            className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
          >
            📄 Django Rest Framework Fundamentals
          </NavLink>
        </li>
        <li>
          <NavLink
            to="/documentation/rendering_markdown_documentation_as_html.md"
            className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
          >
            📄 Rendering Markdown file as HTML
          </NavLink>
        </li>
      </ul>
    </div>
  );
};

export default Python;