from django.urls import path
from django.views.generic import TemplateView
from rest_framework_simplejwt.views import TokenObtainPairView, TokenRefreshView
from codefleet.views import SignupView, LoginView, ContactView, PasswordResetView, PasswordResetConfirmView, UserProfileView
from django.conf import settings
from django.conf.urls.static import static

urlpatterns = [
    path('api/signup/', SignupView.as_view(), name='api_signup'),
    path('api/login/', LoginView.as_view(), name='api_login'),
    path('api/contact/', ContactView.as_view(), name='api_contact'),
    path('api/password-reset/', PasswordResetView.as_view(), name='password_reset'),
    path('api/password-reset-confirm/', PasswordResetConfirmView.as_view(), name='password_reset_confirm'),
    path('api/user/', UserProfileView.as_view(), name='user_profile'),
    path('api/token/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('', TemplateView.as_view(template_name='index.html')),
]

if settings.DEBUG:
    urlpatterns += static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)
