#!/bin/sh

echo "Applying database migrations..."
python manage.py migrate

echo "Loading initial data..."
python manage.py loaddata bookslab/initial_data.json

echo "Starting Gunicorn server..."
exec gunicorn bookslab.wsgi:application --bind 0.0.0.0:8000
