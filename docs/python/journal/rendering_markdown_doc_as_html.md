
# 📘 Tutorial: Rendering Markdown Files Outside a Project as HTML

## 🔍 Introduction
This tutorial guides you through rendering a Markdown file (e.g., `rendering_markdown_doc_as_html.md`) located outside your project directory as HTML on any page within a React/Django application using Docker. We'll use a `/docs` directory outside the project to store Markdown files, mount it into the container, and display the content in a modal on a page like `/resources/python`. This setup ensures CI pipeline compatibility and avoids file duplication.

## ✅ Prerequisites
- A React/Django project with Docker, structured as:
  ```
  codefleet
  ├── docs
  │   ├── rendering_markdown_doc_as_html.md
  ├── website
  │   ├── backend
  │   ├── frontend
  │   ├── docker-compose.yml
  ```
- Docker and `docker-compose` installed.
- Basic knowledge of React, Django, and Docker.

## ⚙️ Step 1: Set Up the Backend to Serve Markdown Files
We'll create a Django view to serve Markdown files from a mounted directory.

### 🧱 1.1 Create a View to Serve Markdown
- **Location**: `backend/views.py`
- **Action**: Add a view to read and serve Markdown files.
  ```python
  def serve_markdown(request, filename):
    logger.info(f"Requested filename: {filename}")
    if not filename.endswith('.md'):
        filename = f"{filename}.md"
    file_path = os.path.join("/app/static/markdown", filename)
    logger.info(f"Checking file path: {file_path}")
    if os.path.exists(file_path):
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read()
        logger.info(f"File found, serving content")
        return HttpResponse(content, content_type='text/plain; charset=utf-8')
    logger.error(f"File not found at: {file_path}")
    return HttpResponse("File not found", status=404)
  ```

### 🔗 1.2 Map the URL to the View
- **Location**: `backend/urls.py`
- **Action**: Add a URL pattern to route Markdown file requests to the view.
  ```python
  from django.urls import path
  from .views import serve_markdown

  urlpatterns = [
      path('markdown/<str:filename>', serve_markdown, name='serve_markdown'),
  ]
  ```

## 🐳 Step 2: Configure Docker to Mount the Markdown Directory
We'll use `docker-compose.yml` to mount the `/docs` directory into the container, making the Markdown files accessible.

### 🧩 2.1 Update `docker-compose.yml`
- **Location**: `volumes` section in `docker-compose.yml`
- **Action**: Mount `/codefleet/docs` to `/app/static/markdown/` in both services.
  ```yaml
  version: '3.8'

  services:
    codefleet:
      build:
        context: ../
        dockerfile: backend/Dockerfile
      ports:
        - "8000:8000"
      volumes:
        - .:/app
        - ../docs:/app/static/markdown:ro
    frontend:
      build:
        context: ./frontend
        dockerfile: Dockerfile
      ports:
        - "3000:3000"
      depends_on:
        - codefleet
      volumes:
        - .:/app
        - ../frontend/build:/app/frontend/build
        - ../../docs:/app/static/markdown:ro
  ```

### 🛠️ 2.2 Ensure the Directory Exists in the Container
- **Location**: `backend/Dockerfile`
- **Action**: Add a command to create the `/app/static/markdown/` directory.
  ```dockerfile
  RUN mkdir -p /app/static/markdown
  ```

## 🧾 Step 3: Configure Django Static File Serving
```
    STATIC_URL = '/static/'
    STATICFILES_DIRS = [BASE_DIR / "static"]
    STATIC_ROOT = BASE_DIR / "staticfiles"
```

### 🧹 3.1 Collect Static Files
- **Location**: Inside the `codefleet` container
- **Action**: Run `collectstatic` to prepare static files for serving.
  ```bash
  docker-compose exec codefleet python manage.py collectstatic --noinput
  ```

## ⚛️ Step 4: Fetch and Render the Markdown in React
We'll fetch the Markdown file from the backend and render it as HTML in a modal on a React page.

### 🧬 4.1 Update the React Component
- **Location**: `frontend/src/Python.jsx` (or any page component)
- **Action**: Modify the `handleClick` function to fetch the Markdown file and render it.
  ```jsx
  useEffect(() => {
    const fetchMarkdown = async () => {
      try {
        const cleanFilename = filename.endsWith('.md') ? filename : `${filename}.md`;
        const response = await fetch(`/markdown/${cleanFilename}?ts=${new Date().getTime()}`, {
          cache: 'no-store',
        });
        if (!response.ok) throw new Error(`File not found: ${response.statusText}`);
        const text = await response.text();
        setMarkdownContent(marked(text, { gfm: true, breaks: true }));
      } catch (error) {
        console.error('Error fetching markdown:', error);
        setMarkdownContent(`<p>Error loading markdown file: ${error.message}</p>`);
      }
    };
    fetchMarkdown();
  }, [filename]);
  ```

### 📦 4.2 Add Dependencies for Markdown Rendering
- **Location**: `frontend/package.json`
- **Action**: Ensure `marked` and `github-markdown-css` are installed for Markdown parsing and styling.
  ```json
  "dependencies": {
    "marked": "^12.0.2",
    "github-markdown-css": "^5.5.1"
  }
  ```

## 🧪 Step 5: Test the Setup
1. Rebuild and restart Docker:
   ```bash
   docker-compose down
   docker-compose up --build
   ```
2. Navigate to `http://localhost:3000/resources/python`.
3. Click the link to open the modal and verify that `rendering_markdown_doc_as_html.md` is rendered as HTML.

## 🔁 Step 6: Ensure CI Pipeline Compatibility
The `docker-compose.yml` uses a relative path (`../docs`), which works in CI pipelines (e.g., GitHub Actions) because the repository structure is preserved during checkout. No absolute paths are used, ensuring compatibility.

## 🏁 Conclusion
This setup allows you to render any Markdown file from outside your project as HTML on any page. The key components are the backend view to serve the file, a volume mount in `docker-compose.yml`, and a React component to fetch and render the content.
