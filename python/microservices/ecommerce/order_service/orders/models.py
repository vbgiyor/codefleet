from django.db import models


class Order(models.Model):
    user_id = models.IntegerField()
    product = models.CharField(max_length=100)
    quantity = models.IntegerField()
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"Order {self.id} for {self.product}"
