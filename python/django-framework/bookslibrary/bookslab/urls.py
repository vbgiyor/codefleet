from django.urls import path
from .views import BookListCreateUpdateView, BookDeleteView
from django.contrib import admin


urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/books/', BookListCreateUpdateView.as_view(), name='get-books'),
    path('api/books/<int:pk>/', BookListCreateUpdateView.as_view(), name='update-book'),
    path('api/book/remove/<int:pk>/', BookDeleteView.as_view(), name='remove-book'),
]
