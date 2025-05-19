#!/bin/sh

# Apply database migrations
python3 manage.py migrate

# Load initial data
python3 manage.py loaddata initial_data.json
