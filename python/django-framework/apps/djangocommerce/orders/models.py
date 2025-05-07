from django.db import models


class Order(models.Model):
    user_id = models.IntegerField()
    book_id = models.IntegerField()
    quantity = models.PositiveIntegerField()
    total_price = models.DecimalField(max_digits=10, decimal_places=2)
    created_at = models.DateTimeField(auto_now_add=True)

    class Meta:
        app_label = 'orders'

    def __str__(self):
        return f"Order {self.id} by User {self.user_id}"
