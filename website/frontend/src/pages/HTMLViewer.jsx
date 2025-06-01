import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

const HTMLViewer = () => {
  const { category, subcategory, doc } = useParams();
  const [htmlContent, setHtmlContent] = useState("");
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const url = `/static/md_to_html/${category}/${subcategory}/${doc}.html`;
    console.log("Fetching URL:", url);

    setIsLoading(true);
    fetch(url)
      .then((response) => {
        console.log("Response status:", response.status);
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.text();
      })
      .then((data) => {
        if (!data.trim()) {
          throw new Error("Received empty HTML response");
        }
        const parser = new DOMParser();
        const doc = parser.parseFromString(data, "text/html");
        const content =
          doc.querySelector(".markdown-body")?.innerHTML || doc.body.innerHTML || "";
        if (!content) {
          throw new Error("No content found in HTML");
        }
        setHtmlContent(content);
        setError(null);
      })
      .catch((error) => {
        console.error("Error loading HTML:", error);
        setError(error.message);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [category, subcategory, doc]);

  return (
    <div className="flex flex-col min-h-screen">
      <div className="container mx-auto flex-grow">
        {isLoading ? (
          <div className="text-gray-600">Loading...</div>
        ) : error ? (
          <div className="text-red-600">Error: {error}</div>
        ) : htmlContent ? (
          <div
            className="markdown-body"
            style={{ minHeight: "200px" }}
            dangerouslySetInnerHTML={{ __html: htmlContent }}
          />
        ) : (
          <div className="text-gray-600">No content available</div>
        )}
      </div>
    </div>
  );
};

export default HTMLViewer;
