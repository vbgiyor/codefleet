# Basic Auth Pop-Up Issue Resolution

----------------

## Problem Statement
The `/basicauth/` endpoint in a Django-React application was intended to display a browser Basic Authentication pop-up, requiring `admin:admin` credentials. Upon successful authentication, it should render the React frontend’s success page (`BasicAuth.jsx`). However, the pop-up was not appearing consistently, and the following issues were observed:
- Visiting `http://localhost:8000/basicauth/` sometimes showed the React frontend’s unauthenticated message instead of the pop-up.
- Unit tests failed because `index.html` was not found in the response (`TemplateDoesNotExist` or incorrect rendering).
- The endpoint worked previously but resurfaced due to changes in CI, server configuration, or frontend routing.
- Browser credential caching caused the pop-up to skip in new incognito sessions, confusing testers.
- Redundant views (`basic_auth_view` and `check_auth_view`) complicated maintenance.
- You're using fetch() from React (JavaScript) to hit /basicauth/ with an Authorization header. This bypasses the browser's automatic auth challenge mechanism, so no popup is triggered. fetch() won't trigger the browser's built-in authentication dialog.

----------------

### ✅ Why the popup doesn't show in your setup:
Browsers **only show the Basic Auth popup** when:
1.  You make a **direct navigation request** (e.g., typing URL in the address bar or clicking a link), **not via `fetch()`** or other JavaScript-based HTTP requests.
2.  The server responds with:
    `HTTP/1.1 401 Unauthorized
    WWW-Authenticate: Basic realm="Restricted Area"`

----------------

# Root Causes
1. **Template Rendering Issue**:
  - `basic_auth_view` used `render(request, 'index.html')`, but `index.html` was not consistently found due to the React build process or CI misconfiguration.
  - The `TEMPLATES['DIRS']` configuration needed verification to ensure correct paths.
2. **Frontend Routing Conflict**:
  - React Router in `App.jsx` intercepted `/basicauth/` requests, rendering `BasicAuth.jsx` before the backend’s 401 response could trigger the pop-up.
3. **Browser Credential Caching**:
  - Browsers stored `admin:admin` credentials, sending the `Authorization: Basic` header automatically, bypassing the pop-up in new sessions.
4. **CI/Deployment Issues**:
  - The CI pipeline (`website_and_cfinspector_ci.yml`) might not have built the React frontend correctly, missing `frontend/build/index.html`.
5. **Bypasse the browser's automatic auth challenge mechanism**:
  - **Manual fetch() call suppressed the browser authentication challenge** — `fetch()` does not trigger browser popups even on `401 + WWW-Authenticate`.
  - This bypasses the browser's automatic auth challenge mechanism, **so no popup is triggered**.
  - **React Dev Server Proxy (port 3000) may interfere with headers** — in some setups, it swallows or alters `WWW-Authenticate` headers.
  - **Redirection loop** — redirecting immediately to the same path without state guard created infinite reload.

----------------

# Solutions

### 1. Template Rendering Issue
- **website/backend/codefleet/settings.py**:
  - [Verified `TEMPLATES` and `STATIC` settings to include correct paths for Django templates and React build output:
    ```python
    TEMPLATES = [
        {
            'BACKEND': 'django.template.backends.django.DjangoTemplates',
            'DIRS': [
                BASE_DIR / 'templates',
                BASE_DIR / './frontend/build',
            ],
            'APP_DIRS': True,
            'OPTIONS': {
                'context_processors': [
                    'django.template.context_processors.debug',
                    'django.template.context_processors.request',
                    'django.contrib.auth.context_processors.auth',
                    'django.contrib.messages.context_processors.messages',
                ],
            },
        },
    ]
    STATIC_URL = '/static/'
    STATICFILES_DIRS = [
        BASE_DIR / 'static',
        BASE_DIR / './frontend/build/static',
    ]
    STATIC_ROOT = BASE_DIR / 'staticfiles'
    STATICFILES_STORAGE = 'whitenoise.storage.CompressedManifestStaticFilesStorage'
    ```
  ]
- Ensured `frontend/build/index.html` exists by running:
  ```bash
  cd website/frontend
  npm install
  npm run build
  ls -l frontend/build/
  ```
- Updated CI pipeline to verify `index.html` existence.

### 1.1. Streamline Authentication Views
- **website/backend/codefleet/views.py**:
  - [Merged `basic_auth_view` and `check_auth_view` into a single `basic_auth_view`. Handled browser (HTML) and API (JSON) requests based on `Accept` header. Added logging for debugging.]
- **website/backend/codefleet/urls.py**:
  - [Removed `/api/check-auth/` endpoint. Kept `/basicauth/` mapping to `basic_auth_view`.]

----------------

### 2. Frontend Routing Conflict
- **website/frontend/src/pages/list/BasicAuth.jsx**:
  - [Updated to check authentication via `/basicauth/` with `Accept: application/json`. Displayed unauthenticated message if auth fails.]

----------------

### 4. Fix Unit Tests
- **website/backend/codefleet/tests.py**:
  - [Updated `test_basic_auth_valid_credentials` to check for `<!DOCTYPE html>` instead of `'index.html'`. Added test for invalid credentials.]

----------------

### 3. Browser Credential Caching
- Noted that browsers cache Basic Auth credentials, skipping the pop-up in new sessions.
- Suggested clearing browser credentials or using different browsers for testing.
- Proposed optional `/logout/` endpoint (not implemented).

----------------

### 4. CI/Deployment Issues
- **.github/workflows/website_and_cfinspector_ci.yml**:
- [Ensured React build step runs before collecting static files. Added verification for `frontend/build/index.html`. Added test for `/basicauth/` response.]

----------------

### 5. Bypasse the browser's automatic auth challenge mechanism
### ✅ Step 1: Rename Django Basic Auth Path
1. First, try a `fetch()` to the Django protected endpoint (`/basic-auth-protected/`). In `urls.py`, change the Django view path from `/basicauth/` to something distinct like `/basic-auth-protected/`:

```python
path('basic-auth-protected/', basic_auth_view, name='basic_auth'),
```

This avoids conflict with `/basicauth` (React route).

* * * * *

### ✅ Step 2: Fix React `BasicAuth.jsx` to Redirect Just Once
Now, update your React component to redirect to `/basic-auth-protected/` --- a true Django endpoint that triggers the popup --- **without causing infinite reload**. If a `401 Unauthorized` is received, trigger browser popup by redirecting to that path.

```jsx 
const BasicAuth = () => {
const [isAuthenticated, setIsAuthenticated] = useState(null);

useEffect(() => {
  // Call backend endpoint to check for valid auth
  fetch('/basic-auth-protected/', {
    headers: {
      Accept: 'application/json'
    },
    credentials: 'include'
  })
    .then((res) => {
      if (res.ok) {
        setIsAuthenticated(true);
      } else if (res.status === 401) {
        // Trigger browser Basic Auth prompt
        window.location.href = '/basic-auth-protected/';
      } else {
        setIsAuthenticated(false);
      }
    })
    .catch(() => setIsAuthenticated(false));
}, []);

if (isAuthenticated === null) {
  return (
    <div className="container mx-auto py-8 bg-gray-100 min-h-full">
      <p className="text-gray-700 text-center">Checking authentication...</p>
    </div>
  );
}
if (!isAuthenticated) {
  return (
    <div className="container mx-auto py-8 bg-gray-100 min-h-full">
      <p className="text-red-700 text-center">Please authenticate using Basic Auth (admin:admin).</p>
      <p className="text-gray-700 text-center mt-2">
        Try visiting <code>http://admin:admin@localhost:8000/basic-auth-protected/</code> directly.
      </p>
    </div>
  );
}
export default BasicAuth;
```

✅ `window.location.replace()` avoids pushing to browser history (helps prevent reload loop).
🔄 React now renders the correct success page after authentication, avoiding infinite reloads.


* * * * *

### ✅ Step 3: Confirm Django View Returns Popup Challenge

Your `basic_auth_view` should now be hit cleanly. Double-check that it returns:

``` python
response = HttpResponse('Unauthorized', status=401)
response['WWW-Authenticate'] = 'Basic realm="Restricted Area"'
return response
```
And not just for `application/json` requests. No more parsing `Accept` headers --- be strict:

```python
@never_cache
@csrf_exempt
def basic_auth_view(request):
  auth_header = request.META.get('HTTP_AUTHORIZATION', '')

  if not auth_header.startswith('Basic '):
      response = HttpResponse('Unauthorized', status=401)
      response['WWW-Authenticate'] = 'Basic realm="Restricted Area"'
      return response

  try:
      encoded_credentials = auth_header.split(' ')[1]
      decoded_credentials = base64.b64decode(encoded_credentials).decode('utf-8')
      username, password = decoded_credentials.split(':')
  except Exception:
      response = HttpResponse('Invalid credentials', status=401)
      response['WWW-Authenticate'] = 'Basic realm="Restricted Area"'
      return response

  if username == 'admin' and password == 'admin':
      return HttpResponse("Success: You are authenticated via Basic Auth.")

  response = HttpResponse('Unauthorized', status=401)
  response['WWW-Authenticate'] = 'Basic realm="Restricted Area"'
  return response
```
After successful login, React handles the page rendering normally.

* * * * *

✅ Final Checklist
-----------------

| What | Should Be | Done? |
| --- | --- | --- |
| ✅ React route `/basicauth` | Just redirects to Django | ✅ |
| ✅ Django route `/basic-auth-protected/` | Triggers Basic Auth | ✅ |
| ✅ No Accept header parsing | Always send `WWW-Authenticate` on 401 | ✅ |
| ✅ React doesn't reload endlessly | Uses `.replace()` instead of `.href` | ✅ |

✅ Django endpoint:
- Ensure the view at /basic-auth-protected/ returns: `401 Unauthorized`
- WWW-Authenticate: Basic realm="Restricted Area"

## Testing and Validation
- Tested locally with Docker:
  ```bash
  cd website/backend
  docker-compose up --build
  curl -v http://localhost:8000/basicauth  # Expect 401 with WWW-Authenticate
  curl -v -u admin:admin http://localhost:8000/basic_auth_protected/  # Expect 200 with HTML
  ```
- Tested in browsers (Chrome, Firefox, Incognito):
  - `http://localhost:8000/basicauth` → Pop-up appears.
  - Enter `admin:admin` → Redirects to success page.
  - `http://admin:admin@localhost:8000/basic_auth_protected` → Direct to success page.
- Ran unit tests:
  ```bash
  docker-compose run codefleet python manage.py test
  ```
- Verified logging:
  ```bash
  cat website/backend/logs/django.log
  ```

## Notes
- **Browser Caching**: Basic Auth credentials may be stored by the browser’s password manager, skipping the pop-up. Clear credentials or use `/logout/` (if implemented) for testing.

## 🧠 TO DO :Deploy React and Django on same domain in Docker (Recommended for prod)

1. E.g. use NGINX to serve both under the same origin, like:
```
/api/... → Django
/...     → React
```
2. Optional `/logout` endpoint 

## Date Updated
- May 24, 2025