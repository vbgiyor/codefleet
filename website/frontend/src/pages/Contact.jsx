import React, { useState } from 'react';
import axios from 'axios';

const Contact = () => {
  const [formData, setFormData] = useState({ name: '', email: '', message: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    try {
      const response = await axios.post('http://localhost:8000/api/contact/', formData, {
        headers: { 'Content-Type': 'application/json' },
      });
      setSuccess(response.data.message);
      setFormData({ name: '', email: '', message: '' });
    } catch (err) {
      setError(err.response?.data?.message || 'An error occurred.');
    }
  };

  return (
    <div className="container mx-auto max-w-md">
      <h2 className="text-2xl font-bold text-center my-8" id="contact_title">Contact Us</h2>
      {error && <p className="text-red-500 text-center" id="contact_error">{error}</p>}
      {success && <p className="text-green-500 text-center" id="contact_success">{success}</p>}
      <form onSubmit={handleSubmit} className="p-6" id="contact_form">
        <div className="mb-4">
          <label htmlFor="name" className="block text-primary-blue">Name</label>
          <input
            type="text"
            id="name"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            className="w-full p-2 border rounded"
            required
          />
        </div>
        <div className="mb-4">
          <label htmlFor="email" className="block text-primary-blue">Email Address</label>
          <input
            type="email"
            id="email"
            value={formData.email}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
            className="w-full p-2 border rounded"
            required
          />
        </div>
        <div className="mb-4">
          <label htmlFor="message" className="block text-primary-blue">Message</label>
          <textarea
            id="message"
            value={formData.message}
            onChange={(e) => setFormData({ ...formData, message: e.target.value })}
            className="w-full p-2 border rounded"
            rows="5"
            required
          />
        </div>
        <button type="submit" className="btn">Submit</button>
      </form>
    </div>
  );
};

export default Contact;