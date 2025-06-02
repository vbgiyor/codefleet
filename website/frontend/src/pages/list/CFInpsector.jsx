import React from "react";
import { Link } from "react-router-dom";

// Define the resources array with a list of objects containing link labels and paths
const resources = [
  { name: "A/B Testing", path: "abtest" },
  { name: "Add / Remove Elements", path: "addremoveelements" },
  { name: "Basic Auth", path: "basicauth" },
  { name: "Broken Images", path: "#broken_images" },
  { name: "Challenging DOM", path: "#challenging_dom" },
  { name: "Checkboxes", path: "#checkboxes" },
  { name: "Context Menu", path: "#context_menu" },
  { name: "Digest Authentication", path: "#digest_auth" },
  { name: "Disappearing Elements", path: "#disappearing_elements" },
  { name: "Drag and Drop", path: "#drag_and_drop" },
  { name: "Dropdown", path: "#dropdown" },
  { name: "Dynamic Controls", path: "#dynamic_controls" },
  { name: "Dynamic Loading", path: "#dynamic_loading" },
  { name: "Entry Ad", path: "#entry_ad" },
  { name: "Exit Intent", path: "#exit_intent" },
  { name: "File Download", path: "#file_download" },
  { name: "File Upload", path: "#file_upload" },
  { name: "Floating Menu", path: "#floating_menu" },
  { name: "Forgot Password", path: "#forgot_password" },
  { name: "Form Authentication", path: "#form_authentication" },
  { name: "Frames", path: "#frames" },
  { name: "Geolocation", path: "#geolocation" },
  { name: "Horizontal Slider", path: "#horizontal_slider" },
  { name: "Hovers", path: "#hovers" },
  { name: "Infinite Scroll", path: "#infinite_scroll" },
  { name: "Inputs", path: "#inputs" },
  { name: "JQuery UI Menus", path: "#jqueryui" },
  { name: "JavaScript Alerts", path: "#javascript_alerts" },
  { name: "JavaScript onload event error", path: "#javascript_error" },
  { name: "Key Presses", path: "#key_presses" },
  { name: "Large & Deep DOM", path: "#large" },
  { name: "Login", path: "#login" },
  { name: "Multiple Windows", path: "#multiple_windows" },
  { name: "Nested Frames", path: "#nested_frames" },
  { name: "Notification Message", path: "#notification_message" },
  { name: "Redirect Link", path: "#redirect" },
  { name: "Secure File Download", path: "#secure_file_download" },
  { name: "Shadow DOM", path: "#shadow_dom" },
  { name: "Shifting Content", path: "#shifting_content" },
  { name: "Slow Resources", path: "#slow" },
  { name: "Sortable Data Tables", path: "#sortabledatatables" },
  { name: "Status Codes", path: "#status_codes" },
  { name: "Tables", path: "#tables" },
  { name: "TinyMCE WYSIWYG Editor", path: "#tinymce" },
  { name: "Typos", path: "#typos" },
  { name: "Windows", path: "#windows" }
];

const CFInspectorPage = () => {
  return (
    
    <div className="container mx-auto my-8 p-6 bg-gray-50 rounded-lg shadow-lg relative">
      {/* Back to Selenium Projects Link */}
      
        <div className="back-link">
          <Link to=".." className="text-blue-600 hover:text-blue-800"> ← Back to Selenium Projects </Link>
        </div>

      {/* Title and Description */}
      <div className="mt-8"> {/* Add spacing above the content */}
        {/* <h3 className="text-2xl font-regular text-blue-800 mb-4">Project: CFInspector</h3> */}
        <p className="text-2xl font-bold text-left my-4 px-6 py-4 bg-white rounded-lg shadow-md text-blue-800">
          Project: CFInspector
        </p>
        <p className="text-blue-900 font-light text-lg block mb-6">
          <span className="text-black-900">CFInspector</span> is a powerful tool designed for inspecting and analyzing content. 
          With a focus on data extraction and validation, it allows users to dynamically interact with web elements and ensure compliance with automation standards.
        </p>
      </div>

    

      {/* Features Section */}
      <div className="mt-8">
        <h3 className="text-xl font-regular text-blue-800 mb-4">Key Features:</h3>
        <ul className="list-disc pl-6 space-y-2 text-blue-900 font-light text-lg block">
          <li>Element Locator Strategies</li>
          <li>Browser Interactions & Control</li>
          <li>Automation and Debugging Tools</li>
          <li>Easy-to-run Command-Line execution</li>
        </ul>
      </div>

      <hr className="border-t-2 border-gray-300 my-8" />

      {/* Heroku Automation Content Section */}
      <div id="heroku_automation" className="mt-6">
        <p className="text-2xl font-bold text-left my-4 px-6 py-4 bg-white rounded-lg shadow-md text-blue-800">
          Web Interactions & Elements Checklist
        </p>

        <div className="prose max-w-none mt-6" id="automation_content">
          <p id="internet_description" className="text-blue-900 font-light text-lg block">
            This collection of resources is designed to help you practice automation testing, inspired by the popular testing site{' '}
            <a
              href="https://the-internet.herokuapp.com/"
              id="internet_link"
              className="text-blue-800 hover:text-blue-900 underline"
            >
              the-internet.herokuapp.com
            </a>.
            I’ve curated these resources to facilitate the automation of fundamental web interactions and element validations. Whether you're testing forms, buttons, dynamic content, or advanced UI elements, this collection will guide you through real-world examples, helping you enhance your testing skills and build robust automation frameworks.
          </p>

          <ul id="test_resources" className="list-disc list-inside mt-6 space-y-2">
            {resources.map((resource, index) => (
              <li key={index}>
                <Link 
                  to={resource.path} 
                  className="text-blue-600 hover:text-blue-800 underline"
                  id={`${resource.path}_link`}
                >
                  {resource.name}
                </Link>
              </li>
            ))}
          </ul>

          <p id="footer_note" className="text-sm text-gray-500 mt-6">
            Note: Some links which are doing nothing in this list, are placeholders for future implementation.</p>
        </div>
      </div>
    </div>
  );
};

export default CFInspectorPage;
