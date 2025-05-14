from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .serializers import UserProfileSerializer, ContactSerializer, PasswordResetSerializer, PasswordResetConfirmSerializer, LoginSerializer
from .models import UserProfile
from django.contrib.auth.models import User
from django.core.mail import send_mail
from django.conf import settings
from rest_framework.permissions import IsAuthenticated
from .email_utils import send_signup_email, send_contact_email
from django.http import HttpResponse
import base64
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.cache import never_cache
from django.utils.decorators import method_decorator
import logging

# Set up logging
logger = logging.getLogger(__name__)


@method_decorator(csrf_exempt, name='dispatch')
class SignupView(APIView):
    def post(self, request):
        serializer = UserProfileSerializer(data=request.data)
        if serializer.is_valid():
            user = serializer.save()
            subscribe_newsletter = request.data.get('subscribe_newsletter', False)
            if subscribe_newsletter:
                try:
                    send_signup_email(user.email, user.first_name)
                    message = "Signup successful! Welcome email sent."
                except Exception as e:
                    message = f"Signup successful! Failed to send welcome email: {str(e)}"
            else:
                message = "Signup successful!"
            return Response({
                'message': message,
                'first_name': user.first_name
            }, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


@method_decorator(csrf_exempt, name='dispatch')
class LoginView(APIView):
    def post(self, request):
        serializer = LoginSerializer(data=request.data)
        if serializer.is_valid():
            email = serializer.validated_data['email']
            try:
                user_profile = UserProfile.objects.get(email=email)
                logger.info(f"Login successful for user: {email}")
                return Response({
                    'access_token': 'mock-access-token',
                    'refresh_token': 'mock-refresh-token',
                    'first_name': user_profile.first_name
                }, status=status.HTTP_200_OK)
            except UserProfile.DoesNotExist:
                logger.warning(f"User not found with email: {email}")
                return Response({'error': 'User not found'}, status=status.HTTP_404_NOT_FOUND)
        logger.warning(f"Invalid credentials for email: {request.data.get('email')}")
        return Response({'error': 'Invalid credentials'}, status=status.HTTP_401_UNAUTHORIZED)


@method_decorator(csrf_exempt, name='dispatch')
class ContactView(APIView):
    def post(self, request):
        serializer = ContactSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({'message': 'Message sent successfully'}, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


@method_decorator(csrf_exempt, name='dispatch')
class PasswordResetView(APIView):
    def post(self, request):
        serializer = PasswordResetSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({'message': 'Password reset email sent'}, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


@method_decorator(csrf_exempt, name='dispatch')
class PasswordResetConfirmView(APIView):
    def post(self, request):
        serializer = PasswordResetConfirmSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({'message': 'Password reset successful'}, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class UserProfileView(APIView):
    permission_classes = [IsAuthenticated]

    def get(self, request):
        user = request.user
        try:
            user_profile = UserProfile.objects.get(user=user)
            return Response({
                'first_name': user_profile.first_name,
                'email': user_profile.email
            })
        except UserProfile.DoesNotExist:
            return Response({'error': 'User profile not found'}, status=status.HTTP_404_NOT_FOUND)


@never_cache
@csrf_exempt
def basic_auth_view(request):
    auth_header = request.META.get('HTTP_AUTHORIZATION', '')
    if not auth_header.startswith('Basic '):
        response = HttpResponse('Unauthorized', status=401)
        response['WWW-Authenticate'] = 'Basic realm="Restricted Area"'
        return response
    try:
        encoded_credentials = auth_header.split(' ')[1]
        decoded_credentials = base64.b64decode(encoded_credentials).decode('utf-8')
        username, password = encoded_credentials.split(':')
    except (IndexError, UnicodeDecodeError, ValueError):
        response = HttpResponse('Invalid credentials', status=401)
        response['WWW-Authenticate'] = 'Basic realm="Restricted Area"'
        return response
    if username == 'admin' and password == 'admin':
        return render(request, 'index.html')
    else:
        response = HttpResponse('Unauthorized', status=401)
        response['WWW-Authenticate'] = 'Basic realm="Restricted Area"'
        return response
