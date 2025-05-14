import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const ABTestingComponent = () => {
  const [version, setVersion] = useState('');

  useEffect(() => {
    // Randomly assign version A or B
    const assignedVersion = Math.random() > 0.5 ? 'A' : 'B';
    setVersion(assignedVersion);
  }, []);

  return (
    <div className="container mx-auto py-8 flex flex-col justify-start items-center min-h-full bg-gray-100 relative">
      {/* Back Link */}
      <div className="absolute top-4 left-4">
        <Link to=".." className="text-blue-600 hover:text-blue-800 underline">
          ← Back to Automation
        </Link>
      </div>

      {version === 'A' ? (
        <div className="bg-blue-50 p-8 rounded-2xl shadow-lg transition duration-500 ease-in-out transform hover:scale-105">
          <h2 className="text-3xl font-bold text-blue-600 mb-4">Surprise!!!</h2>
        </div>
      ) : (
        <div className="bg-red-50 p-8 rounded-2xl shadow-lg transition duration-500 ease-in-out transform hover:scale-105">
          <h2 className="text-3xl font-bold text-red-600 mb-4">Good News!!!</h2>
        </div>
      )}
      <div className="mt-8">
        <p className="text-gray-700">
          This is a simple A/B testing example. Depending on the version assigned, you will see different content.
        </p>
      </div>
    </div>
  );
};

export default ABTestingComponent;