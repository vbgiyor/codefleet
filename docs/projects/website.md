# Codefleet Website Documentation

## Overview
The Codefleet website is a full-stack web application built with a Django backend and a React frontend. It provides a platform for users to learn about programming (Java, Python), CI/CD, and automation. Key features include user authentication (signup, login, password reset, Basic Auth), a contact form, and responsive navigation.

## Project Structure

### Backend (`website/backend/`)
- **Framework**: Django with Django REST Framework (DRF)
- **Key Files**:
  - `codefleet/models.py`: Defines the `UserProfile` model for user data.
  - `codefleet/serializers.py`: Handles data validation and serialization for signup, login, contact, password reset, and Basic Auth.
  - `codefleet/views.py`: API views for signup, login, contact, password reset, user profile retrieval, and Basic Auth.
  - `codefleet/urls.py`: API endpoint routing, including `/basicauth/`.
  - `codefleet/email_utils.py`: Utility functions for sending emails (e.g., signup confirmation, contact form).
  - `codefleet/tests.py`: Unit tests for API endpoints and Basic Auth.
  - `docker-compose.yaml`: Configures PostgreSQL and Redis services.
  - `static/`: Contains static assets like `favicon.ico` and logos.
  - `templates/emails/`: HTML templates for emails (e.g., `contact_email.html`, `signup_email.html`).

### Frontend (`website/frontend/`)
- **Framework**: React with Tailwind CSS
- **Key Files**:
  - `src/App.jsx`: Main app component with routing.
  - `src/components/Header.jsx`: Navigation bar with dynamic Account button ("Hello <First Name>" after login).
  - `src/components/Footer.jsx`: Footer with logo, company name, and copyright notice.
  - `src/pages/SignIn.jsx`: Login page with email/password form and forgot password functionality.
  - `src/pages/Signup.jsx`: Registration page with form validation.
  - `src/pages/ResetPassword.jsx`: Password reset confirmation page.
  - `src/pages/Home.jsx`, `Java.jsx`, `Python.jsx`, `Automation.jsx`, `Contact.jsx`: Static content pages.
  - `src/pages/list/BasicAuth.jsx`: Basic Auth success page.
  - `build/`: Contains compiled React output (e.g., `index.html`, `static/`).

## Setup Instructions

### Prerequisites
- Docker and Docker Compose (for backend services)
- Node.js and npm (for frontend)
- Python 3.9+ (for backend)
- Local `logs/` directory for persistent logging

### Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd website/backend
   ```
2. Create a local `logs/` directory for logging:
   ```bash
   mkdir -p logs
   ```
3. Create a virtual environment and install dependencies:
   ```bash
   python -m venv venv
   source venv/bin/activate  # On Windows: venv\Scripts\activate
   pip install -r requirements.txt
   ```
4. Start PostgreSQL and Redis using Docker:
   ```bash
   docker-compose up -d
   ```
5. Apply migrations:
   ```bash
   python manage.py makemigrations
   python manage.py migrate
   ```
6. Create a superuser (optional, for admin access):
   ```bash
   python manage.py createsuperuser
   ```
7. Run the Django development server:
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
  - Sends a confirmation email if subscribed to newsletter.
- **Login (`/signin`)**:
  - Form fields: Email, Password.
  - On success, stores JWT tokens in `localStorage`, updates Account button to "Hello <First Name>", redirects to `/` after 2 seconds.
  - Displays "Sign Out" in Account dropdown when logged in.
- **Sign Out**:
  - Available in Account dropdown when logged in.
  - Clears JWT tokens and `first_name` from `localStorage`, updates Account button to "Account", redirects to `/`.
- **Password Reset**:
  - Forgot Password link on `/signin` sends a reset email.
  - Reset link redirects to `/reset-password/<uid>/<token>/` for new password (min 8 characters, alphanumeric).
- **Basic Auth (`/basicauth/`)**:
  - Requires `admin:admin` credentials via browser pop-up.
  - Displays success page (`BasicAuth.jsx`) on valid authentication.

### Contact Form (`/contact`)
- Form fields: Name, Email, Message.
- Sends an email to the admin with the user’s message.

### Navigation
- **Header**:
  - Links: Home, Java, Python, Automation, Contact, Account (dropdown).
  - Account button shows "Hello <First Name>" after login, with consistent height (`h-10`).
  - Dropdown shows "Sign In" and "Sign Up" when not logged in, "Sign Out" when logged in.
- **Footer**:
  - Displays Codefleet logo, company name "Codefleet", and "© 2025 Codefleet. All rights reserved."

## API Endpoints
- `POST /api/signup/`: Register a new user.
- `POST /api/login/`: Authenticate and return JWT tokens.
- `POST /api/contact/`: Submit a contact form message.
- `POST /api/password-reset/`: Request a password reset email.
- `POST /api/password-reset-confirm/`: Confirm password reset with a new password.
- `GET /api/user/`: Retrieve user profile details for authenticated users.
- `GET|POST /basicauth/`: Basic Auth endpoint (requires `admin:admin`).

## Testing Guidelines

### Backend Testing
1. Start the backend server and services:
   ```bash
   cd website/backend
   docker-compose up -d
   python manage.py runserver
   ```
2. Run unit tests:
   ```bash
   python manage.py test
   ```
3. Test API endpoints using Postman or curl:
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
     - Test invalid contact number (e.g., "abc123"): Expect 400 Bad Request.
   - **Login**: `POST http://localhost:8000/api/login/`
     ```json
     {
       "email": "john.doe@example.com",
       "password": "password123"
     }
     ```
     - Expect: 200 OK, JWT tokens and first name.
   - **Contact**: `POST http://localhost:8000/api/contact/`
     ```json
     {
       "name": "John Doe",
       "email": "john.doe@example.com",
       "message": "Hello!"
     }
     ```
     - Expect: 200 OK, "Message sent successfully".
   - **Basic Auth**: `GET http://localhost:8000/basicauth/`
     - Without credentials: Expect 401 with `WWW-Authenticate: Basic`.
     - With `admin:admin`: Expect 200 with HTML.

### Frontend Testing
1. Start the frontend server:
   ```bash
   cd website/frontend
   npm start
   ```
2. Test features in the browser (`http://localhost:3000`):
   - **Signup**:
     - Go to `/signup`, fill form, submit.
     - Verify "Subscribe to newsletter" is checked by default.
     - Test invalid inputs (e.g., contact number "abc123").
     - On success, confirm redirect to `/signin` and success message.
   - **Sign In**:
     - Go to `/signin`, log in with valid credentials.
     - Verify Account button updates to "<First Name>" without refresh.
     - Check dropdown shows "Sign Out".
   - **Sign Out**:
     - Click "Sign Out" in Account dropdown.
     - Confirm redirect to `/`, Account button reverts to "Account".
   - **Basic Auth**:
     - Go to `/basicauth/`, enter `admin:admin` in pop-up.
     - Verify success page ("Congratulations! You have successfully authenticated").
   - **Footer**:
     - Verify footer displays logo, "Codefleet", and "© 2025 Codefleet. All rights reserved."

## Known Issues
- Static pages (`/java`, `/python`, `/automation`) are placeholders and need content.
- Email functionality requires a configured email server in `settings.py`.
- Password reset success message is incorrect.
- CI pipeline occasionally fails with `TemplateDoesNotExist` for `index.html`.

## Recent Changes
- Added Basic Auth feature (`/basicauth/`) with `admin:admin` credentials, rendering `BasicAuth.jsx` on success.
- Added Django unit tests for Basic Auth in `codefleet/tests.py`.
- Updated CI pipeline (`website_and_cfinspector_ci.yml`) to run Django tests.

## Next Steps
- Implement content for `/java`, `/python`, `/automation` pages.
- Configure production email server.
- Deploy to a hosting service (e.g., AWS, Azure).

*Last Updated: May 15, 2025*