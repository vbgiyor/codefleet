import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Header from '../Header';
import '@testing-library/jest-dom';

// Mock axios to prevent ESM issues
jest.mock('axios', () => ({
  get: jest.fn(),
}));

describe('Header Component', () => {
  test('renders logo and company name', () => {
    render(
      <MemoryRouter>
        <Header />
      </MemoryRouter>
    );
    expect(screen.getByAltText('Codefleet Logo')).toBeInTheDocument();
    expect(screen.getByText('Codefleet')).toBeInTheDocument();
  });

  test('renders navigation links', () => {
    render(
      <MemoryRouter>
        <Header />
      </MemoryRouter>
    );
    const navLinks = ['Home', 'Resources', 'Contact'];
    navLinks.forEach((link) => {
      expect(screen.getByText(link)).toBeInTheDocument();
    });
  });

  test('renders Resources dropdown with correct links', () => {
    render(
      <MemoryRouter>
        <Header />
      </MemoryRouter>
    );
    const ResourcesButton = screen.getByText('Resources');
    expect(ResourcesButton).toBeInTheDocument();

    // Click to open dropdown
    fireEvent.click(ResourcesButton);

    const dropdownLinks = ['Java', 'Python','Selenium', 'Docs'];
    dropdownLinks.forEach((link) => {
      expect(screen.getByText(link)).toBeInTheDocument();
    });
  });

  test('renders Account button with Sign In and Sign Up when not logged in', () => {
    render(
      <MemoryRouter>
        <Header />
      </MemoryRouter>
    );
    const accountButton = screen.getByText('Account');
    expect(accountButton).toBeInTheDocument();

    // Click to open dropdown
    fireEvent.click(accountButton);

    expect(screen.getByText('Sign In')).toBeInTheDocument();
    expect(screen.getByText('Sign Up')).toBeInTheDocument();
  });
});