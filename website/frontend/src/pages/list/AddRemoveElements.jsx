import React, { useState } from "react";
import { Link } from "react-router-dom";

const AddRemoveElements = () => {
  const MAX_ELEMENTS = 5; // Maximum allowed elements in the list
  const [list, setList] = useState(["AI", "ML", "Data Science"]);
  const [inputValue, setInputValue] = useState("");

  // Handle input change
  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };

  // Add Element
  const handleAddElement = () => {
    const trimmedValue = inputValue.trim();
    if (trimmedValue.length >= 1 && trimmedValue.length <= 32 && list.length < MAX_ELEMENTS) {
      setList([...list, `New Element: ${trimmedValue}`]);
      setInputValue(""); // Clear input
    }
  };

  // Remove Element
  const handleRemoveElement = () => {
    if (list.length > 0) {
      const updatedList = [...list];
      updatedList.pop();
      setList(updatedList);
    }
  };

  return (
    <div className="container mx-auto py-8 bg-gray-100 min-h-full">
      {/* Back Link */}
      <div className="mb-4">
        <Link to=".." className="text-blue-600 hover:text-blue-800 underline">
          ← Back to Automation
        </Link>
      </div>

      {/* Title */}
      <h2 className="text-2xl font-bold text-center mb-6">Add/Remove Elements</h2>

      {/* Input and Buttons */}
      <div className="flex justify-center items-center mb-4">
        <input
          type="text"
          value={inputValue}
          onChange={handleInputChange}
          placeholder="Enter a value (1-32 characters)"
          className="border border-gray-300 rounded-lg px-4 py-2 mr-4 focus:outline-none focus:ring-2 focus:ring-blue-400"
          disabled={list.length >= MAX_ELEMENTS}
        />

        <button
          onClick={handleAddElement}
          className={`bg-blue-600 text-white py-2 px-4 rounded-lg mr-2 transition duration-300 ${
            list.length >= MAX_ELEMENTS || inputValue.trim().length < 1 || inputValue.trim().length > 32
              ? "bg-blue-300 cursor-not-allowed"
              : "hover:bg-blue-500"
          }`}
          disabled={list.length >= MAX_ELEMENTS || inputValue.trim().length < 1 || inputValue.trim().length > 32}
        >
          Add Element
        </button>

        <button
          onClick={handleRemoveElement}
          className={`bg-red-600 text-white py-2 px-4 rounded-lg transition duration-300 ${
            list.length === 0 ? "bg-red-300 cursor-not-allowed" : "hover:bg-red-500"
          }`}
          disabled={list.length === 0}
        >
          Remove Element
        </button>
      </div>

      {/* List Section */}
      <div className="bg-white p-6 rounded-lg shadow-lg">
        {list.length > 0 ? (
          <ul className="list-disc list-inside text-gray-700">
            {list.map((item, index) => (
              <li key={index}>{item}</li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-500">No elements to remove.</p>
        )}
      </div>

      {/* Limit Reached Message */}
      {list.length >= MAX_ELEMENTS && (
        <p className="text-red-600 text-center mt-4">Maximum limit of 5 elements reached.</p>
      )}
    </div>
  );
};

export default AddRemoveElements;