from django.db import models

class Order(models.Model):
    user_id = models.IntegerField()  # Foreign key reference by ID (not FK constraint)
    product = models.CharField(max_length=255)
    quantity = models.PositiveIntegerField()
    created_at = models.DateTimeField(auto_now_add=True)
