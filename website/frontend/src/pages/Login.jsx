import React, { useState } from 'react';
import axios from 'axios';
import { ClipLoader } from 'react-spinners';
import { useForm } from 'react-hook-form';

const Login = () => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const onSubmit = async (data) => {
    setLoading(true);
    try {
      const response = await axios.post('http://localhost:8000/api/token/', data);
      localStorage.setItem('access_token', response.data.access);
      localStorage.setItem('refresh_token', response.data.refresh);
      setSuccess('Login successful!');
      setError('');
      window.location.href = '/';
    } catch (err) {
      setError(err.response?.data?.detail || 'An error occurred.');
      setSuccess('');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mx-auto max-w-md">
      <h2 className="text-2xl font-bold text-center my-8" id="login_title">Login</h2>
      {error && <p className="text-red-500 text-center" id="login_error">{error}</p>}
      {success && <p className="text-green-500 text-center" id="login_success">{success}</p>}
      <form onSubmit={handleSubmit(onSubmit)} className="bg-white p-6 rounded shadow-md" id="login_form">
        <div className="mb-4">
          <label htmlFor="email" className="block text-gray-700">Email</label>
          <input
            type="email"
            id="email"
            {...register('email', { required: 'Email is required', pattern: { value: /^\S+@\S+$/i, message: 'Invalid email' } })}
            className={`w-full p-2 border rounded ${errors.email ? 'border-red-500' : ''}`}
          />
          {errors.email && <p className="text-red-500">{errors.email.message}</p>}
        </div>
        <div className="mb-4">
          <label htmlFor="password" className="block text-gray-700">Password</label>
          <input
            type="password"
            id="password"
            {...register('password', { required: 'Password is required', minLength: { value: 6, message: 'Password must be at least 6 characters' } })}
            className={`w-full p-2 border rounded ${errors.password ? 'border-red-500' : ''}`}
          />
          {errors.password && <p className="text-red-500">{errors.password.message}</p>}
        </div>
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded w-full" id="login_submit" disabled={loading}>
          {loading ? <ClipLoader size={20} color="#fff" /> : 'Login'}
        </button>
      </form>
    </div>
  );
};

export default Login;