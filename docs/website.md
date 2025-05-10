# Codefleet Website Documentation

## Overview
The Codefleet website is a full-stack web application built with a Django backend and a React frontend. It provides a platform for users to learn about programming (Java, Python), CI/CD, and automation. Key features include user authentication (signup, login, password reset), a contact form, and responsive navigation.

## Project Structure

### Backend (`website/backend/`)
- **Framework**: Django with Django REST Framework (DRF)
- **Key Files**:
  - `codefleet/models.py`: Defines the `UserProfile` model for user data.
  - `codefleet/serializers.py`: Handles data validation and serialization for signup, login, contact, and password reset.
  - `codefleet/views.py`: API views for signup, login, contact, password reset, and user profile retrieval.
  - `codefleet/urls.py`: API endpoint routing.
  - `codefleet/email_utils.py`: Utility functions for sending emails (e.g., signup confirmation, contact form).
  - `docker-compose.yml`: Configures PostgreSQL and Redis services for the backend.

### Frontend (`website/frontend/`)
- **Framework**: React with Tailwind CSS
- **Key Files**:
  - `src/App.jsx`: Main app component with routing.
  - `src/components/Header.jsx`: Navigation bar with dynamic Account button ("Hello <First Name>" after login).
  - `src/components/Footer.jsx`: Footer with logo, company name, and copyright notice.
  - `src/pages/SignIn.jsx`: Login page with email/password form and forgot password functionality.
  - `src/pages/Signup.jsx`: Registration page with form validation.
  - `src/pages/ResetPassword.jsx`: Password reset confirmation page.
  - `src/pages/Home.jsx`, `Java.jsx`, `Python.jsx`, `Cicd.jsx`, `Contact.jsx`: Static content pages (to be implemented).

## Setup Instructions

### Prerequisites
- Docker and Docker Compose (for backend services)
- Node.js and npm (for frontend)
- Python 3.9+ (for backend)

### Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd website/backend
   ```
2. Create a virtual environment and install dependencies:
   ```bash
   python -m venv venv
   source venv/bin/activate  # On Windows: venv\Scripts\activate
   pip install -r requirements.txt
   ```
3. Start PostgreSQL and Redis using Docker:
   ```bash
   docker-compose up -d
   ```
4. Apply migrations:
   ```bash
   python manage.py makemigrations
   python manage.py migrate
   ```
5. Create a superuser (optional, for admin access):
   ```bash
   python manage.py createsuperuser
   ```
6. Run the Django development server:
   ```bash
   python manage.py runserver
   ```

### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd website/frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the React development server:
   ```bash
   npm start
   ```
4. Access the app at `http://localhost:3000`.

## Features

### Authentication
- **Signup (`/signup`)**:
  - Form fields: First Name, Last Name, Email, Country Code, Contact Number, Password, Subscribe to Newsletter (checked by default).
  - Contact number validation: Must contain only digits, max 15 digits.
  - Email validation: Must be unique.
  - Password: Minimum 6 characters.
  - On success, redirects to `/signin` after 2 seconds.
  - Sends a confirmation email if the user subscribes to the newsletter.
- **Login (`/signin`)**:
  - Form fields: Email, Password.
  - On success, stores JWT tokens in `localStorage`, updates the Account button to "Hello <First Name>", and redirects to `/` after 2 seconds.
  - Displays "Sign Out" in the Account dropdown when logged in.
- **Sign Out**:
  - Available in the Account dropdown when logged in.
  - Clears JWT tokens and `first_name` from `localStorage`, updates the Account button to "Account", and redirects to `/`.
- **Password Reset**:
  - Forgot Password link on `/signin` sends a reset email.
  - Reset link redirects to `/reset-password/<uid>/<token>/`, where users can set a new password (min 8 characters, alphanumeric).

### Contact Form (`/contact`)
- Form fields: Name, Email, Message.
- Sends an email to the admin with the user’s message.

### Navigation
- **Header**:
  - Links: Home, Java, Python, CI/CD, Automation, Contact, Account (dropdown).
  - Account button displays "Hello <First Name>" after login, with consistent height (`h-10`) across all tabs.
  - Dropdown shows "Sign In" and "Sign Up" when not logged in, and "Sign Out" when logged in.
- **Footer**:
  - Displays the Codefleet logo , company name "Codefleet", and copyright notice "© 2025 Codefleet. All rights reserved."

## API Endpoints
- `POST /api/signup/`: Register a new user.
- `POST /api/login/`: Authenticate and return JWT tokens.
- `POST /api/contact/`: Submit a contact form message.
- `POST /api/password-reset/`: Request a password reset email.
- `POST /api/password-reset-confirm/`: Confirm password reset with a new password.
- `GET /api/user/`: Retrieve user profile details (e.g., first name) for authenticated users.

## Testing Guidelines

### Backend Testing
1. Start the backend server and services:
   ```bash
   cd website/backend
   docker-compose up -d
   python manage.py runserver
   ```
2. Test API endpoints using Postman or curl:
   - **Signup**: `POST http://localhost:8000/api/signup/`
     ```json
     {
       "first_name": "John",
       "last_name": "Doe",
       "email": "john.doe@example.com",
       "country_code": "+1",
       "contact_number": "1234567890",
       "password": "password123",
       "subscribe_newsletter": true
     }
     ```
     - Expect: 201 Created, "User created successfully".
     - Test invalid contact number (e.g., "abc123"): Expect 400 Bad Request, "Contact number must contain only digits."
   - **Login**: `POST http://localhost:8000/api/login/`
     ```json
     {
       "email": "john.doe@example.com",
       "password": "password123"
     }
     ```
     - Expect: 200 OK, JWT tokens and first name in response.
   - **Contact**: `POST http://localhost:8000/api/contact/`
     ```json
     {
       "name": "John Doe",
       "email": "john.doe@example.com",
       "message": "Hello!"
     }
     ```
     - Expect: 200 OK, "Message sent successfully".

### Frontend Testing
1. Start the frontend server:
   ```bash
   cd website/frontend
   npm start
   ```
2. Test features in the browser (`http://localhost:3000`):
   - **Signup**:
     - Go to `/signup`, fill out the form, and submit.
     - Verify the "Subscribe to newsletter" checkbox is checked by default.
     - Test invalid inputs (e.g., contact number "abc123", expect "Contact number must contain only digits").
     - On success, confirm redirect to `/signin` and success message.
   - **Sign In**:
     - Go to `/signin`, log in with valid credentials.
     - Verify the Account button updates to "<First Name>" immediately without refresh.
     - Check the dropdown shows "Sign Out".
   - **Sign Out**:
     - Click "Sign Out" in the Account dropdown.
     - Confirm redirect to `/`, Account button reverts to "Account", and dropdown shows "Sign In" and "Sign Up".
   - **Footer**:
     - Verify the footer displays the Codefleet logo, "Codefleet", and "© 2025 Codefleet. All rights reserved."
     - Ensure no navigation links are present in the footer.

## Known Issues
- Static pages (`/java`, `/python`, `/cicd`, `/automation`) are placeholders and need content.
- Email functionality requires a configured email server (e.g., Gmail SMTP) in `settings.py`.

## Next Steps
- Implement content for `/java`, `/python`, `/cicd`, and `/automation` pages.
- Set up a production email server for sending emails.
- Deploy the application to a hosting service (e.g., Heroku, AWS).

---

*Last Updated: May 10, 2025*