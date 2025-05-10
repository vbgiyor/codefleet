import React, { useState } from 'react';
import axios from 'axios';
import { ClipLoader } from 'react-spinners';
import { useForm } from 'react-hook-form';
import { useNavigate, Link } from 'react-router-dom';

const SignIn = () => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const [showForgotPassword, setShowForgotPassword] = useState(false);
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await axios.post('http://localhost:8000/api/login/', data);
      setSuccess(response.data.message);
      localStorage.setItem('access_token', response.data.access_token);
      localStorage.setItem('refresh_token', response.data.refresh_token);
      localStorage.setItem('first_name', response.data.first_name);
      // Trigger storage event to update Header
      window.dispatchEvent(new Event('storage'));
      setError('');
      setTimeout(() => {
        navigate('/');
      }, 2000);
    } catch (err) {
      console.log('Backend Error Response:', err.response?.data);
      const errorData = err.response?.data;
      if (errorData && typeof errorData === 'object') {
        const errorMessages = errorData.error ||
                             errorData.non_field_errors?.join(' ') ||
                             Object.entries(errorData)
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

  const onForgotPasswordSubmit = async (data) => {
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await axios.post('http://localhost:8000/api/password-reset/', data);
      setSuccess(response.data.message);
      setError('');
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
      <h2 className="text-2xl font-bold text-center my-8" id="signin_title">Sign In</h2>
      {error && <p className="text-red-500 text-center text-base font-sans" id="signin_error">{error}</p>}
      {success && <p className="text-green-500 text-center text-base font-sans" id="signin_success">{success}</p>}
      {!showForgotPassword ? (
        <form onSubmit={handleSubmit(onSubmit)} className="bg-white p-6 rounded shadow-md" id="signin_form">
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
          <button
            type="submit"
            className="bg-blue-600 text-white px-4 py-2 rounded w-full"
            id="signin_submit"
            disabled={loading}
          >
            {loading ? <ClipLoader size={20} color="#fff" /> : 'Sign In'}
          </button>
          <p className="text-center mt-4">
            <button
              type="button"
              className="text-blue-600 hover:underline"
              onClick={() => setShowForgotPassword(true)}
            >
              Forgot Password?
            </button>
          </p>
        </form>
      ) : (
        <form onSubmit={handleSubmit(onForgotPasswordSubmit)} className="bg-white p-6 rounded shadow-md" id="forgot_password_form">
          <div className="mb-4">
            <label htmlFor="email" className="block text-gray-700">Registered Email</label>
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
          <button
            type="submit"
            className="bg-blue-600 text-white px-4 py-2 rounded w-full"
            id="forgot_password_submit"
            disabled={loading}
          >
            {loading ? <ClipLoader size={20} color="#fff" /> : 'Submit'}
          </button>
          <p className="text-center mt-4">
            <Link to="/signin" className="text-blue-600 hover:underline">
              Sign In
            </Link>
          </p>
        </form>
      )}
    </div>
  );
};

export default SignIn;