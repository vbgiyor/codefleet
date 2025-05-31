import React from 'react';

const Home = () => {
  return (
    <div className="container mx-auto mt-12 p-6 bg-gray-50 rounded-lg shadow-lg">
      <ul className="space-y-2 p-2 max-w-md mx-auto">
                  <li className="bg-white p-2 rounded-lg shadow hover:shadow-lg transition-shadow duration-300">
                  <li className="bg-white p-2 rounded-lg shadow hover:shadow-lg transition-shadow duration-300">
                  <li className="bg-white p-2 rounded-lg shadow hover:shadow-lg transition-shadow duration-300">
                  <li className="bg-white p-2 rounded-lg shadow hover:shadow-lg transition-shadow duration-300">
                  <li className="bg-white p-1 rounded-lg shadow hover:shadow-lg transition-shadow duration-300">
                      <h6 className="text-3xl font-bold text-center my-8" id="home_title">Welcome to Codefleet</h6>
                  </li>
                  </li>
                  </li>
                  </li>
                  </li>
                </ul>
      <p className="text-lg text-center font-light" id="home_description">Your one-stop platform for learning Java, Python and Automation.</p>
    </div>
  );
};

export default Home;