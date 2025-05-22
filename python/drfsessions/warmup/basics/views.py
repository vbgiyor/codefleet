from django.shortcuts import render

# Create your views here.

def drf_sessions_view(request):
    return render(request, 'drfsessions.html')
