// App.jsx
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { HelmetProvider, Helmet } from "react-helmet-async";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import Signup from "./pages/Signup";
import SignIn from "./pages/SignIn";
import ResetPassword from "./pages/ResetPassword";
import Contact from "./pages/Contact";
import Java from "./pages/Java";
import Python from "./pages/Python";
import Selenium from "./pages/Selenium";
import ABTest from "./pages/list/ABTest";
import AddRemoveElements from "./pages/list/AddRemoveElements";
import BasicAuth from "./pages/list/BasicAuth";
import ModalViewer from "./pages/list/ModalViewer";
import MarkDownToHTML from "./pages/Docs";
import HTMLViewer from "./pages/HTMLViewer";
import CFInspectorPage from "./pages/list/CFInpsector"; 

function App() {
  return (
    <HelmetProvider>
      {" "}
      <Router>
        {" "}
        <div className="flex flex-col min-h-screen">
          {" "}
          <Header className="navbar" />{" "}
          <main className="flex-grow pt-16 container mx-auto">
            {" "}
            <Routes>
              {/* Home */}
              <Route path="/" element={<><Helmet><title>Codefleet - Home</title></Helmet><Home /></>} />

              {/* Sign Up, Sign In, etc. */}
              <Route path="/signup" element={<><Helmet><title>Codefleet - Sign Up</title></Helmet><Signup /></>} />
              <Route path="/signin" element={<><Helmet><title>Codefleet - Sign In</title></Helmet><SignIn /></>} />
              <Route path="/forgot-password" element={<><Helmet><title>Codefleet - Forgot Password</title></Helmet><ResetPassword /></>} />
              <Route path="/reset-password/:uid/:token" element={<><Helmet><title>Codefleet - Reset Password</title></Helmet><ResetPassword /></>} />
              <Route path="/contact" element={<><Helmet><title>Codefleet - Contact</title></Helmet><Contact /></>} />

              {/* Resources Section */}
              <Route path="/resources">
                <Route path="java" element={<><Helmet><title>Codefleet - Java Case Study</title></Helmet><Java /></>} />
                <Route path="python" element={<><Helmet><title>Codefleet - Python Case Study</title></Helmet><Python /></>} />
                <Route path="docs" element={<><Helmet><title>Codefleet - Markdown to HTML Conversion</title></Helmet><MarkDownToHTML /></>} />

                {/* Selenium Resource and Projects */}
                <Route path="selenium">
                  <Route index element={<><Helmet><title>Codefleet - Selenium Automation Case Study</title></Helmet><Selenium /></>} />
                  <Route path="cfinspector" element={<><Helmet><title>Codefleet - CFInspector Automation</title></Helmet><CFInspectorPage /></>} />
                </Route>

                {/* Other Resources */}
                <Route path="/resources/selenium/cfinspector/abtest" element={<><Helmet><title>Codefleet - A/B Testing</title></Helmet><ABTest /></>} />
                <Route path="/resources/selenium/cfinspector/addremoveelements" element={<><Helmet><title>Codefleet - Add / Remove Elements</title></Helmet><AddRemoveElements /></>} />
                <Route path="/resources/selenium/cfinspector/basicauth" element={<><Helmet><title>Codefleet - Basic Authentication</title></Helmet><BasicAuth /></>} />

              </Route>

              {/* Docs and other Pages */}
              <Route path="/documentation/*" element={<><Helmet><title>Codefleet - Documentation</title></Helmet><ModalViewer /></>} />
              <Route path="/docs/:category/:subcategory/:doc" element={<HTMLViewer />} />
            </Routes>
          </main>
          <Footer className="footer" />
        </div>
      </Router>
    </HelmetProvider>
  );
}

export default App;
