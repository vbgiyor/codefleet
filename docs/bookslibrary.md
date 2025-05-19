
# Bookslab - Books Library Management API

## Project Overview
Bookslab is a Django-based REST API application that allows users to manage a collection of books. The API provides endpoints for creating, updating, retrieving, and deleting books along with data attributes like title, author, published date, ISBN, price, and description.

The project is structured using a modular architecture and supports running both in Docker and non-Docker environments. Additionally, the application uses PostgreSQL as the database backend.

## Setup and Run Instructions

### 1. Prerequisites:
- Python 3.11
- Docker & Docker Compose
- PostgreSQL

### 2. Project Structure:
```
bookslibrary/
│── bookslab/
│   ├── __init__.py
│   ├── apps.py
│   ├── initial_data.json
│   ├── models.py
│   ├── serializers.py
│   ├── settings.py
│   ├── tests.py
│   ├── urls.py
│   ├── views.py
│   └── wsgi.py
│── docker-compose.yml
│── Dockerfile
│── manage.py
│── requirements.txt
```

### 3. Setup and Run (Non-Docker)

**Step 1: Create and activate virtual environment:**

```bash
cd bookslibrary
python3 -m venv env
source env/bin/activate
```

**Step 2: Install dependencies:**

```bash
pip install -r requirements.txt
```

**Step 3: Update `bookslab/settings.py` with local PostgreSQL database config:**

```python
DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql',
        'NAME': 'codefleet_bookslibrary',
        'USER': 'user',
        'PASSWORD': 'password',
        'HOST': 'localhost',
        'PORT': '5432',
    }
}
```

**Step 4: Apply migrations and load initial data:**

```bash
python manage.py makemigrations
python manage.py migrate
python manage.py loaddata bookslab/initial_data.json
```

**Step 5: Run the server:**

```bash
python manage.py runserver
```

### 4. Setup and Run (Docker)

**Step 1: Build and run containers:**

```bash
docker-compose up --build -d
```

**Step 2: Apply migrations and load initial data:**

```bash
docker-compose exec web python manage.py migrate
docker-compose exec web python manage.py loaddata bookslab/initial_data.json
```

**Step 3: Access the application:**

- API Base URL: http://localhost:8000/api/
- Admin Panel: http://localhost:8000/admin

### 5. Database Backup & Restore (Docker)

**Backup:**

```bash
docker-compose exec db pg_dump -U user codefleet_bookslibrary > backup.sql
```

**Restore:**

```bash
docker-compose exec -T db psql -U user codefleet_bookslibrary < backup.sql
```

## API Documentation

Base URL: http://localhost:8000/api/

### 1. Get All Books
- Endpoint: `/api/books/`
- Method: GET

Response:

```json
[
    {
        "id": 1,
        "title": "Book 1",
        "author": "Author 1",
        "price": "10.99",
        "published_date": "2025-05-19T10:00:00Z"
    },
    {
        "id": 2,
        "title": "Book 2",
        "author": "Author 2",
        "price": "12.50",
        "published_date": "2025-05-20T11:00:00Z"
    }
]
```

### 2. Create a New Book
- Endpoint: `/api/books/`
- Method: POST

Payload:

```json
{
    "title": "New Book",
    "author": "New Author",
    "isbn": "1234567890123",
    "price": 15.99
}
```

Response:

```json
{
    "id": 3,
    "title": "New Book",
    "author": "New Author",
    "price": "15.99",
    "published_date": "2025-05-19T10:00:00Z"
}
```

### 3. Update a Book
- Endpoint: `/api/books/<book_id>/`
- Method: PUT

Payload:

```json
{
    "title": "Updated Book",
    "author": "Updated Author",
    "isbn": "1234567890123",
    "price": 18.99
}
```

Response:

```json
{
    "id": 3,
    "title": "Updated Book",
    "author": "Updated Author",
    "price": "18.99",
    "published_date": "2025-05-19T10:00:00Z"
}
```

### 4. Delete a Book
- Endpoint: `/api/book/remove/<book_id>/`
- Method: DELETE

Response:

```json
{
    "message": "Book deleted successfully."
}
```

## 5. Admin Access
To access the admin panel:

Create a superuser:

```bash
docker-compose exec web python manage.py createsuperuser
```

Visit: http://localhost:8000/admin

## 6. Running Tests

**Non-Docker:**

```bash
python manage.py test
```

**Docker:**

```bash
docker-compose exec web python manage.py test
```

## 7. Code Linting
Run lint checks using flake8:

```bash
pip install flake8
flake8 bookslab --max-line-length=120 --exclude=migrations
```