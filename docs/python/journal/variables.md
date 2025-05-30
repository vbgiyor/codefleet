# Variables in Python for DRF Development

In Django REST Framework (DRF), variables are used just like in standard Python — to store data that your application can use and manipulate. Understanding variables is fundamental to working with APIs, handling requests, manipulating data, and responding with serialized outputs.

---

## 🧠 What is a Variable?

A variable is a name that refers to a value. You can think of a variable as a labeled container for data. In Python, you don't need to declare the type of a variable explicitly — Python figures it out at runtime.

---

## 📝 Syntax

```python
variable_name = value
```

### Examples:

```python
name = "Django"
version = 3.2
is_active = True
```

---

## 🔄 Reassignment

Variables can be reassigned to different values or even different data types:

```python
count = 10
count = "ten"  # Now the type of 'count' is str
```

---

## 🧪 Variables in a DRF Context

While working with Django REST Framework, you'll frequently use variables to:

- Store request data
- Hold serialized data
- Pass context to serializers or views
- Handle logic in views or serializers

### Example 1: Storing Request Data

```python
from rest_framework.views import APIView
from rest_framework.response import Response

class HelloView(APIView):
    def get(self, request):
        user_name = request.query_params.get('name', 'Guest')  # Variable usage
        message = f"Hello, {user_name}!"  # Variable usage
        return Response({'message': message})
```

### Example 2: Using Variables in Serializers

```python
from rest_framework import serializers

class BookSerializer(serializers.Serializer):
    title = serializers.CharField()
    author = serializers.CharField()

    def validate_title(self, value):
        title_upper = value.upper()  # Variable usage
        if 'DJANGO' not in title_upper:
            raise serializers.ValidationError("Title must contain 'DJANGO'")
        return value
```

---

## ✅ Summary

- Variables store data that can be used in your DRF views and serializers.
- Variable names should be descriptive.
- Variables help manage logic cleanly and understandably.

---

## 🧪 Quick Practice

1. Create a variable that stores your name.
2. Create another variable that stores your age.
3. Write a DRF view that returns both in the API response.

```python
class InfoView(APIView):
    def get(self, request):
        name = "Alex"
        age = 28
        return Response({'name': name, 'age': age})
```

---
