import React from 'react';
import { Link, Outlet } from "react-router-dom";

const Automation = () => {
  return (
    <div className="container mx-auto">
      <h2 className="text-3xl font-bold text-center my-8" id="automation_title">Automation Testing</h2>
      <div className="prose max-w-none" id="automation_content">
        <p id="internet_description">
          Below is a collection of resources for practicing automation testing, inspired by{' '}
          <a href="https://the-internet.herokuapp.com/" id="internet_link" className="text-red-400 hover:text-red-600 border-b border-gray-300 pb-1">
            the-internet.herokuapp.com
          </a>.
        </p>
        <ul id="test_resources" className="list-disc list-inside">
          <li>
            <Link to="abtest" className="text-blue-600 hover:text-blue-800 underline" id="abtest_link">
              A/B Testing
            </Link>
          </li>
          <li>
            <Link to="addremoveelements" className="text-blue-600 hover:text-blue-800 underline" id="add_remove_link">
              Add / Remove Elements
            </Link>
          </li>
          <li>
            <Link to="/basicauth" className="text-blue-600 hover:text-blue-800 underline" id="basic_auth_link">
              Basic Auth
            </Link>
          </li>
          <li><a href="/broken_images" id="broken_images_link">Broken Images</a></li>
          <li><a href="/challenging_dom" id="challenging_dom_link">Challenging DOM</a></li>
          <li><a href="/checkboxes" id="checkboxes_link">Checkboxes</a></li>
          <li><a href="/context_menu" id="context_menu_link">Context Menu</a></li>
          <li><a href="/digest_auth" id="digest_auth_link">Digest Authentication</a></li>
          <li><a href="/disappearing_elements" id="disappearing_elements_link">Disappearing Elements</a></li>
          <li><a href="/drag_and_drop" id="drag_and_drop_link">Drag and Drop</a></li>
          <li><a href="/dropdown" id="dropdown_link">Dropdown</a></li>
          <li><a href="/dynamic_controls" id="dynamic_controls_link">Dynamic Controls</a></li>
          <li><a href="/dynamic_loading" id="dynamic_loading_link">Dynamic Loading</a></li>
          <li><a href="/entry_ad" id="entry_ad_link">Entry Ad</a></li>
          <li><a href="/exit_intent" id="exit_intent_link">Exit Intent</a></li>
          <li><a href="/file_download" id="file_download_link">File Download</a></li>
          <li><a href="/file_upload" id="file_upload_link">File Upload</a></li>
          <li><a href="/floating_menu" id="floating_menu_link">Floating Menu</a></li>
          <li><a href="/forgot_password" id="forgot_password_link">Forgot Password</a></li>
          <li><a href="/form_authentication" id="form_authentication_link">Form Authentication</a></li>
          <li><a href="/frames" id="frames_link">Frames</a></li>
          <li><a href="/geolocation" id="geolocation_link">Geolocation</a></li>
          <li><a href="/horizontal_slider" id="horizontal_slider_link">Horizontal Slider</a></li>
          <li><a href="/hovers" id="hovers_link">Hovers</a></li>
          <li><a href="/infinite_scroll" id="infinite_scroll_link">Infinite Scroll</a></li>
          <li><a href="/inputs" id="inputs_link">Inputs</a></li>
          <li><a href="/jqueryui" id="jqueryui_link">JQuery UI Menus</a></li>
          <li><a href="/javascript_alerts" id="javascript_alerts_link">JavaScript Alerts</a></li>
          <li><a href="/javascript_error" id="javascript_error_link">JavaScript onload event error</a></li>
          <li><a href="/key_presses" id="key_presses_link">Key Presses</a></li>
          <li><a href="/large" id="large_link">Large & Deep DOM</a></li>
          <li><a href="/login" id="login_link">Login</a></li>
          <li><a href="/multiple_windows" id="multiple_windows_link">Multiple Windows</a></li>
          <li><a href="/nested_frames" id="nested_frames_link">Nested Frames</a></li>
          <li><a href="/notification_message" id="notification_message_link">Notification Message</a></li>
          <li><a href="/redirect" id="redirect_link">Redirect Link</a></li>
          <li><a href="/secure_file_download" id="secure_file_download_link">Secure File Download</a></li>
          <li><a href="/shadow_dom" id="shadow_dom_link">Shadow DOM</a></li>
          <li><a href="/shifting_content" id="shifting_content_link">Shifting Content</a></li>
          <li><a href="/slow" id="slow_link">Slow Resources</a></li>
          <li><a href="/sortabledatatables" id="sortable_data_tables_link">Sortable Data Tables</a></li>
          <li><a href="/status_codes" id="status_codes_link">Status Codes</a></li>
          <li><a href="/tables" id="tables_link">Tables</a></li>
          <li><a href="/tinymce" id="tinymce_link">TinyMCE WYSIWYG Editor</a></li>
          <li><a href="/typos" id="typos_link">Typos</a></li>
          <li><a href="/windows" id="windows_link">Windows</a></li>
        </ul>
        <p id="footer_note">
          Note: These links are placeholders for future implementation. Visit{' '}
          <a href="https://the-internet.herokuapp.com/" id="original_internet_link">
            the-internet.herokuapp.com
          </a>{' '}
          for live examples.
        </p>
      </div>
      <Outlet />
    </div>
  );
};

export default Automation;