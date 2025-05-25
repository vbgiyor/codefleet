from django.db import models


class ValidDjangoModel(models.Model):

    """
    A custom Django model that inherits from models.Model.

    This class serves as a base model for other models in the application.
    It includes a primary key field and a string representation method.
    """

    id = models.BigAutoField(primary_key=True)
    name = models.CharField(max_length=255, blank=True, null=True)
    class Meta:

        """
        Meta options for the class ValidDjangoModel(models.Model).

        This class defines the database table name and ordering for the model.
        """

        db_table = 'custom_django_model'
        ordering = ['id']

    def __init__(self, *args, **kwargs):
        """
        Initialize a class ValidDjangoModel(models.Model): instance.

        Args:
            *args: Variable length argument list.
            **kwargs: Arbitrary keyword arguments.

        """
        super().__init__(*args, **kwargs)


    def __repr__(self):
        """
        Return a string representation of the class ValidDjangoModel(models.Model) instance.

        Returns:
            str: A string in the format 'class ValidDjangoModel(models.Model):(id=<id>)'.

        """
        return f"CustomeDjangoModel(id={self.id})"
