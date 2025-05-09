import React, { useState } from 'react';
import axios from 'axios';
import { ClipLoader } from 'react-spinners';
import { useForm } from 'react-hook-form';

const Contact = () => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const onSubmit = async (data) => {
    setLoading(true);
    try {
      const response = await axios.post('http://localhost:8000/api/contact/', data);
      setSuccess(response.data.message);
      setError('');
      window.location.href = '/';
    } catch (err) {
      setError(err.response?.data?.non_field_errors || 'An error occurred.');
      setSuccess('');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mx-auto max-w-md">
      <h2 className="text-2xl font-bold text-center my-8" id="contact_title">Contact Us</h2>
      {error && <p className="text-red-500 text-center" id="contact_error">{error}</p>}
      {success && <p className="text-green-500 text-center" id="contact_success">{success}</p>}
      <form onSubmit={handleSubmit(onSubmit)} className="bg-white p-6 rounded shadow-md" id="contact_form">
        <div className="mb-4">
          <label htmlFor="email" className="block text-gray-700">Email Address</label>
          <input
            type="email"
            id="email"
            {...register('email', { required: 'Email is required', pattern: { value: /^\S+@\S+$/i, message: 'Invalid email' } })}
            className={`w-full p-2 border rounded ${errors.email ? 'border-red-500' : ''}`}
          />
          {errors.email && <p className="text-red-500">{errors.email.message}</p>}
        </div>
        <div className="mb-4">
          <label htmlFor="opinion" className="block text-gray-700">Opinion</label>
          <textarea
            id="opinion"
            {...register('opinion', { required: 'Opinion is required' })}
            className={`w-full p-2 border rounded ${errors.opinion ? 'border-red-500' : ''}`}
            rows="5"
          ></textarea>
          {errors.opinion && <p className="text-red-500">{errors.opinion.message}</p>}
        </div>
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded w-full" id="contact_submit" disabled={loading}>
          {loading ? <ClipLoader size={20} color="#fff" /> : 'Submit'}
        </button>
      </form>
    </div>
  );
};

export default Contact;