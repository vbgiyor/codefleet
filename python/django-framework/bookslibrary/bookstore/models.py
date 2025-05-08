from django.db import models


class Book(models.Model):
    title = models.CharField(max_length=200)  # Required
    author = models.CharField(max_length=100)  # Required
    isbn = models.CharField(max_length=13, unique=False, null=True, blank=True)
    price = models.DecimalField(max_digits=10, decimal_places=2, null=True, blank=True)
    stock = models.PositiveIntegerField(null=True, blank=True)
    published_date = models.DateField(null=True, blank=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return f"Book {self.title} by {self.author}"
