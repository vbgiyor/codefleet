import React from 'react';
import { NavLink } from 'react-router-dom';
import '../styles.css';

const Python = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <div className="container mx-auto mb-8">
        <h2 className="text-3xl font-bold text-center my-8" id="python_title">
          Python Content Library
        </h2>
        <p className="text-1xl text-center" id="python_description">
          This page will contain details about a Python Knowledgebase. Here are some resources to help you get started with Python and DRF.
        </p>
      </div>

      <div className="container mx-auto mt-8">
        <ul className="max-w-md space-y-1 text-gray-500 list-disc list-inside dark:text-gray-400">
          <li>
            <NavLink
              to="/documentation/django_orm_basics.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              📄 Django ORM Basics
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/documentation/variables.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              📄 Python Variables
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/documentation/rendering_markdown_doc_as_html.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              📄 Rendering Markdown as HTML
            </NavLink>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default Python;
