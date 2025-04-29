from rest_framework import serializers
from .models import Books

class BookSerializer(serializers.ModelSerializer):
    class Meta:
        model = Books
        fields = ['id', 'title', 'author', 'published_date', 'language', 'isbn']

    def validate_isbn(self, value):
        book_instance = self.instance
        if book_instance:
            if book_instance.isbn == value:
                return value

        if Books.objects.filter(isbn=value).exists():
            raise serializers.ValidationError("A book with this ISBN already exists.")
        
        return value