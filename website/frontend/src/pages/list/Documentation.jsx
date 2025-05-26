import React, { useState, useEffect, useRef } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import { marked } from 'marked';
import 'github-markdown-css/github-markdown-light.css';
import '../../styles.css';

const Documentation = () => {
  const { filename } = useParams();
  const navigate = useNavigate();
  const [markdownContent, setMarkdownContent] = useState('');
  const [isFullscreen, setIsFullscreen] = useState(false); // Fullscreen state
  const modalRef = useRef(null);
  const fullscreenButtonRef = useRef(null); // Reference to the fullscreen button
  const location = useLocation();

  // Fetch markdown content
  useEffect(() => {
    const fetchMarkdown = async () => {
      try {
        const fullPath = location.pathname.replace(/^\/documentation\//, '');
        const cleanPath = fullPath.endsWith('.md') ? fullPath : `${fullPath}.md`;
        const response = await fetch(`/markdown/${cleanPath}?ts=${new Date().getTime()}`, { cache: 'no-store' });
        if (!response.ok) throw new Error(`File not found: ${response.statusText}`);
        const text = await response.text();
        setMarkdownContent(marked(text, { gfm: true, breaks: true }));
      } catch (error) {
        console.error('Error fetching markdown:', error);
        setMarkdownContent(`<p>Error loading markdown file: ${error.message}</p>`);
      }
    };
    fetchMarkdown();
  }, [filename, location.pathname]);

  // Handle click outside of the modal
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && fullscreenButtonRef.current) {
        if (!modalRef.current.contains(event.target) && !fullscreenButtonRef.current.contains(event.target)) {
          navigate(-1);
        }
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, [navigate]);

  // Handle fullscreen change events
  useEffect(() => {
    const handleFullscreenChange = () => {
      const isCurrentlyFullscreen = !!(document.fullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || document.msFullscreenElement);
      setIsFullscreen(isCurrentlyFullscreen);
    };

    document.addEventListener('fullscreenchange', handleFullscreenChange);
    document.addEventListener('mozfullscreenchange', handleFullscreenChange);
    document.addEventListener('webkitfullscreenchange', handleFullscreenChange);
    document.addEventListener('msfullscreenchange', handleFullscreenChange);

    return () => {
      document.removeEventListener('fullscreenchange', handleFullscreenChange);
      document.removeEventListener('mozfullscreenchange', handleFullscreenChange);
      document.removeEventListener('webkitfullscreenchange', handleFullscreenChange);
      document.removeEventListener('msfullscreenchange', handleFullscreenChange);
    };
  }, []);

  // Handle Escape key to exit fullscreen
  useEffect(() => {
    const handleEscapeKey = (event) => {
      if (event.key === 'Escape' && isFullscreen) {
        if (document.fullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || document.msFullscreenElement) {
          if (document.exitFullscreen) {
            document.exitFullscreen();
          } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
          } else if (document.webkitExitFullscreen) {
            document.webkitExitFullscreen();
          } else if (document.msExitFullscreen) {
            document.msExitFullscreen();
          }
          setIsFullscreen(false); // Set fullscreen state to false
        }
      }
    };
    document.addEventListener('keydown', handleEscapeKey);
    return () => document.removeEventListener('keydown', handleEscapeKey);
  }, [isFullscreen]);

  // Toggle fullscreen mode for the modal
  const toggleFullscreen = (event) => {
    event.stopPropagation(); // Prevent the click from triggering handleClickOutside
    if (!isFullscreen) {
      if (modalRef.current.requestFullscreen) {
        modalRef.current.requestFullscreen();
      } else if (modalRef.current.mozRequestFullScreen) {
        modalRef.current.mozRequestFullScreen();
      } else if (modalRef.current.webkitRequestFullscreen) {
        modalRef.current.webkitRequestFullscreen();
      } else if (modalRef.current.msRequestFullscreen) {
        modalRef.current.msRequestFullscreen();
      }
      setIsFullscreen(true);
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex items-start justify-center z-[1001] pt-[120px] pb-[120px]">
      <div className={`relative w-full max-w-[1200px] mx-auto ${isFullscreen ? 'h-full' : ''}`}>
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
          style={{ display: isFullscreen ? 'none' : 'block' }} // Ensure button is hidden in fullscreen
        >
          Enter Fullscreen
        </button>
        {/* Modal Content */}
        <div
          ref={modalRef}
          className={`bg-white h-full overflow-auto p-6 rounded-lg shadow-lg ${isFullscreen ? 'w-full' : ''}`}
        >
          <div className="markdown-body" dangerouslySetInnerHTML={{ __html: markdownContent }} />
        </div>
      </div>
    </div>
  );
};

export default Documentation;