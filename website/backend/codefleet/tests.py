from django.test import TestCase
from rest_framework.test import APIClient
from django.urls import reverse


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
        self.assertEqual(response.data['message'], 'Signup successful! Welcome email sent.')

    def test_signup_success_without_newsletter(self):
        response = self.client.post(reverse('api_signup'), {
            'first_name': 'Test',
            'email': 'test2@example.com',
            'password': 'test123',
            'subscribe_newsletter': False
        }, format='json')
        self.assertEqual(response.status_code, 201)
        self.assertEqual(response.data['message'], 'Signup successful!')
