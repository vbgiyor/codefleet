from django.core.mail import send_mail
from django.conf import settings
from django.template.loader import render_to_string


def send_signup_email(email, first_name):
    subject = 'Welcome to Codefleet!'
    message = render_to_string('emails/signup_email.html', {'first_name': first_name})
    send_mail(subject, '', settings.DEFAULT_FROM_EMAIL, [email], html_message=message)


def send_contact_email(email, opinion):
    subject = 'Thank You for Your Feedback!'
    message = render_to_string('emails/contact_email.html', {'opinion': opinion})
    send_mail(subject, '', settings.DEFAULT_FROM_EMAIL, [email], html_message=message)
