from rest_framework import serializers
from .models import Book


class BookSerializer(serializers.ModelSerializer):
    class Meta:
        model = Book
        fields = ['id', 'title', 'author', 'published_date', 'isbn',
                  'price', 'stock']
        extra_kwargs = {
            'published_date': {'required': False, 'allow_null': True},
            'isbn': {'required': False, 'allow_blank': True},
            'price': {'required': False, 'allow_null': True},
            'stock': {'required': False, 'allow_null': True},
        }
