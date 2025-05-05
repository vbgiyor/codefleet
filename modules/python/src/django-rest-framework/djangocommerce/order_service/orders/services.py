# order_service/orders/services.py
import os
import requests # type: ignore

USER_SERVICE_URL = os.getenv("USER_SERVICE_URL", "http://user_service:8000")

def validate_user(user_id):
    try:
        res = requests.get(f"{USER_SERVICE_URL}/users/{user_id}/")
        return res.status_code == 200
    except requests.RequestException:
        return False
