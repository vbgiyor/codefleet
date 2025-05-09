from rest_framework import serializers
from codefleet.models import UserProfile
from django.contrib.auth.models import User
from django.contrib.auth import authenticate


class UserProfileSerializer(serializers.ModelSerializer):
    country_code = serializers.ChoiceField(choices=[('+1', '+1 (USA)'), ('+91', '+91 (India)'), ('+44', '+44 (UK)')], required=False)
    password = serializers.CharField(write_only=True)

    class Meta:
        model = UserProfile
        fields = ['first_name', 'last_name', 'email', 'country_code', 'contact_number', 'subscribe_newsletter', 'password']

    def validate_email(self, value):
        if UserProfile.objects.filter(email=value).exists():
            raise serializers.ValidationError("This email is already registered.")
        return value

    def create(self, validated_data):
        password = validated_data.pop('password')
        user = User.objects.create_user(username=validated_data['email'], email=validated_data['email'], password=password)
        user_profile = UserProfile.objects.create(user=user, **validated_data)
        return user_profile


class LoginSerializer(serializers.Serializer):
    email = serializers.EmailField()
    password = serializers.CharField(write_only=True)

    def validate(self, data):
        user = authenticate(username=data['email'], password=data['password'])
        if user is None:
            raise serializers.ValidationError("Invalid email or password.")
        return data


class ContactSerializer(serializers.Serializer):
    email = serializers.EmailField()
    opinion = serializers.CharField()
