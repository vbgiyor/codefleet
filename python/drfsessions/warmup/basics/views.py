from django.shortcuts import render

# Create your views here.

def drf_sessions_view(request):
    """
    Render the DRF sessions view.

    Args:
        request: The HTTP request object.

    Returns:
        HttpResponse: The rendered HTML response for the drfsessions template.

    """
    return render(request, 'drfsessions.html')
