# website/backend/codefleet/email_utils.py
from django.core.mail import send_mail

def send_signup_email(email, username):
    subject = 'Welcome to Codefleet!'
    body = f'Hi {username},\nThank you for signing up!'
    send_mail(
        subject,
        body,
        'codefleet0@gmail.com',  # Must match EMAIL_HOST_USER
        [email],
        fail_silently=False
    )

def send_contact_email(name, email, message):
    subject = f"Contact Form Submission from {name}"
    body = f"Name: {name}\nEmail: {email}\nMessage: {message}"
    send_mail(
        subject,
        body,
        'codefleet0@gmail.com',
        ['to@example.com'],
        fail_silently=False
    )