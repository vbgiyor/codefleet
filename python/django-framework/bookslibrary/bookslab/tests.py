from django.test import TestCase
from django.utils import timezone
from .models import Book


class BookModelTest(TestCase):

    def setUp(self):
        self.book1 = Book.objects.create(
            title='Test Book',
            author='Author A',
            isbn='1234567890123',
            price=10.99,
            description='A sample book for testing.',
        )

        self.book2 = Book.objects.create(
            title='Another Book',
            author='Author B',
            published_date=timezone.now(),
            isbn='9876543210987',
            price=15.50,
            description='Another book for testing.'
        )

    def test_book_creation(self):
        """Test that the books are correctly created."""
        self.assertEqual(Book.objects.count(), 2)
        self.assertEqual(self.book1.title, 'Test Book')
        self.assertEqual(self.book2.title, 'Another Book')

    def test_book_str_method(self):
        """Test the string representation of the book."""
        self.assertEqual(str(self.book1), 'Test Book by Author A')
        self.assertEqual(str(self.book2), 'Another Book by Author B')

    def test_book_default_published_date(self):
        """Test the default value of published_date."""
        # For book1, it should be the auto-set current time (timezone.now)
        self.assertIsNotNone(self.book1.published_date)
        self.assertTrue(timezone.now() >= self.book1.published_date)

    def test_book_fields(self):
        """Test field values of the book."""
        self.assertEqual(self.book1.isbn, '1234567890123')
        self.assertEqual(self.book1.price, 10.99)
        self.assertEqual(self.book1.description, 'A sample book for testing.')

    def test_book_updated_at_auto_update(self):
        """Test that updated_at is automatically updated."""
        old_updated_at = self.book1.updated_at
        self.book1.title = "Updated Title"
        self.book1.save()
        self.book1.refresh_from_db()
        self.assertNotEqual(old_updated_at, self.book1.updated_at)
