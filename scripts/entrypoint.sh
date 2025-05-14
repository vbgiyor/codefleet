#!/bin/bash

# Navigate to the frontend directory
cd /app/frontend

# Verify package.json exists
if [ ! -f "package.json" ]; then
    echo "Error: package.json not found in /app/frontend. Ensure the frontend directory is correctly mounted."
    exit 1
fi

# Install dependencies
echo "Running npm install..."
npm install --loglevel=verbose
if [ $? -ne 0 ]; then
    echo "Error: npm install failed. Check the logs above for details."
    exit 1
fi

# Build the React app
echo "Running npm run build..."
npm run build --loglevel=verbose
if [ $? -ne 0 ]; then
    echo "Error: npm run build failed. Check the logs above for details."
    exit 1
fi

# Verify build directory exists
if [ ! -d "build" ] || [ ! -f "build/index.html" ]; then
    echo "Error: React build failed to generate build/index.html."
    exit 1
fi

# Navigate back to the backend directory
cd /app

# Collect static files
echo "Running collectstatic..."
python manage.py collectstatic --noinput
if [ $? -ne 0 ]; then
    echo "Error: python manage.py collectstatic failed."
    exit 1
fi

# Start the Django server with Gunicorn
echo "Starting Gunicorn..."
exec gunicorn --bind 0.0.0.0:8000 codefleet.wsgi:application