from rest_framework import serializers
from .models import Order


class OrderSerializer(serializers.ModelSerializer):
    class Meta:
        model = Order
        fields = ['id', 'user_id', 'product', 'quantity', 'created_at']
