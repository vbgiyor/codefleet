import React, { useState } from 'react';
import axios from 'axios';
import { ClipLoader } from 'react-spinners';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

const Signup = () => {
  const { register, handleSubmit, formState: { errors } } = useForm({
    defaultValues: {
      subscribe_newsletter: true
    }
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await axios.post('http://localhost:8000/api/signup/', data);
      console.log(response);
      setSuccess(
        data.subscribe_newsletter
          ? "Registration successful. Thank you for subscribing to our newsletter."
          : "Registration successful."
      );
      setError('');
      setTimeout(() => {
        navigate('/signin');
      }, 2000);
    } catch (err) {
      console.log('Backend Error Response:', err.response?.data);
      const errorData = err.response?.data;
      if (errorData && typeof errorData === 'object') {
        const errorMessages = Object.entries(errorData)
          .map(([key, value]) => (Array.isArray(value) ? value.join(' ') : value))
          .join(' ');
        setError(errorMessages || 'An error occurred.');
      } else {
        setError('An error occurred.');
      }
      setSuccess('');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mx-auto max-w-md">
      <h2 className="text-2xl font-bold text-center my-8" id="signup_title">Sign Up</h2>
      {error && <p className="text-red-500 text-center text-base font-sans" id="signup_error">{error}</p>}
      {success && <p className="text-green-500 text-center text-base font-sans" id="signup_success">{success}</p>}
      <form onSubmit={handleSubmit(onSubmit)} className="bg-white p-6 rounded shadow-md" id="signup_form">
        <div className="mb-4">
          <label htmlFor="first_name" className="block text-gray-700">First Name</label>
          <input
            type="text"
            id="first_name"
            {...register('first_name', { required: 'First name is required' })}
            className={`w-full p-2 border rounded ${errors.first_name ? 'border-red-500' : ''}`}
          />
          {errors.first_name && <p className="text-red-500 text-base font-sans">{errors.first_name.message}</p>}
        </div>
        <div className="mb-4">
          <label htmlFor="last_name" className="block text-gray-700">Last Name</label>
          <input
            type="text"
            id="last_name"
            {...register('last_name', { required: 'Last name is required' })}
            className={`w-full p-2 border rounded ${errors.last_name ? 'border-red-500' : ''}`}
          />
          {errors.last_name && <p className="text-red-500 text-base font-sans">{errors.last_name.message}</p>}
        </div>
        <div className="mb-4">
          <label htmlFor="email" className="block text-gray-700">Email</label>
          <input
            type="email"
            id="email"
            {...register('email', {
              required: 'Email is required',
              pattern: { value: /^\S+@\S+$/i, message: 'Invalid email' }
            })}
            className={`w-full p-2 border rounded ${errors.email ? 'border-red-500' : ''}`}
          />
          {errors.email && <p className="text-red-500 text-base font-sans">{errors.email.message}</p>}
        </div>
        <div className="mb-4">
          <label htmlFor="country_code" className="block text-gray-700">Country Code</label>
          <select
            id="country_code"
            {...register('country_code')}
            className="w-full p-2 border rounded"
          >
            <option value="">Select Country Code</option>
            <option value="+1">+1 (USA)</option>
            <option value="+91">+91 (India)</option>
            <option value="+44">+44 (UK)</option>
          </select>
        </div>
        <div className="mb-4">
          <label htmlFor="contact_number" className="block text-gray-700">Contact Number</label>
          <input
            type="text"
            id="contact_number"
            {...register('contact_number', {
              pattern: { value: /^[0-9]*$/, message: 'Contact number must contain only digits.' },
              maxLength: { value: 15, message: 'Contact number must not exceed 15 digits.' }
            })}
            className={`w-full p-2 border rounded ${errors.contact_number ? 'border-red-500' : ''}`}
          />
          {errors.contact_number && <p className="text-red-500 text-base font-sans">{errors.contact_number.message}</p>}
        </div>
        <div className="mb-4">
          <label htmlFor="password" className="block text-gray-700">Password</label>
          <input
            type="password"
            id="password"
            {...register('password', {
              required: 'Password is required',
              minLength: { value: 6, message: 'Password must be at least 6 characters' }
            })}
            className={`w-full p-2 border rounded ${errors.password ? 'border-red-500' : ''}`}
          />
          {errors.password && <p className="text-red-500 text-base font-sans">{errors.password.message}</p>}
        </div>
        <div className="mb-4">
          <label className="flex items-center">
            <input
              type="checkbox"
              {...register('subscribe_newsletter')}
              className="mr-2"
              defaultChecked={true}
            />
            Subscribe to our newsletter
          </label>
        </div>
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded w-full"
          id="signup_submit"
          disabled={loading}
        >
          {loading ? <ClipLoader size={20} color="#fff" /> : 'Sign Up'}
        </button>
      </form>
    </div>
  );
};

export default Signup;
