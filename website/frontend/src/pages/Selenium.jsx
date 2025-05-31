import React from 'react';
import { Link, Outlet } from "react-router-dom";

// Define Selenium Projects dynamically
const seleniumProjects = [
  { name: "CFInspector", path: "cfinspector" },
  { name: "GithubRepoListingDemo", path: "#" },
  { name: "PageInspector", path: "#" }
];

const Selenium = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <div className="container mx-auto mt-12 p-6 bg-gray-50 rounded-lg shadow-lg">
        <div className="flex justify-center items-center my-4">
          <h2 className="text-2xl font-semibold text-center text-blue-900 bg-white backdrop-blur-sm p-4 rounded-2xl border border-blue-100">
            Selenium Resource Library
          </h2>
        </div>
          <p className="text-1xl text-left font-light" id="python_description">
                This in-depth tutorial series explores Selenium UI automation with Java, focusing on key topics such as element locators, wait strategies, and browser interactions. Finally, discover how to manage browser interactions for effective, reliable automated testing.
        </p>
        </div>
      <div className="container mx-auto mt-12 grid grid-cols-1 md:grid-cols-3 gap-2" id="pyprojects">
        <h4 className="custom-font-size font-bold text-left text-blue-900 my-4 col-span-full" id="py_projects_title">🔹 Selenium Projects</h4>
        {seleniumProjects.map((project, index) => (
          <ul key={index} className="w-full max-w-md p-4 space-y-3 bg-blue-100/10 backdrop-blur-md rounded-xl shadow-lg">
            <li className="bg-blue/10 backdrop-blur-sm rounded-lg p-3 hover:bg-blue-200/20 transition-all">
              <Link to={project.path} className="text-blue-900 font-light text-lg block">
                {project.name}
              </Link>
            </li>
          </ul>
        ))}
      </div>
      <Outlet />
    </div>
  );
};

export default Selenium;
