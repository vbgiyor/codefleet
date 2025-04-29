from django.db import models

class Books(models.Model):
    title = models.CharField(max_length=100)
    author = models.CharField(max_length=100)
    published_date = models.DateField()
    isbn = models.CharField(max_length=13, unique=False)
    pages = models.IntegerField(null=True, blank=True)
    cover_image = models.URLField(null=True, blank=True)
    language = models.CharField(max_length=30)

    def __str__(self):
        return self.title 