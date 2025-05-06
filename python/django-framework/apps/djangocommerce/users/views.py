import logging
from rest_framework import viewsets
from .models import User
from .serializers import UserSerializer

logger = logging.getLogger(__name__)

class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer

    def get_queryset(self):
        logger.debug("Fetching User queryset")
        try:
            return super().get_queryset()
        except Exception as e:
            logger.error(f"Error in get_queryset: {str(e)}")
            raise