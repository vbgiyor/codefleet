# Codefleet Website Development Journey

This document captures the development journey of the Codefleet website, a React and Django-based application, over the period of May 12–14, 2025. It details the problems faced, solutions implemented, recommendations, implementation approaches, and key learnings. The website was developed to include basic functionalities like navigation, case studies, authentication (Sign In/Sign Out, Basic Auth), and password reset, while ensuring compatibility with Selenium automation testing via the CFInspector module.

## Project Overview

- **Repository:** [https://github.com/vbgivor/codefleet/](https://github.com/vbgivor/codefleet/)
- **Team:** Shekhar (primary developer), with assistance from Grok (xAI).
- **Tech Stack:**
  - **Frontend:** React (with `react-router-dom`, Tailwind CSS), running at `http://localhost:3000` (development) or served via Django at `http://localhost:8000` (production/testing).
  - **Backend:** Django with PostgreSQL, Redis, running at `http://localhost:8000`.
  - **Automation:** Selenium with Java (CFInspector module), TestNG.
- **Deployment:** Dockerized setup using `docker-compose.yaml` for backend and frontend integration.
- **CI/CD:** GitHub Actions workflows (`website_ci.yml`, `selenium_ci.yml`).

## Problems Faced and Solutions Implemented

### 1. BauHausBold Font Loading Error
**Problem:** The website displayed an error: `Failed to decode downloaded font: http://localhost:3000/static/media/BauhausBold.b4bfaf572f237e4310b3.ttf`, indicating the `BauHausBold` font failed to load.

**Solution:**
- **Diagnosis:** The font file was bundled into `/static/media/` by the React build tool, but `index.css` incorrectly referenced it at `/src/fonts/`. The file might also have been corrupted.
- **Fix:**
  - Moved `BauHausBold.ttf` to `website/frontend/public/fonts/` to serve it statically.
  - Updated `index.css` to reference the font correctly:
    ```css
    @font-face {
      font-family: 'BauHausBold';
      src: url('/fonts/BauHausBold.ttf') format('truetype');
      font-weight: bold;
      font-style: normal;
    }
    ```
  - Restarted the development server (`npm start`) to confirm the font loaded successfully.
- **Verification:** The `<h1>` element with `id="company_name"` in `Home.jsx` applied the `BauHausBold` font, and the browser’s Network tab showed a 200 status for the font file.

**Recommendation:** Use the `public/` directory for static assets like fonts to avoid bundling issues with React. Regularly check the Network tab during development to catch asset loading errors early.

**Approach:** Shekhar moved the font to the `public/` directory and updated the CSS, ensuring the font loaded without bundling interference.

### 2. Blank Pages for A/B Testing, Add/Remove Elements, and Basic Auth
**Problem:** Links on the Automation page (`/case-studies/automation`) for A/B Testing, Add/Remove Elements, and Basic Auth led to blank pages.

**Solution:**
- **Diagnosis:** The links in `Automation.jsx` (`/abtest`, `/addremoveelements`, `/basicauth/`) did not match the routes in `App.jsx` (`/case-studies/automation/abtest`, etc.). The Basic Auth link used `<a>` instead of `<Link>`, causing a full page reload.
- **Fix:**
  - Updated `App.jsx` to define nested routes under `/case-studies/automation`:
    ```jsx
    <Route path="automation">
      <Route index element={<Automation />} />
      <Route path="abtest" element={<ABTest />} />
      <Route path="addremoveelements" element={<AddRemoveElements />} />
    </Route>
    ```
  - Moved the Basic Auth route to the top level:
    ```jsx
    <Route path="/basicauth" element={<BasicAuth />} />
    ```
  - Updated `Automation.jsx` to use relative paths with `<Link>`:
    ```jsx
    <Link to="abtest">A/B Testing</Link>
    <Link to="addremoveelements">Add / Remove Elements</Link>
    <Link to="/basicauth">Basic Auth</Link>
    ```
  - Added `<Outlet />` in `Automation.jsx` to render nested routes.
- **Verification:** The pages loaded correctly at `/case-studies/automation/abtest`, `/case-studies/automation/addremoveelements`, and `/basicauth`.

**Recommendation:** Use `<Link>` for client-side navigation in React to prevent full page reloads. Define routes hierarchically with nested routes for better organization.

**Approach:** Shekhar preferred using `<Link>` and nested routes to maintain a clean routing structure, ensuring all links worked without page reloads.

### 3. Basic Auth Route Mismatch and No Prompt
**Problem:** The Basic Auth page (`/basicauth`) did not trigger the browser’s authentication prompt, and the frontend route did not align with the backend’s Basic Auth endpoint.

**Solution:**
- **Diagnosis:** The React dev server (`http://localhost:3000`) handled `/basicauth` via React Router, rendering `BasicAuth.jsx` client-side without hitting Django’s `basic_auth_view`. This bypassed the backend’s Basic Auth enforcement, resulting in no prompt.
- **Fix:**
  - **Backend:** Updated `urls.py` to prioritize the `/basicauth` route before the catch-all route:
    ```python
    path('basicauth/', basic_auth_view, name='basic_auth'),
    re_path(r'^(?iapi/|basicauth/|static/).*', TemplateView.as_view(template_name='index.html'), name='spa'),
    ```
  - Added `@never_cache` to `basic_auth_view` to prevent caching issues:
    ```python
    from django.views.decorators.cache import never_cache

    @never_cache
    def basic_auth_view(request):
        auth_header = request.META.get('HTTP_AUTHORIZATION', '')
        if auth_header.startswith('Basic '):
            encoded_credentials = auth_header.split(' ')[1]
            decoded_credentials = base64.b64decode(encoded_credentials).decode('utf-8')
            username, password = decoded_credentials.split(':')
            if username == 'admin' and password == 'admin':
                return render(request, 'index.html')
            return HttpResponse('Unauthorized', status=401)
        return HttpResponse('Authorization header missing', status=401)
    ```
  - **Frontend (Development):** Configured a proxy in `package.json` to forward `/basicauth/` requests to Django:
    ```json
    "proxy": "http://localhost:8000"
    ```
  - **Production/Testing:** Served the React app through Django (`http://localhost:8000`) by updating `settings.py` to include the React build directory:
    ```python
    TEMPLATES = [
        {
            'BACKEND': 'django.template.backends.django.DjangoTemplates',
            'DIRS': [
                BASE_DIR / 'templates',
                BASE_DIR.parent / 'frontend/build',
            ],
            'APP_DIRS': True,
            ...
        },
    ]
    STATICFILES_DIRS = [
        BASE_DIR.parent / 'frontend/build/static',
    ]
    ```
  - Ran `collectstatic` to copy React’s static files:
    ```bash
    docker-compose exec codefleet python manage.py collectstatic --noinput
    ```
- **Verification:** Accessing `http://admin:admin@localhost:8000/basicauth` triggered the browser prompt and displayed the `BasicAuth.jsx` page upon successful authentication. In development, the proxy ensured the prompt appeared even with `npm start`.

**Recommendation:** Serve the React app through Django in production/testing to enforce server-side authentication like Basic Auth. Use a proxy in development to mimic this behavior without losing hot reloading.

**Approach:** Shekhar opted to serve the app through Django for testing, using the proxy for development to maintain a smooth workflow with `npm start`.

### 4. Forgot Password and Reset Password Label Confusion
**Problem:** There was confusion between `ForgotPassword.jsx` and `ResetPassword.jsx`, and the Forgot Password page needed its title set to “Reset Password,” with the email field cleared after submission and an updated success message.

**Solution:**
- **Diagnosis:** The provided `forgotpassword.jsx` was actually `ResetPassword.jsx`, causing a mix-up. The Forgot Password page (for email submission) required specific updates.
- **Fix:**
  - Created a proper `ForgotPassword.jsx` with the title “Reset Password”:
    ```jsx
    <h2 className="text-2xl font-bold text-center my-8" id="forgot_password_title">Reset Password</h2>
    ```
  - Added email field clearing:
    ```jsx
    const handleSubmit = async (e) => {
      e.preventDefault();
      try {
        const response = await axios.post('http://localhost:8000/api/password-reset/', { email });
        setMessage(response.data.message);
        setError('');
        setEmail(''); // Clear email field
      } catch (err) {
        setError(err.response?.data?.error || 'Something went wrong');
        setMessage('');
      }
    };
    ```
  - Updated the backend success message in `views.py`:
    ```python
    return Response({
        'message': 'If provided email is present in our system, you must have received link to reset password. Please check.'
    }, status=status.HTTP_200_OK)
    ```
  - Aligned styling with `ResetPassword.jsx` and added form validation using `react-hook-form`.
- **Verification:** The Forgot Password page at `/forgot-password` displayed the correct title, cleared the email field on submission, and showed the updated success message.

**Recommendation:** Use consistent naming conventions for files and components to avoid confusion. Implement form validation early to ensure robust user input handling.

**Approach:** Shekhar requested specific updates (title, email clearing, success message) and later opted to align styling and add validation for consistency across forms.

### 5. General Routing Issues
**Problem:** Some routes, such as `/forgot-password` and `/reset-password/:uid/:token`, were not rendering due to missing route definitions in `App.jsx`.

**Solution:**
- **Fix:**
  - Added missing routes in `App.jsx`:
    ```jsx
    <Route path="/forgot-password" element={<ForgotPassword />} />
    <Route path="/reset-password/:uid/:token" element={<ResetPassword />} />
    ```
- **Verification:** All pages rendered correctly at their respective URLs.

**Recommendation:** Define all routes upfront in `App.jsx` and test navigation during development to catch missing routes early.

**Approach:** Shekhar added the routes as suggested, ensuring all pages were accessible.

### 6. Database Initialization and Volume Issues
**Problem:** The PostgreSQL database (`codefleet_db`) failed to initialize properly, and removing the `postgres_data` volume caused data loss, leading to errors like `relation "codefleet_userprofile" does not exist`.

**Solution:**
- **Diagnosis:** The `init-db.sql` file was incorrectly created as a directory, causing initialization errors. Removing the volume deleted the database, and migrations were not reapplied.
- **Fix:**
  - Replaced `init-db.sql` directory with a file:
    ```bash
    echo "CREATE DATABASE codefleet_db; GRANT ALL PRIVILEGES ON DATABASE codefleet_db TO codefleet_user;" > init-db.sql
    ```
  - Rebuilt the containers and applied migrations:
    ```bash
    docker-compose down --volumes
    docker-compose up --build
    docker-compose exec codefleet python manage.py migrate
    ```
  - Backed up data before volume removal (for future prevention):
    ```bash
    docker-compose exec db pg_dump -U codefleet_user codefleet_db > backup.sql
    ```
  - Restored data if needed:
    ```bash
    docker-compose exec -T db psql -U codefleet_user -d codefleet_db < backup.sql
    ```
- **Verification:** The database initialized correctly, and the `codefleet_userprofile` table was recreated after migrations.

**Recommendation:** Always back up the database before removing volumes. Use a healthcheck in `docker-compose.yaml` to ensure the database is ready before the application starts.

**Approach:** Shekhar followed the steps to fix `init-db.sql` and reapply migrations, adding backup/restore steps to his workflow to prevent future data loss.

### 7. Password Field Migration and Empty Password Issues
**Problem:** Adding a non-nullable `password` field to `UserProfile` caused migration issues, and the field remained empty, breaking sign-in functionality.

**Solution:**
- **Diagnosis:** The migration required a default value, and the `password` field in `UserProfile` was redundant since the `User` model already handled authentication.
- **Fix:**
  - Removed the `password` field, `set_password`, and `check_password` methods from `UserProfile` in `models.py`.
  - Updated `serializers.py` to remove password-related logic:
    ```python
    # Removed password=user.password in UserProfileSerializer.create
    # Removed UserProfile password sync in PasswordResetConfirmSerializer.save
    ```
  - Updated `views.py` to use the `User` model for authentication, fixing the `last_login` error in `PasswordResetView`.
  - Ran migrations to apply changes:
    ```bash
    docker-compose exec codefleet python manage.py makemigrations
    docker-compose exec codefleet python manage.py migrate
    ```
- **Verification:** Sign-in and password reset worked on both `localhost:3000` and `localhost:8000`, with no empty password issues.

**Recommendation:** Rely on Django’s `User` model for authentication to avoid redundancy. Test migrations on a staging database first to catch issues like non-nullable field errors.

**Approach:** Shekhar chose to simplify the application by removing the redundant `password` field, focusing on standard Django authentication practices.

### 8. 403 Errors on All Endpoints
**Problem:** After backend changes, all endpoints (Sign In, Sign Up, Reset Password) returned 403 errors, with no API calls appearing in the network tab.

**Solution:**
- **Diagnosis:** The 403 errors were due to CORS/CSRF misconfigurations, despite `@csrf_exempt`. The lack of network tab activity indicated browser-level blocking.
- **Fix:**
  - Verified CORS settings in `settings.py`:
    ```python
    CORS_ALLOWED_ORIGINS = [
        "http://localhost:3000",
        "http://localhost:8000",
    ]
    ```
  - Ensured endpoints remained `@csrf_exempt`.
  - Updated frontend requests to include credentials if needed (not applicable here since `@csrf_exempt` was used).
- **Verification:** API calls appeared in the network tab, and endpoints returned 200 statuses.

**Recommendation:** Double-check CORS and CSRF settings when encountering 403 errors. Use browser dev tools to debug network activity early.

**Approach:** Shekhar confirmed the CORS settings were correct and relied on `@csrf_exempt` to bypass CSRF issues, resolving the 403 errors.

### 9. Case Studies Dropdown in Header
**Problem:** The header needed a “Case Studies” tab with a dropdown for Java, Python, and Automation pages, with flexibility for future additions.

**Solution:**
- **Fix:**
  - Created `Header.jsx` with a click-based dropdown using React state:
    ```jsx
    const [isCaseStudiesOpen, setIsCaseStudiesOpen] = useState(false);
    const caseStudies = [
      { name: "Java", path: "/case-studies/java" },
      { name: "Python", path: "/case-studies/python" },
      { name: "Automation", path: "/case-studies/automation" },
    ];
    ```
  - Updated `App.jsx` to include new routes:
    ```jsx
    <Route path="case-studies">
      <Route index element={<CaseStudies />} />
      <Route path="java" element={<JavaCaseStudy />} />
      <Route path="python" element={<PythonCaseStudy />} />
      <Route path="automation" element={<AutomationCaseStudy />}>
        <Route path="abtest" element={<ABTest />} />
        <Route path="addremoveelements" element={<AddRemoveElements />} />
      </Route>
    </Route>
    ```
  - Created placeholder components (`JavaCaseStudy.jsx`, `PythonCaseStudy.jsx`, `AutomationCaseStudy.jsx`).
  - Updated `Home.jsx` to link to `/case-studies/automation` with the new ID `case_studies_automation_link`.
- **Verification:** The header displayed Home, Case Studies (with dropdown), Contact, and Account tabs. Clicking Case Studies showed the dropdown with Java, Python, and Automation links.

**Recommendation:** Use an array for dropdown items to ensure extensibility. Consider a backend API for dynamic case study data in the future.

**Approach:** Shekhar opted for a click-based dropdown for better accessibility and ensured the structure was extensible by using an array.

### 10. Volume Requirement Issue in `website_ci.yml`
**Problem:** The `website_ci.yml` workflow included a `volumes: postgres_data:` section, which caused a syntax error because GitHub Actions does not support top-level named volumes. Additionally, there was uncertainty about whether the `postgres_data` volume was needed for the website backend CI workflow.

**Solution:**
- **Diagnosis:** In GitHub Actions, services like `postgres` are ephemeral—they are created fresh for each job run and destroyed afterward. Named volumes don’t persist across runs, and top-level `volumes` sections are not supported in GitHub Actions workflows, leading to the syntax error. The `postgres_data` volume was intended to persist PostgreSQL data, but this wasn’t necessary for CI purposes since the database is recreated each time.
- **Fix:**
  - Removed the top-level `volumes: postgres_data:` section from `website_ci.yml` to resolve the syntax error.
  - Kept the `postgres` service in both the `backend` and `collect-static` jobs to provide a temporary database for migrations, tests, and `collectstatic`, but removed the `volumes` mapping since persistence isn’t needed.
  - Added a comment in the `collect-static` job to note that the `postgres` and `redis` services might be removable if `collectstatic` doesn’t require database access:
    ```yaml
    services:
      # PostgreSQL service for Django (included to ensure collectstatic works if database access is needed)
      postgres:
        image: postgres:latest
        env:
          POSTGRES_DB: codefleet_db
          POSTGRES_USER: codefleet_user
          POSTGRES_PASSWORD: codefleet_pass
        ports:
          - 5432:5432
        options: --health-cmd pg_isready --health-interval 10s --health-timeout 5s --health-retries 5

      # Redis service for Django (included to match production environment)
      redis:
        image: redis:latest
        ports:
          - 6379:6379
    ```
- **Verification:** The updated `website_ci.yml` parsed correctly in GitHub Actions and ran without syntax errors. The `postgres` service provided a working database for migrations and tests, and `collectstatic` completed successfully.

**Recommendation:** Avoid using named volumes in GitHub Actions unless persistence across runs is explicitly required (e.g., for caching or debugging). For CI workflows, rely on ephemeral services for database needs. Test whether `collectstatic` requires database access in your setup; if not, remove the `postgres` and `redis` services from the `collect-static` job to optimize resource usage.

**Approach:** Shekhar agreed to remove the `volumes` section and keep the `postgres` service in the `collect-static` job for now, with a plan to test whether `collectstatic` can run without database access to potentially optimize the workflow further.

## Key Learnings

- **Frontend Routing:** Use `<Link>` and nested routes in React to manage navigation effectively. Test all routes early to catch mismatches.
- **Backend Authentication:** Serve React through Django for server-side authentication like Basic Auth. Use proxies in development to maintain hot reloading while testing auth flows.
- **Database Management:** Always back up data before removing Docker volumes. Use healthchecks to ensure database readiness.
- **Static Files:** Run `collectstatic` after frontend changes to ensure Django serves the latest React assets.
- **Testing Impact:** Frontend changes (e.g., route updates, ID changes) affect Selenium tests. Plan to update locators and test scripts alongside UI changes.
- **Volumes in GitHub Actions CI:** GitHub Actions services are ephemeral, meaning they don’t persist data between runs. Named volumes (e.g., `postgres_data`) are not needed for CI workflows unless data persistence across runs is explicitly required (e.g., for caching or debugging). Use ephemeral services for database needs in CI, and avoid top-level `volumes` sections, as they are not supported and cause syntax errors.

## CI/CD Updates

### `website_ci.yml` Update Summary (May 14, 2025)
The `website_ci.yml` workflow was updated to reflect recent project changes and improve reliability:
- **Added Services:** Included PostgreSQL and Redis services in the `backend` and `collect-static` jobs to match the production environment, ensuring migrations and tests run correctly.
- **Added Migration Step:** Added a step to apply migrations before running Django tests, ensuring the database schema is up to date.
- **Added `collect-static` Job:** Introduced a new job to run `collectstatic` after the frontend build, ensuring Django can serve the React app in production. This job depends on `backend` and `frontend` to ensure both are complete.
- **Paths and Filenames:** Verified that paths (`website/backend`, `website/frontend`) match the project structure.
- **Environment Variables:** Added `DATABASE_URL` to connect to the PostgreSQL service, using credentials from the project structure.
- **Volume Handling:** Removed the erroneous `volumes: postgres_data:` section, as GitHub Actions does not support top-level named volumes and persistence isn’t needed for CI. Kept the `postgres` service in the `collect-static` job to ensure `collectstatic` works if database access is required.
- **Recommendation:** Test whether `collectstatic` requires database access in your setup. If not, remove the `postgres` and `redis` services from the `collect-static` job to optimize resource usage.

### `selenium_ci.yml` Update Summary (May 14, 2025)
The `selenium_ci.yml` workflow was updated to support multiple projects and optimize CI runs:
- **Matrix Strategy:** Updated the `strategy.matrix.project` to include `[PageInspector, CFInspector]`, allowing the workflow to run for multiple projects under `automation/ui/selenium/java`.
- **Path Filtering:** Updated the `on.push.paths` and `on.pull_request.paths` to include both projects’ directories (`automation/ui/selenium/java/PageInspector/**`, `automation/ui/selenium/java/CFInspector/**`).
- **Dynamic Change Detection:** Added a step using `tj-actions/changed-files` to detect changes in each project’s directory, ensuring CI builds only run for projects with changes.
- **Working Directory:** Updated all `working-directory` paths to use `automation/ui/selenium/java/${{ matrix.project }}`, ensuring steps run in the correct project directory.
- **Cache Key:** Updated the Maven cache key to include the project name (`${{ matrix.project }}`), ensuring separate caches for each project.
- **Artifact Names:** Ensured artifact names include the project name (`selenium-${{ matrix.project }}-testng-reports`, `selenium-${{ matrix.project }}-screenshots`).
- **Recommendation:** To add new projects in the future, update the `matrix.project` list and the `paths` filters accordingly. If Selenium tests require the Codefleet website to be running, add website services (Django, React) to the workflow.

## Next Steps

- **CI/CD Testing:** Test the updated `website_ci.yml` and `selenium_ci.yml` workflows to ensure they run successfully. Optimize the `collect-static` job in `website_ci.yml` by removing unnecessary services if `collectstatic` doesn’t require database access.
- **Automation Suite:** Update the CFInspector module to align with website changes (e.g., new routes, updated locators).
- **Dynamic Content:** Consider adding a backend API for case study data to make the content dynamic.

This concludes the Codefleet website development journey as of May 14, 2025. For further details, refer to the project repository and meeting notes.