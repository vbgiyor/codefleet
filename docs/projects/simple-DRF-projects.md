# Simple Django Rest Framework (DRF) Projects for Beginners

Below are five simple Django Rest Framework (DRF) projects tailored for beginners who have completed a foundational Python learning plan. These projects apply Python basics (data structures, functions, classes, file handling) to DRF concepts like models, serializers, views, and API endpoints. Each project is designed to be achievable within a few hours to a couple of days.

## 1. Task Management API
**Description**: Build a simple API to manage tasks, allowing users to create, read, update, and delete (CRUD) tasks. This project reinforces DRF’s serializers, views, and models, which are core to DRF development.

**Features**:
- Create a task with a title, description, and status (e.g., pending/completed).
- List all tasks or filter by status.
- Update or delete a task by ID.

**Learning Outcomes**:
- Understand DRF models and migrations.
- Use serializers to validate and format data.
- Implement class-based views with DRF’s generic views.

**Steps**:
1. Set up a Django project and install DRF.
2. Create a `Task` model with fields: `title` (str), `description` (str), `status` (bool or choice field).
3. Define a `TaskSerializer` to handle data serialization.
4. Use DRF’s `ListCreateAPIView` and `RetrieveUpdateDestroyAPIView` for endpoints (`/tasks/` and `/tasks/<id>/`).
5. Test endpoints using tools like Postman or curl.

**Estimated Time**: 6-8 hours  
**Resources**: DRF official tutorial (https://www.django-rest-framework.org/), Django model documentation.

## 2. Book Library API
**Description**: Create an API to manage a collection of books, allowing users to add, view, update, or delete book records. This project emphasizes working with JSON data and API filtering.

**Features**:
- Add a book with title, author, and publication year.
- Retrieve a list of books or a single book by ID.
- Filter books by author or year.
- Update or delete books.

**Learning Outcomes**:
- Practice DRF serializers for JSON handling.
- Implement filtering with DRF’s built-in tools.
- Use Django’s ORM for querying data.

**Steps**:
1. Create a Django app with a `Book` model (`title`, `author`, `year`).
2. Set up a `BookSerializer` to convert model data to JSON.
3. Create views using `ModelViewSet` for full CRUD functionality.
4. Add filtering using DRF’s `SearchFilter` or `django-filter`.
5. Test with sample data via API calls.

**Estimated Time**: 5-7 hours  
**Resources**: DRF filtering guide, Django ORM basics.

## 3. Simple Blog Post API
**Description**: Build an API for managing blog posts, where users can create and view posts. This introduces authentication basics, as you can optionally restrict post creation to authenticated users.

**Features**:
- Create a post with title, content, and author.
- List all posts or retrieve a single post.
- (Optional) Restrict post creation to logged-in users.

**Learning Outcomes**:
- Learn DRF’s authentication and permissions.
- Work with foreign keys in models (e.g., linking posts to users).
- Use DRF’s pagination for listing posts.

**Steps**:
1. Set up a Django project with DRF and a `Post` model (`title`, `content`, `author` as ForeignKey to User).
2. Create a `PostSerializer` for data handling.
3. Use `GenericAPIView` or `ModelViewSet` for endpoints.
4. (Optional) Add token authentication using DRF’s `TokenAuthentication`.
5. Test endpoints with authentication headers.

**Estimated Time**: 8-10 hours (with authentication)  
**Resources**: DRF authentication docs, Django user model guide.

## 4. Expense Tracker API
**Description**: Develop an API to track personal expenses, allowing users to log, view, and categorize expenses. This project focuses on handling numerical data and basic aggregations.

**Features**:
- Log an expense with amount, category (e.g., food, travel), and date.
- List expenses or filter by category/date.
- Calculate total expenses for a category.

**Learning Outcomes**:
- Use DRF to handle numerical data and aggregations.
- Implement custom serializer methods for calculations.
- Practice Django ORM for filtering and aggregating.

**Steps**:
1. Create an `Expense` model (`amount` as float, `category` as choice field, `date`).
2. Define an `ExpenseSerializer` with a custom method to compute totals.
3. Set up views for CRUD operations and filtering by category/date.
4. Use Django’s `aggregate()` for summing expenses.
5. Test with sample expense data.

**Estimated Time**: 6-8 hours  
**Resources**: Django ORM aggregation docs, DRF serializer fields.

## 5. User Profile API
**Description**: Create an API to manage user profiles, allowing users to create and update their profile information. This project introduces nested serializers and user-related endpoints.

**Features**:
- Create a profile with name, email, and bio.
- Retrieve or update a user’s profile.
- List all profiles (public data only).

**Learning Outcomes**:
- Work with nested serializers for related models.
- Handle user-related data securely.
- Use DRF’s permission classes to restrict access.

**Steps**:
1. Create a `Profile` model linked to Django’s `User` model via OneToOneField.
2. Set up a `ProfileSerializer` with nested user data.
3. Implement views for profile creation, retrieval, and updates.
4. Add `IsAuthenticated` permission to restrict updates to the profile owner.
5. Test with sample profiles and authentication.

**Estimated Time**: 7-9 hours  
**Resources**: DRF permissions guide, Django OneToOneField docs.

## General Tips
- **Tools**: Use VS Code, Postman (or curl) for testing APIs, and SQLite (Django’s default) for simplicity.
- **Resources**: Refer to the DRF official documentation (https://www.django-rest-framework.org/) and Django’s documentation for models and ORM.
- **Practice**: After each project, test endpoints thoroughly and try adding one extra feature (e.g., pagination, sorting) to deepen your understanding.
- **Next Steps**: Once comfortable, explore advanced DRF topics like authentication (JWT), throttling, or deploying APIs to platforms like Heroku.

These projects align with your Python skills by leveraging classes (for models/views), dictionaries (for JSON), and virtual environments (for project setup). Start with the Task Management API for a straightforward introduction, then progress to others based on your interest.