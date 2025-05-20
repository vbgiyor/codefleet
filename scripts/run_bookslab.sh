#!/bin/bash
set -e

echo -e "Starting Bookslab app..."
cd  python/django-framework/bookslibrary/

echo -e "Rebuilding Docker containers..."

docker-compose down
docker-compose build --no-cache
docker-compose up -d

echo -e "Collecting static files..."
docker-compose exec $(docker compose config --services | grep "web") python manage.py collectstatic --noinput

echo -e "Build complete! Access the app at http://localhost:8000"