from django.test import TestCase
from .models import Order


class OrderModelTest(TestCase):
    def test_create_order(self):
        order = Order.objects.create(user_id=1, book_id=1, quantity=2,
                                     total_price=59.98)
        self.assertEqual(order.quantity, 2)
        self.assertEqual(order.total_price, 59.98)
