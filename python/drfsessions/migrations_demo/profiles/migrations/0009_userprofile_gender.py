# Generated by Django 5.2.1 on 2025-05-28 06:45

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('profiles', '0008_userprofile_email'),
    ]

    operations = [
        migrations.AddField(
            model_name='userprofile',
            name='gender',
            field=models.CharField(blank=True, max_length=10, null=True),
        ),
    ]
