#!/bin/bash

python3 -m ensurepip
python3 -m pip install --upgrade pip
python3 -m pip install -r /app/website/backend/requirements.txt
cd /app/website/backend
python3 manage.py migrate
exec python3 manage.py runserver 0.0.0.0:8000