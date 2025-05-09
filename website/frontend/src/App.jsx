import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { HelmetProvider, Helmet } from 'react-helmet-async';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './pages/Home';
import Signup from './pages/Signup';
import Login from './pages/Login';
import Contact from './pages/Contact';
import Java from './pages/Java';
import Python from './pages/Python';
import Cicd from './pages/Cicd';
import Automation from './pages/Automation';
import './styles.css';
import React from 'react';

function App() {
  return (
    <HelmetProvider>
      <Router>
        <div className="flex flex-col min-h-screen bg-red-500">
          <Header />
          <main className="flex-grow pt-16">
            <Routes>
              <Route path="/" element={<><Helmet><title>Codefleet - Home</title></Helmet><Home /></>} />
              <Route path="/signup" element={<><Helmet><title>Codefleet - Sign Up</title></Helmet><Signup /></>} />
              <Route path="/login" element={<><Helmet><title>Codefleet - Login</title></Helmet><Login /></>} />
              <Route path="/contact" element={<><Helmet><title>Codefleet - Contact</title></Helmet><Contact /></>} />
              <Route path="/java" element={<><Helmet><title>Codefleet - Java</title></Helmet><Java /></>} />
              <Route path="/python" element={<><Helmet><title>Codefleet - Python</title></Helmet><Python /></>} />
              <Route path="/cicd" element={<><Helmet><title>Codefleet - CI/CD</title></Helmet><Cicd /></>} />
              <Route path="/automation" element={<><Helmet><title>Codefleet - Automation</title></Helmet><Automation /></>} />
            </Routes>
          </main>
          <Footer />
        </div>
      </Router>
    </HelmetProvider>
  );
}

export default App;