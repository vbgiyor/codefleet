# Basic Auth Pop-Up Issue Resolution

## Problem Statement
The `/basicauth/` endpoint in a Django-React application was intended to display a browser Basic Authentication pop-up, requiring `admin:admin` credentials. Upon successful authentication, it should render the React frontend’s success page (`BasicAuth.jsx`). However, the pop-up was not appearing consistently, and the following issues were observed:
- Visiting `http://localhost:8000/basicauth/` sometimes showed the React frontend’s unauthenticated message instead of the pop-up.
- Unit tests failed because `index.html` was not found in the response (`TemplateDoesNotExist` or incorrect rendering).
- The endpoint worked previously but resurfaced due to changes in CI, server configuration, or frontend routing.
- Browser credential caching caused the pop-up to skip in new incognito sessions, confusing testers.
- Redundant views (`basic_auth_view` and `check_auth_view`) complicated maintenance.

### Root Causes
1. **Template Rendering Issue**:
   - `basic_auth_view` used `render(request, 'index.html')`, but `index.html` was not consistently found due to the React build process or CI misconfiguration.
   - The `TEMPLATES['DIRS']` configuration needed verification to ensure correct paths.
2. **Frontend Routing Conflict**:
   - React Router in `App.jsx` intercepted `/basicauth/` requests, rendering `BasicAuth.jsx` before the backend’s 401 response could trigger the pop-up.
3. **Browser Credential Caching**:
   - Browsers stored `admin:admin` credentials, sending the `Authorization: Basic` header automatically, bypassing the pop-up in new sessions.
4. **Redundant Views**:
   - `basic_auth_view` (for browser) and `check_auth_view` (for frontend API) duplicated authentication logic.
5. **CI/Deployment Issues**:
   - The CI pipeline (`website_and_cfinspector_ci.yml`) might not have built the React frontend correctly, missing `frontend/build/index.html`.

## Solution

### 1. Verify Template and Static File Configuration
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

### 2. Streamline Authentication Views
- **website/backend/codefleet/views.py**:
  - [Merged `basic_auth_view` and `check_auth_view` into a single `basic_auth_view`. Handled browser (HTML) and API (JSON) requests based on `Accept` header. Added logging for debugging.]
- **website/backend/codefleet/urls.py**:
  - [Removed `/api/check-auth/` endpoint. Kept `/basicauth/` mapping to `basic_auth_view`.]

### 3. Update Frontend
- **website/frontend/src/pages/list/BasicAuth.jsx**:
  - [Updated to check authentication via `/basicauth/` with `Accept: application/json`. Displayed unauthenticated message if auth fails.]

### 4. Fix Unit Tests
- **website/backend/codefleet/tests.py**:
  - [Updated `test_basic_auth_valid_credentials` to check for `<!DOCTYPE html>` instead of `'index.html'`. Added test for invalid credentials.]

### 5. Handle Browser Credential Caching
- Noted that browsers cache Basic Auth credentials, skipping the pop-up in new sessions.
- Suggested clearing browser credentials or using different browsers for testing.
- Proposed optional `/logout/` endpoint (not implemented).

### 6. Update CI Pipeline
- **.github/workflows/website_and_cfinspector_ci.yml**:
  - [Ensured React build step runs before collecting static files. Added verification for `frontend/build/index.html`. Added test for `/basicauth/` response.]

## Logging Configuration Issue

### Issue
The logging configuration in `settings.py` caused a `FileNotFoundError` when attempting to write to `/app/logs/django.log`, resulting in a `ValueError: Unable to configure handler 'file'`. This prevented the Django application from starting in the Docker container.

### Analysis and Root Cause
- The logging configuration used a `FileHandler` to write logs to `/app/logs/django.log`, but the `/app/logs/` directory did not exist in the Docker container.
- The `FileHandler` requires the target directory to be present before writing, and no command or volume in `docker-compose.yml` created `/app/logs/`.
- Removing the logging configuration from `settings.py` resolved the issue, but persistent logging is needed for debugging.

### Solution
- **website/backend/docker-compose.yml**:
  - [Added volume mapping to persist logs locally:
    ```yaml
    services:
      codefleet:
        volumes:
          - ./logs:/app/logs
    ```
  ]
- **scripts/entrypoint.sh**:
  - [Added command to create `logs/` directory:
    ```bash
    mkdir -p /app/logs
    ```
  ]
- **website/backend/codefleet/settings.py**:
  - [Added logging configuration with console fallback:
    ```python
    LOGGING = {
        'version': 1,
        'disable_existing_loggers': False,
        'handlers': {
            'file': {
                'level': 'DEBUG',
                'class': 'logging.FileHandler',
                'filename': '/app/logs/django.log',
            },
            'console': {
                'level': 'DEBUG',
                'class': 'logging.StreamHandler',
            },
        },
        'loggers': {
            '': {
                'handlers': ['file', 'console'],
                'level': 'DEBUG',
                'propagate': True,
            },
        },
    }
    ```
  ]

### Impact
- **Positive**: Logs are now saved to `./logs/django.log` locally, with console logging as a fallback for debugging. This ensures persistent and accessible logs.
- **Negative**: Requires creating a local `logs/` directory before running `docker-compose up`. Minimal disk space used for logs.
- **Testing**: Run `docker-compose up`, verify `./logs/django.log` exists, and check `docker-compose logs codefleet` for no `FileNotFoundError`.

## Testing and Validation
- Tested locally with Docker:
  ```bash
  cd website/backend
  docker-compose up --build
  curl -v http://localhost:8000/basicauth/  # Expect 401 with WWW-Authenticate
  curl -v -u admin:admin http://localhost:8000/basicauth/  # Expect 200 with HTML
  ```
- Tested in browsers (Chrome, Firefox, Incognito):
  - `http://localhost:8000/basicauth/` → Pop-up appears.
  - Enter `admin:admin` → Redirects to success page (`BasicAuth.jsx`).
  - `http://admin:admin@localhost:8000/basicauth` → Direct to success page.
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
- **CI Pipeline**: Ensure `npm run build` runs before `collectstatic` in `website_and_cfinspector_ci.yml`.
- **Logging**: Check `./logs/django.log` for debugging output.

## Date Resolved
- May 15, 2025