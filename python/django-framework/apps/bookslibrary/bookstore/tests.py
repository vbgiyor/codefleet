from django.test import TestCase
from .models import Book


class BookModelTest(TestCase):
    def test_create_book(self):
        book = Book.objects.create(title="Test Book", author="Author",
                                   price=29.99)
        self.assertEqual(book.title, "Test Book")
        self.assertEqual(book.price, 29.99)
