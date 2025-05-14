#!/bin/bash
set -e

echo "Building React frontend..."
cd frontend
npm install
npm run build
cd ..

echo "Rebuilding Docker containers..."
cd backend
docker-compose down --volumes
docker-compose up --build -d

echo "Collecting static files..."
docker-compose exec codefleet python manage.py collectstatic --noinput

echo "Build complete! Access the app at http://localhost:8000"