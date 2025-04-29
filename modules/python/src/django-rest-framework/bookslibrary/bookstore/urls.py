from django.urls import path    
from .views import BookListCreateViewSet, BookUpdateDeleteViewSet

urlpatterns = [
    path('books/', BookListCreateViewSet.as_view(), name='book-list'),
    path('books/<int:pk>', BookUpdateDeleteViewSet.as_view(), name='book-details')
]