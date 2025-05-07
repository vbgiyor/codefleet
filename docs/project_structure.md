# Codefleet Project Structure

The `codefleet` repository is a monorepo, organizing multiple projects for the `codefleet` web app. Below is the detailed structure:

```
codefleet
├── .github
│   └── workflows
│       ├── deploy_aws.yml
│       ├── deploy_azure.yml
│       ├── django_ci.yml
│       ├── java_ci.yml
│       ├── python_ci.yml
│       ├── selenium_ci.yml
│       └── website_ci.yml
├── .gitignore
├── automation
│   ├── api
│   │   └── apachejmeter
│   │       └── sample_jmx.jmx
│   └── ui
│       └── selenium
│           └── java
│               └── PageInspector
│                   └── HerokuApp
│                       ├── .gitignore
│                       ├── checkstyle.xml
│                       ├── Dockerfile.txt
│                       ├── pom.xml
│                       ├── src
│                       │   ├── main
│                       │   │   ├── java
│                       │   │   │   └── modules
│                       │   │   │       ├── config
│                       │   │   │       │   └── ConfigReader.java
│                       │   │   │       ├── core
│                       │   │   │       │   ├── CoreActions.java
│                       │   │   │       │   └── WebDriverFactory.java
│                       │   │   │       ├── pages
│                       │   │   │       │   ├── BasePage.java
│                       │   │   │       │   └── HerokuappHomePage.java
│                       │   │   │       └── utils
│                       │   │   │           ├── LoggerUtil.java
│                       │   │   │           ├── ScreenshotUtil.java
│                       │   │   │           └── WaitUtil.java
│                       │   │   └── resources
│                       │   │       ├── data.properties
│                       │   │       ├── locators.properties
│                       │   │       ├── META-INF
│                       │   │       │   └── maven
│                       │   │       │       └── archetype.xml
│                       │   │       └── webapp.properties
│                       │   └── test
│                       │       └── java
│                       │           └── modules
│                       │               └── tests
│                       │                   ├── BasePageTest.java
│                       │                   └── HerokuappHomePageTest.java
│                       └── testng.xml
├── docs
│   ├── architecture.md
│   ├── bookslibrary.markdown
│   ├── djangocommerce.markdown
│   ├── project_structure.md
│   ├── selenium.markdown
│   ├── setup.md
│   ├── website.markdown
│   └── website.md
├── LICENSE
├── project_structure.txt
├── pyproject.toml
├── python
│   └── django-framework
│       ├── apps
│       │   ├── bookslibrary
│       │   │   ├── bookstore
│       │   │   │   ├── __init__.py
│       │   │   │   ├── models.py
│       │   │   │   ├── serializers.py
│       │   │   │   ├── tests.py
│       │   │   │   ├── urls.py
│       │   │   │   └── views.py
│       │   │   └── manage.py
│       │   └── djangocommerce
│       │       ├── docker-compose.yml
│       │       ├── manage.py
│       │       ├── order_service
│       │       │   └── Dockerfile
│       │       ├── orders
│       │       │   ├── __init__.py
│       │       │   ├── models.py
│       │       │   ├── serializers.py
│       │       │   ├── tests.py
│       │       │   ├── urls.py
│       │       │   └── views.py
│       │       ├── user_service
│       │       │   └── Dockerfile
│       │       └── users
│       │           ├── __init__.py
│       │           ├── models.py
│       │           ├── serializers.py
│       │           ├── tests.py
│       │           ├── urls.py
│       │           └── views.py
│       ├── core
│       │   ├── __init__.py
│       │   ├── settings
│       │   │   ├── __init__.py
│       │   │   ├── base.py
│       │   │   ├── dev.py
│       │   │   └── prod.py
│       │   ├── urls.py
│       │   └── wsgi.py
│       ├── docker-compose.yml
│       ├── Dockerfile
│       ├── manage.py
│       ├── postman-collection
│       │   └── post.json
│       └── requirements
│           ├── base.txt
│           ├── dev.txt
│           └── prod.txt
├── README.md
└── website
    └── frontend
        ├── Dockerfile.txt
        ├── public
        │   ├── favicon.ico
        │   └── index.html
        └── src
            ├── components
            │   └── Navbar.jsx
            └── pages
                └── Home.jsx

47 directories, 82 files
```