import React, { useState, useEffect, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { marked } from 'marked';
import 'github-markdown-css/github-markdown-light.css';
import '../../styles.css';

const Documentation = () => {
  const { filename } = useParams();
  const navigate = useNavigate();
  const [markdownContent, setMarkdownContent] = useState('');
  const modalRef = useRef(null);

  useEffect(() => {
    const fetchMarkdown = async () => {
      try {
        const cleanFilename = filename.endsWith('.md') ? filename : `${filename}.md`;
        const response = await fetch(`/markdown/${cleanFilename}?ts=${new Date().getTime()}`, {
          cache: 'no-store',
        });
        if (!response.ok) throw new Error(`File not found: ${response.statusText}`);
        const text = await response.text();
        setMarkdownContent(marked(text, { gfm: true, breaks: true }));
      } catch (error) {
        console.error('Error fetching markdown:', error);
        setMarkdownContent(`<p>Error loading markdown file: ${error.message}</p>`);
      }
    };
    fetchMarkdown();
  }, [filename]);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        navigate(-1);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, [navigate]);

  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex items-start justify-center z-[1001] pt-[120px] pb-[100px]">
      <div className="relative w-full max-w-[1200px] mx-auto">
        <button
          onClick={() => navigate(-1)}
          className="absolute top-[-40px] right-0 text-white bg-red-500 px-3 py-1 rounded hover:bg-red-700 z-[1002]"
        >
          Close
        </button>
        <div
          ref={modalRef}
          className="bg-white w-full h-full overflow-auto p-6 rounded-lg shadow-lg"
          style={{ maxHeight: '100%' }}
        >
          <div className="markdown-body" dangerouslySetInnerHTML={{ __html: markdownContent }} />
        </div>
      </div>
    </div>
  );
};

export default Documentation;