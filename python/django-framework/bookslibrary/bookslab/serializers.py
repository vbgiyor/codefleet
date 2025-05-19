from .models import Book
from rest_framework import serializers


class BookListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Book
        # For GET, expose only [title, author, price, published_date]
        fields = ['id', 'title', 'author', 'price', 'published_date']
        extra_kwargs = {
            'isbn': {'required': False},
            'description': {'required', False}
        }
        read_only_fields = ['published_date', 'created_at', 'updated_at']


class BookCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Book
        # For POST/PUT, expsose only [title, authon, price and isbn] fileds
        fields = ['id', 'title', 'author', 'price', 'isbn']
        extra_kwargs = {
            'description': {'required': False}
        }
        read_only_fields = ['id', 'published_date']
