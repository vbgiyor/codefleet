from django.db import models

class UserProfile(models.Model):
    user_name = models.CharField(max_length=100)
    email = models.EmailField(max_length=254, null=True, blank=True)
    phone = models.CharField(max_length=15, null=True, blank=True)
    birth_date = models.DateField(null=True, blank=True)
    address = models.CharField(max_length=200, null=True, blank=True)

    # city = models.CharField(max_length=254, null=True, blank=True)
    # pincode = models.IntegerField(null=True, blank=True)
    # tehsil = models.CharField(max_length=15, null=True, blank=True)
    gender = models.CharField(max_length=10, null=True, blank=True)

    class Meta:
        db_table = 'userprofile'
        verbose_name = 'User Profile'
        verbose_name_plural = 'User Profiles'
    
    def __str__(self):
        return f"{self.user_name}'s Profile"
