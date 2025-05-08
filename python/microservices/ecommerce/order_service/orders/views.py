import logging
from rest_framework import viewsets
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.permissions import AllowAny
from .models import Order
from .serializers import OrderSerializer

logger = logging.getLogger(__name__)


class OrderViewSet(viewsets.ModelViewSet):
    queryset = Order.objects.all()
    serializer_class = OrderSerializer

    @action(detail=False, methods=['get'], url_path='user_details/(?P<user_id>\d+)', permission_classes=[AllowAny])
    def user_details(self, request, user_id=None):
        logger.info(f"Fetching orders for user_id: {user_id}")
        try:
            orders = Order.objects.filter(user_id=user_id)
            if not orders.exists():
                logger.warning(f"No orders found for user_id: {user_id}")
                return Response({'error': 'No orders found for this user'}, status=404)
            serializer = self.get_serializer(orders, many=True)
            logger.info(f"Successfully retrieved {len(orders)} orders for user_id: {user_id}")
            return Response(serializer.data)
        except Exception as e:
            logger.error(f"Error fetching orders for user_id {user_id}: {str(e)}")
            return Response({'error': str(e)}, status=500)
