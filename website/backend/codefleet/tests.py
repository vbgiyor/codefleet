from django.test import TestCase, Client
from rest_framework.test import APIClient
from django.urls import reverse
from django.contrib.auth.models import User
from .models import UserProfile
import base64


class SignupViewTest(TestCase):
    def setUp(self):
        self.client = APIClient()

    def test_signup_success_with_newsletter(self):
        response = self.client.post(reverse('api_signup'), {
            'first_name': 'Test',
            'email': 'test@example.com',
            'password': 'test123',
            'subscribe_newsletter': True
        }, format='json')
        self.assertEqual(response.status_code, 201)
        self.assertEqual(response.data['message'],
                         'Signup successful! Welcome email sent.')

    def test_signup_success_without_newsletter(self):
        response = self.client.post(reverse('api_signup'), {
            'first_name': 'Test',
            'email': 'test2@example.com',
            'password': 'test123',
            'subscribe_newsletter': False
        }, format='json')
        self.assertEqual(response.status_code, 201)
        self.assertEqual(response.data['message'], 'Signup successful!')


class BasicAuthViewTest(TestCase):
    def setUp(self):
        self.client = Client()
        self.url = reverse('basic_auth')

    def test_basic_auth_no_credentials(self):
        response = self.client.get(self.url)
        self.assertEqual(response.status_code, 401)
        self.assertEqual(response['WWW-Authenticate'], 'Basic realm="Restricted Area"')
        self.assertEqual(response.content.decode(), 'Unauthorized')

    def test_basic_auth_invalid_credentials(self):
        credentials = base64.b64encode(b'wrong:wrong').decode('utf-8')
        response = self.client.get(self.url, HTTP_AUTHORIZATION=f'Basic {credentials}')
        self.assertEqual(response.status_code, 401)
        self.assertEqual(response['WWW-Authenticate'], 'Basic realm="Restricted Area"')
        self.assertEqual(response.content.decode(), 'Unauthorized')

    def test_basic_auth_valid_credentials(self):
        credentials = base64.b64encode(b'admin:admin').decode('utf-8')
        response = self.client.get(self.url, HTTP_AUTHORIZATION=f'Basic {credentials}', HTTP_ACCEPT='text/html')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.content.decode(), 'Success: You are authenticated via Basic Auth.')

    def test_basic_auth_valid_credentials_json(self):
        credentials = base64.b64encode(b'admin:admin').decode('utf-8')
        response = self.client.get(self.url, HTTP_AUTHORIZATION=f'Basic {credentials}', HTTP_ACCEPT='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.content.decode(), 'Success: You are authenticated via Basic Auth.')

class UserProfileViewTest(TestCase):
    def setUp(self):
        self.client = Client()
        self.user = User.objects.create_user(username='test@example.com', email='test@example.com', password='testpass')
        self.user_profile = UserProfile.objects.create(
            user=self.user,
            first_name='Test',
            last_name='User',
            email='test@example.com',
            country_code='+1',
            contact_number='1234567890'
        )
        self.url = reverse('user_profile')

    def test_user_profile_authenticated(self):
        self.client.login(username='test@example.com', password='testpass')
        response = self.client.get(self.url)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()['first_name'], 'Test')
        self.assertEqual(response.json()['email'], 'test@example.com')

    def test_user_profile_unauthenticated(self):
        response = self.client.get(self.url)
        self.assertEqual(response.status_code, 403)
