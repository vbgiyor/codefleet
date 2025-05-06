from django.contrib import admin
from django.urls import path, include
from django.conf import settings
from django.conf.urls.static import static
from decouple import config

# Determine the service type
SERVICE_TYPE = config('SERVICE_TYPE', default='bookslibrary')

# Base URL patterns
urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/orders/', include('apps.djangocommerce.orders.urls')),
    path('api/users/', include('apps.djangocommerce.users.urls')),
]

# Add bookslibrary URLs only for bookslibrary service
if SERVICE_TYPE == 'bookslibrary':
    urlpatterns.append(
        path('api/books/', include('apps.bookslibrary.bookstore.urls'))
    )

urlpatterns += static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)