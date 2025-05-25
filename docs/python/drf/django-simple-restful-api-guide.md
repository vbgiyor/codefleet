# 🌟 Building a Simple To-Do List API with Django REST Framework

This guide shows you how to create a RESTful API for a **To-Do List** application using **Django REST Framework (DRF)**. We'll build an API to manage `Todo` items, allowing users to create, read, update, and delete tasks. This is a practical example for beginners to understand API development in Django.

## 📋 Prerequisites
- **Django** and **Django REST Framework** installed.
- A Django app named `todos` with the following configuration:

```python
# todos/apps.py
from django.apps import AppConfig

class TodosConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'todos'
```

- A `Todo` model defined in `todos/models.py` (example below):

```python
# todos/models.py
from django.db import models

class Todo(models.Model):
    title = models.CharField(max_length=200)
    description = models.TextField(blank=True)
    completed = models.BooleanField(default=False)
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.title
```

## 🚀 Steps to Build the API

### 1️⃣ Install Django REST Framework
Install DRF if not already present:

```bash
pip install djangorestframework
```

Add it to `INSTALLED_APPS` in your project’s settings:

```python
# project/settings.py
INSTALLED_APPS = [
    ...
    'rest_framework',
    'todos.apps.TodosConfig',
    ...
]
```

### 2️⃣ Create a Serializer
Serializers convert model data to JSON. Create a serializer for the `Todo` model:

```python
# todos/serializers.py
from rest_framework import serializers
from .models import Todo

class TodoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Todo
        fields = ['id', 'title', 'description', 'completed', 'created_at']
```

### 3️⃣ Define a ViewSet
Use a DRF ViewSet to define API endpoints for CRUD operations:

```python
# todos/views.py
from rest_framework import viewsets
from .models import Todo
from .serializers import TodoSerializer

class TodoViewSet(viewsets.ModelViewSet):
    queryset = Todo.objects.all()
    serializer_class = TodoSerializer
```

### 4️⃣ Configure URLs
Set up API routes in `todos/urls.py`:

```python
# todos/urls.py
from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import TodoViewSet

router = DefaultRouter()
router.register(r'todos', TodoViewSet)

urlpatterns = [
    path('', include(router.urls)),
]
```

Include the app’s URLs in the project’s `urls.py`:

```python
# project/urls.py
from django.contrib import admin
from django.urls import path, include

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/', include('todos.urls')),
]
```

### 5️⃣ Run Migrations
Apply the database schema for the `Todo` model:

```bash
python manage.py makemigrations
python manage.py migrate
```

### 6️⃣ Test the API
Start the Django development server:

```bash
python manage.py runserver
```

Access the API endpoints:
- 🌐 **List all todos**: `GET /api/todos/`
- 📤 **Create a todo**: `POST /api/todos/` (e.g., `{"title": "Buy groceries", "description": "Milk, eggs, bread", "completed": false}`)
- 🔍 **Retrieve a todo**: `GET /api/todos/<id>/`
- ✏️ **Update a todo**: `PUT /api/todos/<id>/` (e.g., `{"title": "Buy groceries", "description": "Milk, eggs", "completed": true}`)
- 🗑️ **Delete a todo**: `DELETE /api/todos/<id>/`

Test using **Postman**, **curl**, or DRF’s browsable API (available at the endpoint URLs in your browser).

## 🔒 Security Notes
- **Authentication**: In production, secure your API with DRF’s `permission_classes` (e.g., `IsAuthenticated`).
- **Permissions**: Restrict endpoints (e.g., limit `DELETE` to admin users with `IsAdminUser`).

## 🛠️ Workflow Summary
1. Define the `Todo` model in `todos/models.py`.
2. Set up DRF with serializers, views, and URLs.
3. Run migrations to create the database schema.
4. Test the API to ensure it works as expected.

## 💡 Pro Tips
- Use **DRF’s browsable API** for quick testing during development.
- Add serializer validation (e.g., `required=True` for `title`) to ensure data integrity.
- Enable pagination in `ViewSet` for large lists (e.g., `pagination_class` in DRF settings).
- Create a superuser (`python manage.py createsuperuser`) to test the Django admin interface.

Happy API development! 🚀

*Last Updated: May 25, 2025*
