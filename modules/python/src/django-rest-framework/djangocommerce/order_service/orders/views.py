# order_service/orders/views.py

from rest_framework import viewsets
from .models import Order
from .serializers import OrderSerializer
from .services import validate_user  # optional
from rest_framework.response import Response

class OrderViewSet(viewsets.ModelViewSet):
    queryset = Order.objects.all()
    serializer_class = OrderSerializer

    def create(self, request, *args, **kwargs):
        user_id = request.data.get("user_id")
        if not validate_user(user_id):
            return Response({"error": "Invalid user"}, status=400)
        return super().create(request, *args, **kwargs)
