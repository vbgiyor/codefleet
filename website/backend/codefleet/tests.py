from django.test import TestCase, Client
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

    def test_basic_auth_no_credentials(self):
        response = self.client.get('/basicauth/')
        self.assertEqual(response.status_code, 401)
        self.assertEqual(response['WWW-Authenticate'], 'Basic realm="Restricted Area"')

    def test_basic_auth_valid_credentials(self):
        import base64
        credentials = base64.b64encode(b'admin:admin').decode('utf-8')
        response = self.client.get('/basicauth/', HTTP_AUTHORIZATION=f'Basic {credentials}')
        self.assertEqual(response.status_code, 200)
        # Check for content from index.html (e.g., a known string)
        self.assertContains(response, '<!DOCTYPE html>')
