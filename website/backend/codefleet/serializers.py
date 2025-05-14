from rest_framework import serializers
from django.contrib.auth.models import User
from django.core.mail import send_mail
from django.utils.http import urlsafe_base64_encode, urlsafe_base64_decode
from django.utils.encoding import force_bytes
from django.contrib.auth.tokens import default_token_generator
from django.contrib.auth import authenticate
from .models import UserProfile
from .email_utils import send_signup_email, send_contact_email


class UserProfileSerializer(serializers.ModelSerializer):
    country_code = serializers.ChoiceField(
        choices=[('+1', '+1 (USA)'), ('+91', '+91 (India)'),
                 ('+44', '+44 (UK)')],
        required=False
    )
    password = serializers.CharField(write_only=True)

    class Meta:
        model = UserProfile
        fields = ['first_name', 'last_name', 'email', 'country_code',
                  'contact_number', 'subscribe_newsletter', 'password']

    def validate_email(self, value):
        if UserProfile.objects.filter(email=value).exists():
            raise serializers.ValidationError(
                "This email is already registered.")
        return value

    def validate_contact_number(self, value):
        if value and not value.isdigit():
            raise serializers.ValidationError(
                "Contact number must contain only digits.")
        if value and len(value) > 15:
            raise serializers.ValidationError(
                "Contact number must not exceed 15 digits.")
        return value

    def create(self, validated_data):
        password = validated_data.pop('password')
        user = User.objects.create_user(
            username=validated_data['email'],
            email=validated_data['email'],
            password=password
        )
        user_profile = UserProfile.objects.create(
            user=user,
            **validated_data
        )
        if validated_data.get('subscribe_newsletter', False):
            send_signup_email(user.email, user.first_name)
        return user_profile


class ContactSerializer(serializers.Serializer):
    name = serializers.CharField(max_length=100)
    email = serializers.EmailField()
    message = serializers.CharField()

    def save(self):
        send_contact_email(
            self.validated_data['name'],
            self.validated_data['email'],
            self.validated_data['message']
        )


class LoginSerializer(serializers.Serializer):
    email = serializers.EmailField()
    password = serializers.CharField(write_only=True)

    def validate(self, data):
        user = authenticate(username=data['email'], password=data['password'])
        if user is None:
            raise serializers.ValidationError("Invalid email or password.")
        return data


class PasswordResetSerializer(serializers.Serializer):
    email = serializers.EmailField()

    def validate_email(self, value):
        return value

    def save(self):
        email = self.validated_data['email']
        if User.objects.filter(email=email).exists():
            user = User.objects.get(email=email)
            token = default_token_generator.make_token(user)
            uid = urlsafe_base64_encode(force_bytes(user.pk))
            reset_link = f"http://localhost:3000/reset-password/{uid}/{token}/"
            send_mail(
                'Password Reset Request',
                f'Click this link to reset your password: {reset_link}',
                'codefleet0@gmail.com',
                [email],
                fail_silently=False
            )


class PasswordResetConfirmSerializer(serializers.Serializer):
    new_password = serializers.CharField(write_only=True)
    uid = serializers.CharField()
    token = serializers.CharField()

    def validate_new_password(self, value):
        import re
        if not re.match(r'^[a-zA-Z0-9]{8,}$', value):
            raise serializers.ValidationError(
                "Password must be at least 8 characters and contain only alphabets and numbers."
            )
        return value

    def validate(self, data):
        try:
            uid = urlsafe_base64_decode(data['uid']).decode()
            user = User.objects.get(pk=uid)
        except (TypeError, ValueError, OverflowError, User.DoesNotExist):
            raise serializers.ValidationError("Invalid reset link.")
        if not default_token_generator.check_token(user, data['token']):
            raise serializers.ValidationError("Invalid or expired token.")
        return data

    def save(self):
        uid = urlsafe_base64_decode(self.validated_data['uid']).decode()
        user = User.objects.get(pk=uid)
        user.set_password(self.validated_data['new_password'])
        user.save()


class UserProfileDetailSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ['first_name']
