# Website Project

## Overview
The `website` project is a React-based frontend for the Codefleet platform, serving as the user interface for interacting with backend APIs.

## Project Configuration
- **Location**: `website/frontend`
- **Framework**: React
- **Dependencies**: Defined in `website/frontend/package.json`
- **CI**: Configured in `.github/workflows/website_ci.yml`
- **Endpoints**:
  - Development: `http://localhost:3000`

## How to Run

### Using Docker
1. Navigate to project directory:
   ```bash
   cd website/frontend
   ```
2. Build Docker image:
   ```bash
   docker build -t codefleet-website .
   ```
3. Run container:
   ```bash
   docker run --rm -p 3000:3000 codefleet-website
   ```
4. Access:
   - App: `http://localhost:3000`

### Without Docker
1. Ensure Node.js and npm are installed.
2. Navigate to project directory:
   ```bash
   cd website/frontend
   ```
3. Install dependencies:
   ```bash
   npm install
   ```
4. Start development server:
   ```bash
   npm start
   ```
5. Access:
   - App: `http://localhost:3000`

## Project Details
- **Components**: `Navbar.jsx`
- **Pages**: `Home.jsx`
- **Tests**: Configured via Jest (if applicable)
- **Linting**: ESLint (configured in `package.json`)