import React from "react";
import "../styles.css";

// Define Python Projects dynamically
const pythonProjects = [
  { name: "Books Library", path: "#" },
  { name: "Ecommerce", path: "#" },
  { name: "Fake Payment Interface", path: "#" },
  { name: "TODO List", path: "#" },
  { name: "Event Management", path: "#" },
  { name: "Ticket Booking", path: "#" }
];

const Python = () => {
  return (
    <div className="flex flex-col min-h-screen">
	<div className="container mx-auto mt-12 p-6 bg-gray-50 rounded-lg shadow-lg">
		<div className="flex justify-center items-center my-4">
			<h2 className="text-2xl font-semibold text-center text-blue-900 bg-white backdrop-blur-sm p-4 rounded-2xl border border-blue-100">
				Python Resource Library
			</h2>
		</div>
        <p className="text-1xl text-left font-light" id="python_description">
		Explore a wide range of resources covering Python programming concepts, in-depth tutorials, and practical coding projects.
		Dive deeper into the world of Django REST Framework (DRF), where you'll learn how to build scalable and robust APIs with real-world examples.
		</p>
      </div>
      <div className="container mx-auto mt-12 grid grid-cols-1 md:grid-cols-3 gap-2" id="pyprojects">
        <h4 className="custom-font-size font-bold text-left text-green-800 my-4 col-span-full" id="py_projects_title">🐍 Python Projects</h4>
        {pythonProjects.map((project, index) => (
          <ul key={index} className="w-full max-w-md p-4 space-y-3 bg-blue-100/10 backdrop-blur-md rounded-xl shadow-lg">
            <li className="bg-blue/10 backdrop-blur-sm rounded-lg p-3 hover:bg-blue-200/20 transition-all">
              <a href={project.path} className="text-blue-900 font-light text-lg block">
                {project.name}
              </a>
            </li>
          </ul>
        ))}
      </div>
    </div>
  );
};
export default Python;