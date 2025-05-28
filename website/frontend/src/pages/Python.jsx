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
        <h4 className="custom-font-size font-bold text-left text-blue-600 my-8" id="drf_resource_title">
          Django Rest Framework (DRF) Resources
        </h4>
        <ul className="max-w-md space-y-1 text-gray-500 list-disc list-inside dark:text-gray-400">
          <li>
            <NavLink
              to="/documentation/django_orm_basics.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              🧩 Django ORM Basics
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/documentation/django-migrations-fundamentals.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              📘 Django Migrations Fundamentals
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/documentation/django-migrations-examples-guide.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              🗄️ Django Migrations Examples
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/documentation/variables.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              🐍 Python Variables
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/documentation/rendering_markdown_doc_as_html.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              🌐 Rendering Markdown as HTML
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/documentation/django_logging_configuration.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              📋 Django Logging Configuration
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/documentation/diff-between-django_repr_and_str.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              🔤 `__repr__` vs `__str__` Comparison
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/documentation/django-models-and-app-config-guide.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              🌟 Django Models and App Configuration Guide
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/documentation/django-simple-restful-api-guide.md"
              className="cursor-pointer text-dark-blue text-center hover:text-blue-700"
            >
              ⚙️ Building a Simple RESTful API with DRF
            </NavLink>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default Python;
