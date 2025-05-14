import React, { useState } from 'react';
import axios from 'axios';
import { ClipLoader } from 'react-spinners';
import { useForm } from 'react-hook-form';
import { useParams, Link } from 'react-router-dom';

const ResetPassword = () => {
  const [setEmail] = useState('');
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const { uid, token } = useParams();

  const onSubmit = async (data) => {
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await axios.post('http://localhost:8000/api/password-reset-confirm/', {
        new_password: data.new_password,
        uid,
        token
      });
      setSuccess(response.data.message);
      setError('');
      setEmail('');
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
      <h2 className="text-2xl font-bold text-center my-8" id="reset_password_title">
        Reset Password
      </h2>
      {error && (
        <p className="text-red-500 text-center text-base font-sans" id="reset_password_error">
          {error}
        </p>
      )}
      {success && (
        <p className="text-green-500 text-center text-base font-sans" id="reset_password_success">
          {success}
        </p>
      )}
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="bg-white p-6 rounded shadow-md"
        id="reset_password_form"
      >
        <div className="mb-4">
          <label htmlFor="new_password" className="block text-gray-700">
            New Password
          </label>
          <input
            type="password"
            id="new_password"
            {...register('new_password', {
              required: 'Password is required',
              pattern: {
                value: /^[a-zA-Z0-9]{8,}$/,
                message: 'Password must be at least 8 characters and contain only alphabets and numbers.'
              }
            })}
            className={`w-full p-2 border rounded ${errors.new_password ? 'border-red-500' : ''}`}
          />
          {errors.new_password && (
            <p className="text-red-500 text-base font-sans">
              {errors.new_password.message}
            </p>
          )}
        </div>
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded w-full"
          id="reset_password_submit"
          disabled={loading}
        >
          {loading ? <ClipLoader size={20} color="#fff" /> : 'Reset Password'}
        </button>
        {success && (
          <p className="text-center mt-4">
            <Link to="/signin" className="text-blue-600 hover:underline">
              Sign In
            </Link>
          </p>
        )}
      </form>
    </div>
  );
};

export default ResetPassword;