from django.urls import path, include
from rest_framework import routers
from .views import OrderViewSet

router = routers.DefaultRouter()
router.register(r'orders', OrderViewSet)

urlpatterns = [
    path('', include(router.urls)),
]