# Selenium Project

## Overview
The `selenium` project is a Java-based UI testing framework using Selenium WebDriver and TestNG. It includes two independent projects: `PageInspector` (tests Herokuapp website) and `CFInspector` (tests Codefleet website).

## Project Configuration

### PageInspector
- **Location**: `automation/ui/selenium/java/PageInspector/HerokuApp`
- **Main Package**: `modules`
- **Dependencies**: Defined in `pom.xml`
- **Tests**: Located in `src/test/java/modules/tests`
- **CI**: Configured in `.github/workflows/selenium_ci.yml`
- **Browser**: Chrome (headless mode supported)

### CFInspector
- **Location**: `automation/ui/selenium/java/CFInspector`
- **Main Package**: `com.codefleet.cfinspector.modules`
- **Dependencies**: Defined in `pom.xml`
- **Tests**: Located in `src/test/java/com/codefleet/cfinspector/modules/tests`
- **CI**: Configured in `.github/workflows/website_and_cfinspector_ci.yml`
- **Browser**: Chrome (headless mode supported)

## Setup Instructions

### Prerequisites
- JDK 17 and Maven
- Chrome browser
- Docker (optional, for containerized runs)
- Codefleet website running locally for CFInspector (see `website.md`)

### PageInspector Setup
1. Ensure JDK 17 and Maven are installed:
   ```bash
   java -version
   mvn -version
   ```
2. Install Chrome browser:
   ```bash
   google-chrome --version
   ```
3. Navigate to the project directory:
   ```bash
   cd automation/ui/selenium/java/PageInspector/HerokuApp
   ```

### CFInspector Setup
1. Start the Codefleet website:
   ```bash
   cd website/backend
   mkdir -p logs
   docker-compose up -d
   python manage.py runserver
   ```
2. Ensure JDK 17 and Maven are installed (as above).
3. Install Chrome browser (as above).
4. Navigate to the project directory:
   ```bash
   cd automation/ui/selenium/java/CFInspector
   ```

## How to Run

### PageInspector (Using Docker)
1. Build Docker image:
   ```bash
   cd automation/ui/selenium/java/PageInspector/HerokuApp
   docker build -t selenium-herokuapp .
   ```
2. Run tests:
   ```bash
   docker run --rm -e BROWSER=chrome -e HEADLESS=true selenium-herokuapp mvn clean test -Dtestng.xml=testng.xml
   ```
3. View reports:
   - Reports: `target/surefire-reports/`
   - Screenshots (if failed): `target/screenshots/`

### PageInspector (Without Docker)
1. Install dependencies and run tests:
   ```bash
   cd automation/ui/selenium/java/PageInspector/HerokuApp
   mvn clean test -Dtestng.xml=testng.xml -DBROWSER=chrome -DHEADLESS=true
   ```
2. View reports:
   - Reports: `target/surefire-reports/`
   - Screenshots (if failed): `target/screenshots/`

### CFInspector (Using Docker)
1. Ensure Codefleet website is running (see Setup).
2. Build Docker image:
   ```bash
   cd automation/ui/selenium/java/CFInspector
   docker build -t selenium-cfinspector .
   ```
3. Run tests:
   ```bash
   docker run --rm -e BROWSER=chrome -e HEADLESS=true -e BASE_URL=http://host.docker.internal:8000 selenium-cfinspector mvn clean test -Dtestng.xml=testng.xml
   ```
4. View reports:
   - Reports: `target/surefire-reports/`
   - Screenshots (if failed): `target/screenshots/`

### CFInspector (Without Docker)
1. Ensure Codefleet website is running (see Setup).
2. Install dependencies and run tests:
   ```bash
   cd automation/ui/selenium/java/CFInspector
   mvn clean test -Dtestng.xml=testng.xml -DBROWSER=chrome -DHEADLESS=true -DBASE_URL=http://localhost:8000
   ```
3. View reports:
   - Reports: `target/surefire-reports/`
   - Screenshots (if failed): `target/screenshots/`

## Project Details

### PageInspector
- **Framework**: Selenium WebDriver, TestNG
- **Key Classes**:
  - `HerokuappHomePage`: Page object for Herokuapp.
  - `BasePageTest`: Base test class.
  - `CoreActions`: Core Selenium actions.
  - `WebDriverFactory`: Manages WebDriver instances.
  - `LoggerUtil`, `ScreenshotUtil`, `WaitUtil`: Utility classes.
- **Tests**: `HerokuappHomePageTest` (in `src/test/java/modules/tests`).
- **Configuration**:
  - `data.properties`: Test data.
  - `locators.properties`: Element locators.
  - `webapp.properties`: App-specific settings.
- **Linting**: Checkstyle with `checkstyle.xml`.

### CFInspector
- **Framework**: Selenium WebDriver, TestNG
- **Key Classes**:
  - `BasicAuthPage`, `HomePage`, `AutomationPage`, `ABTestPage`, etc.: Page objects for Codefleet website.
  - `BasePageTest`: Base test class.
  - `CoreActions`, `WebDriverFactory`, `LocatorParser`: Core functionality.
  - `LoggerUtil`, `ScreenShotUtil`, `WaitForElementsUtil`: Utility classes.
- **Tests**:
  - `HomePageTest` (in `src/test/java/com/codefleet/cfinspector/modules/tests/homepage`).
  - `ABTestPageTest` (in `src/test/java/com/codefleet/cfinspector/modules/tests/automationpage/abtestpage`).
- **Configuration**:
  - `data.properties`: Test data.
  - `locators.properties`: Element locators.
- **Linting**: Checkstyle with `checkstyle.xml`.

## Testing Guidelines
- **PageInspector**: Tests Herokuapp homepage and features.
- **CFInspector**: Tests Codefleet website features (e.g., Basic Auth, navigation).
- Use headless mode for CI (`HEADLESS=true`).
- Check `target/surefire-reports/` for results and `target/screenshots/` for failures.
- For CFInspector, ensure `http://localhost:8000` is accessible.

## Recent Changes
- Added `CFInspector` to test Codefleet website, including Basic Auth (`BasicAuthPage`).
- Updated CI pipeline (`website_and_cfinspector_ci.yml`) to support CFInspector and Django tests.

*Last Updated: May 15, 2025*