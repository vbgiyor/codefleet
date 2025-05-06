# Codefleet Project Structure

The `codefleet` repository is a monorepo, organizing multiple projects for the `codefleet` web app. Below is the detailed structure:

```
codefleet/
├── .github/
│   └── workflows/
│       ├── django_ci.yml          # CI/CD for Django apps
│       ├── website_ci.yml         # CI/CD for website (ReactJS + Node.js)
│       ├── selenium_ci.yml        # CI/CD for Selenium + Java
│       ├── java_ci.yml            # CI/CD for standalone Java programs
│       ├── python_ci.yml          # CI/CD for Python modules
│       ├── deploy_aws.yml         # Deployment to AWS (shared)
│       └── deploy_azure.yml       # Deployment to Azure (shared)
├── django/
│   ├── app1/                     # Django app 1 (e.g., dashboard)
│   │   ├── __init__.py
│   │   ├── admin.py
│   │   ├── apps.py
│   │   ├── migrations/
│   │   ├── models.py
│   │   ├── tests.py
│   │   ├── urls.py
│   │   └── views.py
│   ├── app2/                     # Django app 2 (e.g., api)
│   │   └── ...                   # Similar structure
│   ├── core/                     # Django project settings for Codefleet web app
│   │   ├── __init__.py
│   │   ├── settings/
│   │   │   ├── base.py           # Shared settings
│   │   │   ├── dev.py            # Development settings
│   │   │   └── prod.py           # Production settings
│   │   ├── urls.py
│   │   └── wsgi.py
│   ├── static/                   # Shared static files
│   ├── templates/                # Shared Django templates
│   ├── requirements/
│   │   ├── base.txt              # Shared dependencies
│   │   ├── dev.txt               # Development dependencies
│   │   └── prod.txt              # Production dependencies
│   ├── manage.py
│   ├── Dockerfile                # Docker for Django apps
│   └── docker-compose.yml        # Local development
├── website/
│   ├── frontend/                 # ReactJS frontend for Codefleet website
│   │   ├── public/
│   │   │   ├── index.html        # Main HTML entry point
│   │   │   └── favicon.ico
│   │   ├── src/
│   │   │   ├── components/
│   │   │   │   └── Navbar.jsx    # React components
│   │   │   ├── pages/
│   │   │   │   └── Home.jsx      # React pages
│   │   │   ├── App.jsx
│   │   │   ├── index.js
│   │   │   └── styles/
│   │   │       └── tailwind.css  # Tailwind CSS
│   │   ├── package.json          # Node.js dependencies for frontend
│   │   ├── tailwind.config.js    # Tailwind configuration
│   │   ├── Dockerfile            # Docker for frontend
│   │   └── .env.example          # Environment variables (e.g., API URL)
│   ├── backend/                  # Node.js backend for Codefleet website
│   │   ├── src/
│   │   │   ├── routes/
│   │   │   │   └── api.js       # API routes
│   │   │   ├── controllers/
│   │   │   │   └── apiController.js
│   │   │   └── server.js        # Main server entry point
│   │   ├── package.json          # Node.js dependencies for backend
│   │   ├── Dockerfile            # Docker for backend
│   │   └── .env.example          # Environment variables (e.g., PORT)
│   ├── docker-compose.yml        # Local development for website
│   └── README.md                 # Website project setup
├── java/
│   ├── project1/                 # Standalone Java project
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   └── java/
│   │   │   │       └── com/example/
│   │   │   └── test/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   └── project2/
│       └── ...                   # Similar structure
├── selenium/
│   ├── automation1/              # Selenium project 1
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   └── java/
│   │   │   │       └── com/example/automation/
│   │   │   └── test/
│   │   ├── pom.xml
│   │   ├── testng.xml
│   │   └── Dockerfile
│   └── automation2/
│       └── ...                   # Similar structure
├── python/
│   ├── module1/                  # Standalone Python module
│   │   ├── __init__.py
│   │   ├── main.py
│   │   ├── tests/
│   │   └── requirements.txt
│   ├── module2/
│   │   └── ...                   # Similar structure
│   └── Dockerfile
├── scripts/
│   ├── deploy_aws.sh             # AWS deployment script
│   ├── deploy_azure.sh           # Azure deployment script
│   └── setup_env.sh              # Environment setup script
├── docs/
│   ├── api/                      # API documentation
│   ├── setup.md                  # Setup instructions
│   ├── architecture.md           # Project architecture
│   ├── website.md                # Website-specific documentation
│   └── project_structure.md      # This file
├── .gitignore                    # Git ignore file
├── README.md                     # Project overview
├── LICENSE                       # License file
├── .env.example                  # Shared environment variable template
└── pyproject.toml                # Python project configuration
```