# This file is part of the DRF Sessions project.

from django.db import models

# This class contains a class that falsly simulates to create Django model.

class InvalidDjangoModel:

    """
    A class that simulates a large auto field.

    This is just a placeholder to demonstrate the concept.
    """

    id = models.BigAutoField(primary_key=True)

    def __init__(self, value):
        """
        Initialize a InvalidDjangoModel instance.

        Args:
            value: The value to initialize the instance with.

        """
        self.value = value

    def __repr__(self):
        """
        Return a string representation of the InvalidDjangoModel instance.

        Returns:
            str: A string in the format 'InvalidDjangoModel(value=<value>)'.

        """
        return f"InvalidDjangoModel(value={self.value})"
