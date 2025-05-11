from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .serializers import UserProfileSerializer, ContactSerializer, PasswordResetSerializer, PasswordResetConfirmSerializer
from .models import UserProfile
from django.core.mail import send_mail
from django.conf import settings
from rest_framework.permissions import IsAuthenticated
from .email_utils import send_signup_email, send_contact_email
from django.http import HttpResponse
import base64
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.cache import never_cache


class SignupView(APIView):
    def post(self, request):
        serializer = UserProfileSerializer(data=request.data)
        if serializer.is_valid():
            user = serializer.save()

            # Check if user subscribed to newsletter and send welcome email
            subscribe_newsletter = request.data.get(
                'subscribe_newsletter', False)
            if subscribe_newsletter:
                send_signup_email(user.email, user.first_name)
                message = "Signup successful! Welcome email sent."
            else:
                message = "Signup successful!"

            return Response({
                'message': message,
                'first_name': user.first_name
            }, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class LoginView(APIView):
    def post(self, request):
        email = request.data.get('email')
        password = request.data.get('password')
        try:
            user = UserProfile.objects.get(email=email)
            if user.check_password(password):
                # For simplicity, returning a mock token
                return Response({
                    'access_token': 'mock-access-token',
                    'refresh_token': 'mock-refresh-token',
                    'first_name': user.first_name
                })
            return Response({'error': 'Invalid credentials'}, status=status.HTTP_401_UNAUTHORIZED)
        except UserProfile.DoesNotExist:
            return Response({'error': 'User not found'}, status=status.HTTP_404_NOT_FOUND)


class ContactView(APIView):
    def post(self, request):
        serializer = ContactSerializer(data=request.data)
        if serializer.is_valid():
            name = serializer.validated_data['name']
            email = serializer.validated_data['email']
            message = serializer.validated_data['message']
            send_contact_email(name, email, message)
            return Response({'message': 'Message sent successfully'}, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class PasswordResetView(APIView):
    def post(self, request):
        serializer = PasswordResetSerializer(data=request.data)
        if serializer.is_valid():
            email = serializer.validated_data['email']
            try:
                user = UserProfile.objects.get(email=email)
                # For simplicity, skipping actual token generation
                reset_link = f"http://localhost:3000/reset-password/mock-uid/mock-token/"
                send_mail(
                    'Password Reset Request',
                    f'Click the link to reset your password: {reset_link}',
                    settings.DEFAULT_FROM_EMAIL,
                    [email],
                    fail_silently=False,
                )
                return Response({'message': 'Password reset email sent'}, status=status.HTTP_200_OK)
            except UserProfile.DoesNotExist:
                return Response({'error': 'User not found'}, status=status.HTTP_404_NOT_FOUND)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class PasswordResetConfirmView(APIView):
    def post(self, request):
        serializer = PasswordResetConfirmSerializer(data=request.data)
        if serializer.is_valid():
            # For simplicity, assuming the token is valid
            email = request.data.get('email')
            new_password = serializer.validated_data['new_password']
            try:
                user = UserProfile.objects.get(email=email)
                user.set_password(new_password)
                user.save()
                return Response({'message': 'Password reset successful'}, status=status.HTTP_200_OK)
            except UserProfile.DoesNotExist:
                return Response({'error': 'User not found'}, status=status.HTTP_404_NOT_FOUND)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class UserProfileView(APIView):
    permission_classes = [IsAuthenticated]

    def get(self, request):
        user = request.user
        return Response({
            'first_name': user.first_name,
            'email': user.email
        })


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
        decoded_credentials = base64.b64decode(
            encoded_credentials).decode('utf-8')
        username, password = decoded_credentials.split(':')
    except (IndexError, UnicodeDecodeError, ValueError):
        response = HttpResponse('Invalid credentials', status=401)
        response['WWW-Authenticate'] = 'Basic realm="Restricted Area"'
        return response
    if username == 'admin' and password == 'admin':
        # Render the React app's index.html
        # Assumes React's build/index.html
        return render(request, 'index.html')
    else:
        response = HttpResponse('Unauthorized', status=401)
        response['WWW-Authenticate'] = 'Basic realm="Restricted Area"'
        return response
