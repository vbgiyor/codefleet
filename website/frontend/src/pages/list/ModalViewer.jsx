import React, { useState, useEffect, useRef } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import { marked } from "marked";
import "github-markdown-css/github-markdown-light.css";
import "../../styles.css";
const ModalViewer = () => {
  const { filename } = useParams();
  const navigate = useNavigate();
  const [markdownContent, setMarkdownContent] = useState("");
  const [isFullscreen, setIsFullscreen] = useState(false);
  const modalRef = useRef(null);
  const fullscreenButtonRef = useRef(null);
  const location = useLocation();
  useEffect(() => {
    const fetchMarkdown = async () => {
      try {
        const fullPath = location.pathname.replace(/^\/documentation\//, "");
        const cleanPath = fullPath.endsWith(".md")
          ? fullPath
          : `${fullPath}.md`;        
        const BASE_URL = process.env.REACT_APP_API_URL || '';
        const response = await fetch(`${BASE_URL}/markdown/${cleanPath}?ts=${new Date().getTime()}`, { cache: "no-store" });
        if (!response.ok)
          throw new Error(`File not found: ${response.statusText}`);        
        const text = await response.text();
        console.log("Raw response:", text);
        setMarkdownContent(marked(text, { gfm: true, breaks: true }));
      } catch (error) {
        console.error("Error fetching markdown:", error);
        setMarkdownContent(
          `<p>Error loading markdown file: ${error.message}</p>`
        );
      }
    };
    fetchMarkdown();
  }, [filename, location.pathname]);
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        modalRef.current &&
        fullscreenButtonRef.current &&
        !modalRef.current.contains(event.target) &&
        !fullscreenButtonRef.current.contains(event.target)
      ) {
        navigate(-1);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, [navigate]);
  useEffect(() => {
    const handleFullscreenChange = () => {
      const isCurrentlyFullscreen = !!(
        document.fullscreenElement ||
        document.mozFullScreenElement ||
        document.webkitFullscreenElement ||
        document.msFullscreenElement
      );
      setIsFullscreen(isCurrentlyFullscreen);
    };
    document.addEventListener("fullscreenchange", handleFullscreenChange);
    document.addEventListener("mozfullscreenchange", handleFullscreenChange);
    document.addEventListener("webkitfullscreenchange", handleFullscreenChange);
    document.addEventListener("msfullscreenchange", handleFullscreenChange);
    return () => {
      document.removeEventListener("fullscreenchange", handleFullscreenChange);
      document.removeEventListener(
        "mozfullscreenchange",
        handleFullscreenChange
      );
      document.removeEventListener(
        "webkitfullscreenchange",
        handleFullscreenChange
      );
      document.removeEventListener(
        "msfullscreenchange",
        handleFullscreenChange
      );
    };
  }, []);
  useEffect(() => {
    const handleEscapeKey = (event) => {
      if (event.key === "Escape" && isFullscreen) {
        if (
          document.exitFullscreen ||
          document.mozCancelFullScreen ||
          document.webkitExitFullscreen ||
          document.msExitFullscreen
        ) {
          if (document.exitFullscreen) document.exitFullscreen();
          else if (document.mozCancelFullScreen) document.mozCancelFullScreen();
          else if (document.webkitExitFullscreen)
            document.webkitExitFullscreen();
          else if (document.msExitFullscreen) document.msExitFullscreen();
          setIsFullscreen(false);
        }
      }
    };
    document.addEventListener("keydown", handleEscapeKey);
    return () => document.removeEventListener("keydown", handleEscapeKey);
  }, [isFullscreen]);
  const toggleFullscreen = (event) => {
    event.stopPropagation();
    if (!isFullscreen) {
      if (modalRef.current.requestFullscreen)
        modalRef.current.requestFullscreen();
      else if (modalRef.current.mozRequestFullScreen)
        modalRef.current.mozRequestFullScreen();
      else if (modalRef.current.webkitRequestFullscreen)
        modalRef.current.webkitRequestFullscreen();
      else if (modalRef.current.msRequestFullscreen)
        modalRef.current.msRequestFullscreen();
      setIsFullscreen(true);
    }
  };
  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 z-[1001] flex justify-center items-center">
      
      <div
        className={`relative w-full ${
          isFullscreen ? "h-full p-4" : "max-w-[1200px] max-h-[90vh] mx-4"
        } bg-white rounded-lg shadow-lg flex flex-col`}
      >
        
        {/* Close Button */}
        <button
          onClick={() => navigate(-1)}
          className="absolute top-[-40px] right-0 text-white bg-red-500 px-3 py-1 rounded hover:bg-red-700 z-[1002]"
        >
          
          Close
        </button>
        {/* Fullscreen Button */}
        <button
          ref={fullscreenButtonRef}
          onClick={toggleFullscreen}
          className="absolute top-[-40px] left-0 text-white bg-blue-500 px-3 py-1 rounded hover:bg-blue-700 z-[1002]"
          style={{ display: isFullscreen ? "none" : "block" }}
        >
          
          Enter Fullscreen
        </button>
        {/* Modal Content */}
        <div
          ref={modalRef}
          className={`flex-1 overflow-y-auto p-6 rounded-lg ${
            isFullscreen ? "bg-white max-w-screen-xl mx-auto" : ""
          }`}
        >
          
          <div
            className="markdown-body"
            dangerouslySetInnerHTML={{ __html: markdownContent }}
          />
        </div>
      </div>
    </div>
  );
};
export default ModalViewer;