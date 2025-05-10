from django.test import TestCase
from rest_framework.test import APIClient
from django.urls import reverse


class SignupViewTest(TestCase):
    def setUp(self):
        self.client = APIClient()

    def test_signup_success(self):
        response = self.client.post(reverse('api_signup'), {
            'first_name': 'Test',
            'email': 'test@example.com',
            'password': 'test123',
            'subscribe_newsletter': True
        }, format='json')
        self.assertEqual(response.status_code, 201)
        self.assertEqual(response.data['message'], 'User created successfully')
