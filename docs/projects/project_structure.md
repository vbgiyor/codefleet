# Codefleet Project Structure

The `codefleet` repository is a monorepo, organizing multiple projects for the `codefleet` web app. Below is the detailed structure:

```
codefleet
.
├── .github
│   └── workflows
│       ├── bookslibrary_ci.yml
│       ├── ecommerce_ci.yml
│       ├── selenium_ci.yml
│       ├── website_and_cfinspectorci.yml
│       └── website_ci.yml
├── .gitignore
├── automation
│   ├── api
│   │   └── apachejmeter
│   │       └── sample_jmx.jmx
│   └── ui
│       └── selenium
│           └── java
│               ├── CFInspector
│               │   ├── checkstyle.xml
│               │   ├── Dockerfile.txt
│               │   ├── log4j2.xml
│               │   ├── pom.xml
│               │   ├── src
│               │   │   ├── main
│               │   │   │   ├── java
│               │   │   │   │   └── com
│               │   │   │   │       └── codefleet
│               │   │   │   │           └── cfinspector
│               │   │   │   │               └── modules
│               │   │   │   │                   ├── config
│               │   │   │   │                   │   └── ConfigManager.java
│               │   │   │   │                   ├── core
│               │   │   │   │                   │   ├── CoreActions.java
│               │   │   │   │                   │   ├── LocatorParser.java
│               │   │   │   │                   │   └── WebDriverFactory.java
│               │   │   │   │                   ├── pages
│               │   │   │   │                   │   ├── ABTestPage.java
│               │   │   │   │                   │   ├── AddRemoveElementsPage.java
│               │   │   │   │                   │   ├── AutomationPage.java
│               │   │   │   │                   │   ├── BasePage.java
│               │   │   │   │                   │   ├── BasicAuthPage.java
│               │   │   │   │                   │   ├── ContactPage.java
│               │   │   │   │                   │   ├── HomePage.java
│               │   │   │   │                   │   ├── JavaPage.java
│               │   │   │   │                   │   ├── PageNavigationUtility.java
│               │   │   │   │                   │   └── PythonPage.java
│               │   │   │   │                   └── utils
│               │   │   │   │                       ├── LoggerUtil.java
│               │   │   │   │                       ├── ScreenShotUtil.java
│               │   │   │   │                       └── WaitForElementsUtil.java
│               │   │   │   └── resources
│               │   │   │       ├── data.properties
│               │   │   │       └── locators.properties
│               │   │   ├── test
│               │   │   │   └── java
│               │   │   │       └── com
│               │   │   │           └── codefleet
│               │   │   │               └── cfinspector
│               │   │   │                   └── modules
│               │   │   │                       └── tests
│               │   │   │                           ├── ABTestPageTest.java
│               │   │   │                           ├── AddRemoveElementsPageTest.java
│               │   │   │                           ├── BasePageTest.java
│               │   │   │                           ├── HomePageTest.java
│               │   │   │                           └── TestSetup.java
│               │   │   └── testcases
│               │   │       └── cases.txt
│               │   ├── testng-firefox.xml
│               │   ├── testng-parallel.xml
│               │   └── testng.xml
│               ├── GithubRepoListingDemo
│               │   ├── allure-report.png
│               │   ├── jenkins-plugin.png
│               │   ├── README.md
│               │   └── uiapi
│               │       ├── allure-results
│               │       │   └── .gitkeep
│               │       ├── pom.xml
│               │       └── src
│               │           ├── main
│               │           │   ├── java
│               │           │   │   ├── core
│               │           │   │   │   ├── LoadEnvProperties.java
│               │           │   │   │   ├── LocatorParser.java
│               │           │   │   │   ├── PageCore.java
│               │           │   │   │   └── UIElementsImpl.java
│               │           │   │   ├── pages
│               │           │   │   │   └── GitHubRepoList.java
│               │           │   │   └── utilities
│               │           │   │       └── ResponseParser.java
│               │           │   └── resources
│               │           │       ├── drivers
│               │           │       │   ├── chromedriver.exe
│               │           │       │   ├── geckodriver.exe
│               │           │       │   └── msedgedriver.exe
│               │           │       ├── envConfig.properties
│               │           │       ├── log4j2.xml
│               │           │       └── props
│               │           │           └── Locators.properties
│               │           └── test
│               │               ├── java
│               │               │   ├── config
│               │               │   │   └── TestCore.java
│               │               │   ├── listeners
│               │               │   │   ├── RetryAnnotationTransformer.java
│               │               │   │   ├── RetryFailedCases.java
│               │               │   │   └── TestReportListener.java
│               │               │   └── validations
│               │               │       └── GitHubRepoListTests.java
│               │               └── resources
│               │                   └── testng.xml
│               ├── logback-test.xml
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
│   ├── projects
│   │   ├── architecture.md
│   │   ├── bookslibrary.md
│   │   ├── codefleet-web-app-dev-journey.md
│   │   ├── djangocommerce.md
│   │   ├── project_structure.md
│   │   ├── selenium.md
│   │   ├── simple-DRF-projects.md
│   │   └── website.md
│   ├── python
│   │   ├── drf
│   │   │   ├── django_orm_basics.md
│   │   │   └── variables.md
│   │   └── learn-to-code
│   │       └── rendering_markdown_doc_as_html.md
│   └── solutions
│       ├── aws-deployment-and-hosting.md
│       ├── basic-auth-browser-prompt-issue-resolution.md
│       ├── git-checkout-conflict-branch.md
│       ├── markdown-file-benefits.md
│       └── selenium-test-independence-guide.md
├── LICENSE
├── project_tree.txt
├── pyproject.toml
├── python
│   ├── django-framework
│   │   ├── .flake8
│   │   └── bookslibrary
│   │       ├── bookslab
│   │       │   ├── __init__.py
│   │       │   ├── apps.py
│   │       │   ├── asgi.py
│   │       │   ├── init_db.sh
│   │       │   ├── initial_data.json
│   │       │   ├── migrations
│   │       │   │   ├── __init__.py
│   │       │   │   └── 0001_initial.py
│   │       │   ├── models.py
│   │       │   ├── serializers.py
│   │       │   ├── settings.py
│   │       │   ├── tests.py
│   │       │   ├── urls.py
│   │       │   ├── views.py
│   │       │   └── wsgi.py
│   │       ├── docker-compose.yml
│   │       ├── Dockerfile
│   │       ├── entrypoint.sh
│   │       ├── initial_data.json
│   │       ├── manage.py
│   │       └── requirements.txt
│   ├── drfsessions
│   │   └── warmup
│   │       ├── basics
│   │       │   ├── __init__.py
│   │       │   ├── admin.py
│   │       │   ├── apps.py
│   │       │   ├── migrations
│   │       │   │   └── __init__.py
│   │       │   ├── models.py
│   │       │   ├── tests.py
│   │       │   └── views.py
│   │       ├── db.sqlite3
│   │       ├── manage.py
│   │       ├── templates
│   │       │   └── drfsessions.html
│   │       └── warmup
│   │           ├── __init__.py
│   │           ├── asgi.py
│   │           ├── settings.py
│   │           ├── urls.py
│   │           └── wsgi.py
│   └── microservices
│       └── ecommerce
│           ├── db.sqlite3
│           ├── docker-compose.yml
│           ├── order_service
│           │   ├── __init__.py
│           │   ├── asgi.py
│           │   ├── docker-compose.yml
│           │   ├── Dockerfile
│           │   ├── manage.py
│           │   ├── orders
│           │   │   ├── __init__.py
│           │   │   ├── admin.py
│           │   │   ├── apps.py
│           │   │   ├── migrations
│           │   │   │   ├── __init__.py
│           │   │   │   └── 0001_initial.py
│           │   │   ├── models.py
│           │   │   ├── serializers.py
│           │   │   ├── tests.py
│           │   │   ├── urls.py
│           │   │   └── views.py
│           │   ├── requirements.txt
│           │   ├── settings.py
│           │   ├── urls.py
│           │   └── wsgi.py
│           └── user_service
│               ├── __init__.py
│               ├── asgi.py
│               ├── docker-compose.yml
│               ├── Dockerfile
│               ├── manage.py
│               ├── requirements.txt
│               ├── settings.py
│               ├── urls.py
│               ├── users
│               │   ├── __init__.py
│               │   ├── admin.py
│               │   ├── apps.py
│               │   ├── migrations
│               │   │   ├── __init__.py
│               │   │   └── 0001_initial.py
│               │   ├── models.py
│               │   ├── serializers.py
│               │   ├── tests.py
│               │   ├── urls.py
│               │   └── views.py
│               └── wsgi.py
├── README.md
├── scripts
│   ├── build_website.sh
│   ├── clean_stray_bookstore.sh
│   ├── entrypoint.sh
│   ├── run_bookslab.sh
│   └── run_django.sh
└── website
    ├── backend
    │   ├── .gitignore
    │   ├── codefleet
    │   │   ├── __init__.py
    │   │   ├── asgi.py
    │   │   ├── email_utils.py
    │   │   ├── migrations
    │   │   │   ├── __init__.py
    │   │   │   ├── 0001_initial.py
    │   │   │   ├── 0002_alter_userprofile_email.py
    │   │   │   ├── 0003_userprofile_password.py
    │   │   │   ├── 0004_alter_userprofile_password.py
    │   │   │   └── 0005_remove_userprofile_password.py
    │   │   ├── models.py
    │   │   ├── serializers.py
    │   │   ├── settings.py
    │   │   ├── tests.py
    │   │   ├── urls.py
    │   │   ├── views.py
    │   │   └── wsgi.py
    │   ├── docker-compose.yml
    │   ├── Dockerfile
    │   ├── init-db.sql
    │   ├── manage.py
    │   ├── requirements.txt
    │   └── templates
    │       └── emails
    │           ├── contact_email.html
    │           └── signup_email.html
    └── frontend
        ├── build
        │   ├── asset-manifest.json
        │   ├── favicon_ico
        │   │   ├── android-chrome-192x192.png
        │   │   ├── android-chrome-512x512.png
        │   │   ├── apple-touch-icon.png
        │   │   ├── favicon-16x16.png
        │   │   ├── favicon-32x32.png
        │   │   ├── favicon.ico
        │   │   └── site.webmanifest
        │   ├── favicon.ico
        │   ├── fonts
        │   │   ├── BauhausBold.ttf
        │   │   ├── BauhausBoldItalic.ttf
        │   │   ├── BauhausItalic.ttf
        │   │   └── BauhausRegular.ttf
        │   └── index.html
        ├── package-lock.json
        ├── package.json
        ├── postcss.config.js
        ├── public
        │   ├── favicon_ico
        │   │   ├── android-chrome-192x192.png
        │   │   ├── android-chrome-512x512.png
        │   │   ├── apple-touch-icon.png
        │   │   ├── favicon-16x16.png
        │   │   ├── favicon-32x32.png
        │   │   ├── favicon.ico
        │   │   └── site.webmanifest
        │   ├── favicon.ico
        │   ├── fonts
        │   │   ├── BauhausBold.ttf
        │   │   ├── BauhausBoldItalic.ttf
        │   │   ├── BauhausItalic.ttf
        │   │   └── BauhausRegular.ttf
        │   └── index.html
        ├── src
        │   ├── App.jsx
        │   ├── components
        │   │   ├── __tests__
        │   │   │   └── Header.test.jsx
        │   │   ├── Footer.jsx
        │   │   └── Header.jsx
        │   ├── images
        │   │   ├── logo-100x100.png
        │   │   ├── logo-transparent.png
        │   │   └── logo.png
        │   ├── index.css
        │   ├── index.js
        │   ├── output.css
        │   ├── pages
        │   │   ├── Automation.jsx
        │   │   ├── Contact.jsx
        │   │   ├── Home.jsx
        │   │   ├── Java.jsx
        │   │   ├── list
        │   │   │   ├── ABTest.jsx
        │   │   │   ├── AddRemoveElements.jsx
        │   │   │   ├── BasicAuth.jsx
        │   │   │   └── Documentation.jsx
        │   │   ├── Python.jsx
        │   │   ├── ResetPassword.jsx
        │   │   ├── SignIn.jsx
        │   │   └── Signup.jsx
        │   ├── setupTests.js
        │   └── styles.css
        └── tailwind.config.js

110 directories, 262 files
```