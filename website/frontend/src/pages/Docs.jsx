import React from "react";
import { Link, NavLink } from "react-router-dom";
import "../styles.css";

// Define resources dynamically
const markdownResources = [
  { name: "Rendering Markdown as HTML", path: "/documentation/rendering_markdown_doc_as_html.md" }
];

const pythonResources = [
  { name: "Data Types and Type Conversions", path: "/documentation/data_types_type_conversion.md" },
  { name: "Variables", path: "/documentation/variables.md" },
  { name: "__repr__ vs __str__ Comparison", path: "/documentation/diff-between-django_repr_and_str.md" }
];

const djangoResources = [
  { name: "Django ORM Basics", path: "/documentation/django_orm_basics.md" },
  { name: "Django Migrations Fundamentals", path: "/documentation/django-migrations-fundamentals.md" },
  { name: "Django Migrations Examples", path: "/documentation/django-migrations-examples-guide.md" },
  { name: "Django Logging Configuration", path: "/documentation/django_logging_configuration.md" },
  { name: "Django Models and App Configuration Guide", path: "/documentation/django-models-and-app-config-guide.md" },
  { name: "Building a Simple RESTful API with DRF", path: "/documentation/django-simple-restful-api-guide.md" }
];

const Docs = () => {
  return (
    <div className="flex flex-col min-h-screen">
      {/* Markdown Rendering Section */}
      <div className="container mx-auto mt-12 p-6 bg-gray-50 rounded-lg shadow-lg mb-4" id="md_resources">
        <h4 className="custom-font-size font-regular text-left text-green-800 my-8" id="render_markdown_title">🌐 Render Markdown Files as HTML</h4>
        <p className="text-blue-900 font-light text-lg block mb-2">
          Learn how to render Markdown (.md) files as HTML content displayed within a modal window. For more detailed instructions, refer this documentation:
          <p className="text-lg text-gray-800 hover:text-blue-600 transition-all duration-300">
            <NavLink to={markdownResources[0].path} className="flex items-center space-x-2 font-medium underline hover:no-underline">
              <span className="text-blue-600 font-light">Rendering Markdown as HTML</span>
            </NavLink>
          </p>
        </p>
        <hr className="border-t-2 border-gray-200 mb-8" />

        {/* Python Resources Section */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="flex flex-col items-start">
            <h4 className="custom-font-size font-light text-left text-blue-600 underline decoration-gray-300 decoration-2 mb-3" id="py_md_resource_title">🐍 Python Resources</h4>
            <ul className="cursor-pointer text-dark-blue list-disc list-inside space-y-2">
              {pythonResources.map((resource, index) => (
                <li key={index}>
                  <NavLink to={resource.path} className="hover:text-blue-800">{resource.name}</NavLink>
                </li>
              ))}
            </ul>
          </div>

          {/* Django Resources Section */}
          <div className="flex flex-col items-start">
            <h4 className="custom-font-size font-light text-left text-blue-600 underline decoration-gray-300 decoration-2 mb-3" id="django_md_resource_title">🌐 Django Rest Framework (DRF) Resources</h4>
            <ul className="cursor-pointer text-dark-blue list-disc list-inside space-y-2 text-gray-700">
              {djangoResources.map((resource, index) => (
                <li key={index}>
                  <NavLink to={resource.path} className="hover:text-blue-800">{resource.name}</NavLink>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>

      {/* Markdown to HTML Conversion Section */}
      <div className="container mx-auto mt-12 p-6 bg-gray-50 rounded-lg shadow-lg mt-4" id="html_resources">
        <h4 className="custom-font-size font-regular text-left text-green-800 my-8" id="render_html_title">🌐 Convert Markdown Files to Static HTML</h4>
        <p className="text-blue-900 font-light text-lg block mb-2">
          Learn how to convert Markdown (.md) files to fully rendered HTML pages. Explore techniques for styling, sanitizing, and displaying rich content in your React app.
        </p>
        <hr className="border-t-2 border-gray-200 mb-8" />

        {/* Python Resources Section */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="flex flex-col items-start">
            <h4 className="custom-font-size font-light text-left text-blue-600 underline decoration-gray-300 decoration-2 mb-3" id="py_html_resource_title">🐍 Python Resources</h4>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="flex flex-col items-start">
                <ul className="cursor-pointer text-dark-blue list-disc list-inside space-y-2">
                  <li>
                    ⚙️ Data Types
                    <ul className="ml-9 list-disc">
                      <li>
                        <Link to="/docs/python/journal/data_types_type_conversion" className="hover:text-blue-800">📊 Data Types and Type Conversions</Link>
                      </li>
                    </ul>
                  </li>
                  <li>
                    <Link to="/docs/python/journal/variables" className="hover:text-blue-800">🔄 Variables</Link>
                  </li>
                  <li>
                    <Link to="/docs/python/drf/diff-between-django_repr_and_str" className="hover:text-blue-800">🔤 `__repr__` vs `__str__` Comparison</Link>
                  </li>
                </ul>
              </div>
            </div>
          </div>

          {/* Django Resources Section */}
          <div className="flex flex-col items-start">
            <h4 className="custom-font-size font-light text-left text-blue-600 underline decoration-gray-300 decoration-2 mb-3" id="django_html_resource_title">🌐 Django Rest Framework (DRF) Resources</h4>
            <ul className="cursor-pointer text-dark-blue list-disc list-inside space-y-2 text-gray-700">
              <li>
                <Link to="/docs/python/drf/django_orm_basics" className="hover:text-blue-800">🧩 Django ORM Basics</Link>
              </li>
              <li>
                📝 Django Migrations
                <ul className="ml-9 list-disc">
                  <li>
                    <Link to="/docs/python/drf/django-migrations-fundamentals" className="hover:text-blue-800">📘 Django Migrations Fundamentals</Link>
                  </li>
                  <li>
                    <Link to="/docs/python/drf/django-migrations-examples-guide" className="hover:text-blue-800">🗄️ Django Migrations Examples</Link>
                  </li>
                </ul>
              </li>
              <li>
                <Link to="/docs/python/drf/django_logging_configuration" className="hover:text-blue-800">📋 Django Logging Configuration</Link>
              </li>
              <li>
                <Link to="/docs/python/drf/django-models-and-app-config-guide" className="hover:text-blue-800">🏗️ Django Models and App Configuration Guide</Link>
              </li>
              <li>
                <Link to="/docs/python/drf/django-simple-restful-api-guide" className="hover:text-blue-800">⚙️ Building a Simple RESTful API with DRF</Link>
              </li>
              <li>
                <Link to="/docs/python/journal/rendering_markdown_doc_as_html" className="hover:text-blue-800">🌐 Rendering Markdown as HTML</Link>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Docs;
