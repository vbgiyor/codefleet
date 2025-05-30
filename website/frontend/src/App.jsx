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
import Automation from "./pages/Automation";
import ABTest from "./pages/list/ABTest";
import AddRemoveElements from "./pages/list/AddRemoveElements";
import BasicAuth from "./pages/list/BasicAuth";
import Documentation from "./pages/list/Documentation";
import MarkDownToHTML from "./pages/MDToHTML";
import DocViewer from "./pages/DocViewer";
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
              {" "}
              <Route
                path="/"
                element={
                  <>
                    {" "}
                    <Helmet>
                      {" "}
                      <title>Codefleet - Home</title>{" "}
                    </Helmet>{" "}
                    <Home />{" "}
                  </>
                }
              />{" "}
              <Route
                path="/signup"
                element={
                  <>
                    {" "}
                    <Helmet>
                      {" "}
                      <title>Codefleet - Sign Up</title>{" "}
                    </Helmet>{" "}
                    <Signup />{" "}
                  </>
                }
              />{" "}
              <Route
                path="/signin"
                element={
                  <>
                    {" "}
                    <Helmet>
                      {" "}
                      <title>Codefleet - Sign In</title>{" "}
                    </Helmet>{" "}
                    <SignIn />{" "}
                  </>
                }
              />{" "}
              <Route
                path="/forgot-password"
                element={
                  <>
                    {" "}
                    <Helmet>
                      {" "}
                      <title>Codefleet - Forgot Password</title>{" "}
                    </Helmet>{" "}
                    <ResetPassword />{" "}
                  </>
                }
              />{" "}
              <Route
                path="/reset-password/:uid/:token"
                element={
                  <>
                    {" "}
                    <Helmet>
                      {" "}
                      <title>Codefleet - Reset Password</title>{" "}
                    </Helmet>{" "}
                    <ResetPassword />{" "}
                  </>
                }
              />{" "}
              <Route
                path="/contact"
                element={
                  <>
                    {" "}
                    <Helmet>
                      {" "}
                      <title>Codefleet - Contact</title>{" "}
                    </Helmet>{" "}
                    <Contact />{" "}
                  </>
                }
              />{" "}
              <Route path="/case-studies">
                {" "}
                <Route
                  path="java"
                  element={
                    <>
                      {" "}
                      <Helmet>
                        {" "}
                        <title>Codefleet - Java Case Study</title>{" "}
                      </Helmet>{" "}
                      <Java />{" "}
                    </>
                  }
                />{" "}
                <Route
                  path="python"
                  element={
                    <>
                      {" "}
                      <Helmet>
                        {" "}
                        <title>Codefleet - Python Case Study</title>{" "}
                      </Helmet>{" "}
                      <Python />{" "}
                    </>
                  }
                />{" "}
                <Route path="automation">
                  {" "}
                  <Route
                    index
                    element={
                      <>
                        {" "}
                        <Helmet>
                          {" "}
                          <title>Codefleet - Automation Case Study</title>{" "}
                        </Helmet>{" "}
                        <Automation />{" "}
                      </>
                    }
                  />{" "}
                  <Route
                    path="abtest"
                    element={
                      <>
                        {" "}
                        <Helmet>
                          {" "}
                          <title>Codefleet - A/B Testing</title>{" "}
                        </Helmet>{" "}
                        <ABTest />{" "}
                      </>
                    }
                  />{" "}
                  <Route
                    path="addremoveelements"
                    element={
                      <>
                        {" "}
                        <Helmet>
                          {" "}
                          <title>Codefleet - Add / Remove Elements</title>{" "}
                        </Helmet>{" "}
                        <AddRemoveElements />{" "}
                      </>
                    }
                  />{" "}
                </Route>{" "}
                <Route
                  path="markdowntohtml"
                  element={
                    <>
                      {" "}
                      <Helmet>
                        {" "}
                        <title>
                          Codefleet - Markdown to HTML Conversion
                        </title>{" "}
                      </Helmet>{" "}
                      <MarkDownToHTML />{" "}
                    </>
                  }
                />{" "}
              </Route>{" "}
              <Route
                path="/basicauth"
                element={
                  <>
                    {" "}
                    <Helmet>
                      {" "}
                      <title>Codefleet - Basic Authentication</title>{" "}
                    </Helmet>{" "}
                    <BasicAuth />{" "}
                  </>
                }
              />{" "}
              <Route
                path="/documentation/*"
                element={
                  <>
                    {" "}
                    <Helmet>
                      {" "}
                      <title>Codefleet - Documentation</title>{" "}
                    </Helmet>{" "}
                    <Documentation />{" "}
                  </>
                }
              />{" "}
              <Route
                path="/docs/:category/:subcategory/:doc"
                element={<DocViewer />}
              />{" "}
            </Routes>{" "}
          </main>{" "}
          <Footer className="footer" />{" "}
        </div>{" "}
      </Router>{" "}
    </HelmetProvider>
  );
}
export default App;