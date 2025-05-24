#!/bin/bash
set -e

open -a Docker
echo  "Starting Docker..."
sleep 5
echo "Checking if Docker is running..."
if ! docker info > /dev/null 2>&1; then
    echo "Docker is not running. Please start Docker and try again."
    exit 1
fi
echo "Docker is running."
echo "Pulling latest Docker images..."

echo "Rebuilding Codefleet Docker containers..."
cd website/backend
docker-compose down
docker-compose build --no-cache
docker-compose up -d

echo "Building React frontend..."
cd ../frontend
npm install
npm run build

echo "Copying built frontend to backend static files..."
cd ../backend
echo "Collecting static files..."
docker-compose exec codefleet python manage.py collectstatic --noinput

echo "Build complete! Access the app at http://localhost:8000"