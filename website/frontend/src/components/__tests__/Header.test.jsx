import React from 'react';
  import { render, screen } from '@testing-library/react';
  import { MemoryRouter } from 'react-router-dom';
  import Header from '../Header';
  import '@testing-library/jest-dom';

  test('renders header with logo and navigation', () => {
    render(
      <MemoryRouter>
        <Header />
      </MemoryRouter>
    );
    expect(screen.getByText('Codefleet')).toBeInTheDocument();
    expect(screen.getByText('Sign Up')).toBeInTheDocument();
    expect(screen.getByText('Login')).toBeInTheDocument();
  });