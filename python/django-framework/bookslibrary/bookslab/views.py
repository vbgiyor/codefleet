# from django.http import JsonResponse

# def home(request):
#     return JsonResponse({"message": "Welcome to the Booklab API"})

from .models import Book
from .serializers import BookListSerializer, BookCreateUpdateSerializer
from rest_framework import generics
from rest_framework import status
from rest_framework.response import Response


class BookListCreateUpdateView(generics.RetrieveUpdateAPIView, generics.ListCreateAPIView):
    """
    API view to retrieve, create, update, and delete books.
    """
    queryset = Book.objects.all()

    def get_serializer_class(self):
        """
        Return the appropriate serializer class based on the request method.
        """
        if self.request.method in ("GET", "DELETE"):
            return BookListSerializer
        return BookCreateUpdateSerializer

    def get(self, request, *args, **kwargs):
        """
        Retrieve a list of books.
        Only return title, author, price and published date.
        """
        books = Book.objects.all().values('id', 'title', 'author', 'price', 'published_date')
        return Response(books, status=status.HTTP_200_OK)

    def post(self, request, *args, **kwargs):
        """
        Create a new book with mandatory fields: title, author, price, isbn.
        """
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        book = serializer.save()
        return Response({"id": book.id, **serializer.data},  status=status.HTTP_201_CREATED)

    def put(self, request, *args, **kwargs):
        """
        Update an existing book.
        """
        try:
            book = Book.objects.get(pk=kwargs.get("pk"))
        except Book.DoesNotExist:
            return Response({"error": "Book not found."}, status=status.HTTP_404_NOT_FOUND)

        serializer = self.get_serializer(book, data=request.data)
        serializer.is_valid(raise_exception=True)
        book = serializer.save()
        return Response({"id": book.id, **serializer.data}, status=status.HTTP_200_OK)


class BookDeleteView(generics.DestroyAPIView):
    """
    API view to delete a book by its ID.
    """
    queryset = Book.objects.all()

    def delete(self, request, *args, **kwargs):
        """
        Delete a book by its ID.
        """
        try:
            book = Book.objects.get(pk=kwargs.get("pk"))
            book.delete()
            return Response({"message": "Book deleted successfully."}, status=status.HTTP_200_OK)
        except Book.DoesNotExist:
            return Response({"error": "Book not found."}, status=status.HTTP_404_NOT_FOUND)
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
