# Djangocommerce Project

## Overview
The `djangocommerce` project is a Django-based REST API with two services: `order_service` (manages orders) and `user_service` (manages users). It supports CRUD operations via `/api/orders/orders/` and `/api/users/users/`.

## Project Configuration
- **Location**: `python/django-framework/apps/djangocommerce`
- **Apps**: `orders`, `users`
- **Services**:
  - `order_service`: Port 8001
  - `user_service`: Port 8002
- **Settings**: `core/settings/base.py` (uses `SERVICE_TYPE=djangocommerce`)
- **Dependencies**: Listed in `python/django-framework/requirements/base.txt`
- **Database**: PostgreSQL (`codefleet_djangocommerce`)
- **Endpoints**:
  - Order Admin: `http://localhost:8001/admin/`
  - Order API: `http://localhost:8001/api/orders/orders/`
  - User Admin: `http://localhost:8002/admin/`
  - User API: `http://localhost:8002/api/users/users/`

## How to Run

### Using Docker
1. Navigate to project directory:
   ```bash
   cd python/django-framework/apps/djangocommerce
   ```
2. Build and start services:
   ```bash
   docker-compose down --volumes
   docker-compose up --build -d
   ```
3. Apply migrations:
   ```bash
   docker-compose exec order_service python apps/djangocommerce/manage.py makemigrations orders --settings=core.settings.base
   docker-compose exec order_service python apps/djangocommerce/manage.py makemigrations users --settings=core.settings.base
   docker-compose exec order_service python apps/djangocommerce/manage.py migrate --settings=core.settings.base
   docker-compose exec user_service python apps/djangocommerce/manage.py makemigrations users --settings=core.settings.base
   docker-compose exec user_service python apps/djangocommerce/manage.py migrate --settings=core.settings.base
   ```
4. Create superusers (optional):
   ```bash
   docker-compose exec order_service python apps/djangocommerce/manage.py createsuperuser
   docker-compose exec user_service python apps/djangocommerce/manage.py createsuperuser
   ```
5. Access:
   - Order Admin: `http://localhost:8001/admin/`
   - Order API: `http://localhost:8001/api/orders/orders/`
   - User Admin: `http://localhost:8002/admin/`
   - User API: `http://localhost:8002/api/users/users/`
6. Stop:
   ```bash
   docker-compose down
   ```

### Without Docker
1. Ensure PostgreSQL is running and create a database:
   ```bash
   psql -U postgres -c "CREATE DATABASE codefleet_djangocommerce;"
   ```
2. Navigate to project directory:
   ```bash
   cd python/django-framework/apps/djangocommerce
   ```
3. Install dependencies:
   ```bash
   pip install -r ../../requirements/base.txt
   ```
4. Set environment variables:
   ```bash
   export SERVICE_TYPE=djangocommerce
   export DATABASE_URL=postgres://user:password@localhost:5432/codefleet_djangocommerce
   export PYTHONPATH=$(pwd)/../..
   ```
5. Apply migrations:
   ```bash
   python manage.py makemigrations orders --settings=core.settings.base
   python manage.py makemigrations users --settings=core.settings.base
   python manage.py migrate --settings=core.settings.base
   ```
6. Create superuser (optional):
   ```bash
   python manage.py createsuperuser
   ```
7. Run order_service:
   ```bash
   python manage.py runserver 8001 --settings=core.settings.base
   ```
8. In another terminal, run user_service:
   ```bash
   cd python/django-framework/apps/djangocommerce
   python manage.py runserver 8002 --settings=core.settings.base
   ```
9. Access:
   - Order Admin: `http://localhost:8001/admin/`
   - Order API: `http://localhost:8001/api/orders/orders/`
   - User Admin: `http://localhost:8002/admin/`
   - User API: `http://localhost:8002/api/users/users/`

## Project Details
- **Framework**: Django 3.2, Django REST Framework
- **Models**:
  - `Order`: user_id, book_id, quantity, total_price
  - `User`: username, email
- **Tests**: Located in `apps/djangocommerce/orders/tests.py`, `apps/djangocommerce/users/tests.py`
- **CI**: Configured in `.github/workflows/django_ci.yml`
- **Environment**: Uses `python-decouple` for configuration