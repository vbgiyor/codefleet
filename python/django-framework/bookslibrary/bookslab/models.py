from django.db import models
from django.utils import timezone


class Book(models.Model):
    id = models.AutoField(primary_key=True)
    title = models.CharField(max_length=255) #mandatory
    author = models.CharField(max_length=255) #mandatory
    published_date = models.DateTimeField(default=timezone.now, null=True, blank=True)
    isbn = models.CharField(max_length=13, unique=False, null=True, blank=True)
    price = models.DecimalField(max_digits=6, decimal_places=2)
    description = models.TextField(blank=True, null=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return f"{self.title} by {self.author}"
    