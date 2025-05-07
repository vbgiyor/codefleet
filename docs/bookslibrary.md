# Bookslibrary Project

## Overview
The `bookslibrary` project is a Django-based REST API for managing a bookstore. It allows CRUD operations on books via the `/api/books/` endpoint.

## Project Configuration
- **Location**: `python/django-framework/apps/bookslibrary`
- **Main App**: `bookstore`
- **Settings**: `core/settings/base.py` (uses `SERVICE_TYPE=bookslibrary`)
- **Dependencies**: Listed in `python/django-framework/requirements/base.txt`
- **Database**: PostgreSQL (`codefleet_bookslibrary`)
- **Endpoints**:
  - Admin: `http://localhost:8000/admin/`
  - API: `http://localhost:8000/api/books/`

## How to Run

### Using Docker
1. Navigate to project directory:
   ```bash
   cd python/django-framework
   ```
2. Build and start services:
   ```bash
   docker-compose down --volumes
   docker-compose up --build -d
   ```
3. Apply migrations:
   ```bash
   docker-compose exec web python apps/bookslibrary/manage.py makemigrations bookstore --settings=core.settings.base
   docker-compose exec web python apps/bookslibrary/manage.py migrate --settings=core.settings.base
   ```
4. Create superuser (optional):
   ```bash
   docker-compose exec web python apps/bookslibrary/manage.py createsuperuser
   ```
5. Access:
   - Admin: `http://localhost:8000/admin/`
   - API: `http://localhost:8000/api/books/`
6. Stop:
   ```bash
   docker-compose down
   ```

### Without Docker
1. Ensure PostgreSQL is running and create a database:
   ```bash
   psql -U postgres -c "CREATE DATABASE codefleet_bookslibrary;"
   ```
2. Navigate to project directory:
   ```bash
   cd python/django-framework
   ```
3. Install dependencies:
   ```bash
   pip install -r requirements/base.txt
   ```
4. Set environment variables:
   ```bash
   export SERVICE_TYPE=bookslibrary
   export DATABASE_URL=postgres://user:password@localhost:5432/codefleet_bookslibrary
   export PYTHONPATH=$(pwd)
   ```
5. Apply migrations:
   ```bash
   python apps/bookslibrary/manage.py makemigrations bookstore --settings=core.settings.base
   python apps/bookslibrary/manage.py migrate --settings=core.settings.base
   ```
6. Create superuser (optional):
   ```bash
   python apps/bookslibrary/manage.py createsuperuser
   ```
7. Run server:
   ```bash
   python apps/bookslibrary/manage.py runserver --settings=core.settings.base
   ```
8. Access:
   - Admin: `http://localhost:8000/admin/`
   - API: `http://localhost:8000/api/books/`

## Project Details
- **Framework**: Django 3.2, Django REST Framework
- **Models**: `Book` (title, author, price)
- **Tests**: Located in `apps/bookslibrary/bookstore/tests.py`
- **CI**: Configured in `.github/workflows/django_ci.yml`
- **Environment**: Uses `python-decouple` for configuration