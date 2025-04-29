from rest_framework import generics
from .models import Books
from .serializers import BookSerializer

class BookListCreateViewSet(generics.ListCreateAPIView):
    queryset = Books.objects.all()
    serializer_class = BookSerializer

class BookUpdateDeleteViewSet(generics.RetrieveUpdateDestroyAPIView):
    queryset = Books.objects.all()
    serializer_class = BookSerializer
